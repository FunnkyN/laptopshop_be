package com.id.akn.serviceimpl;

import com.id.akn.model.Category;
import com.id.akn.model.Order;
import com.id.akn.repository.LaptopRepository;
import com.id.akn.repository.OrderRepository;
import com.id.akn.repository.UserRepository;
import com.id.akn.response.*;
import com.id.akn.service.RevenueStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RevenueStatisticsServiceImpl implements RevenueStatisticsService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LaptopRepository laptopRepository;

    @Override
    public List<YearlyRevenueRes> calculateTotalRevenuePerYear() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.DELIVERED)
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear(),
                        Collectors.summingDouble(Order::getTotalDiscountedPrice)
                ))
                .entrySet().stream()
                .map(entry -> new YearlyRevenueRes(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public YearlyRevenueDTO calculateYearlyRevenueWithMonthlyData(int year) {
        List<Order> orders = orderRepository.findAll();

        List<Order> filteredOrders = orders.stream()
                .filter(order -> order.getCreatedAt().getYear() == year)
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.DELIVERED)
                .collect(Collectors.toList());

        Map<Integer, Double> monthlyRevenueMap = filteredOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getMonthValue(),
                        Collectors.summingDouble(Order::getTotalDiscountedPrice)
                ));

        List<MonthlyRevenueDTO> monthlyRevenueList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            double revenue = monthlyRevenueMap.getOrDefault(month, 0.0);
            monthlyRevenueList.add(new MonthlyRevenueDTO(month, revenue));
        }

        double yearlyTotalRevenue = monthlyRevenueList.stream()
                .mapToDouble(MonthlyRevenueDTO::getRevenue)
                .sum();

        return new YearlyRevenueDTO(year, yearlyTotalRevenue, monthlyRevenueList);
    }

    @Override
    public List<ProductRevenuePercentageDTO> calculateProductRevenuePercentages() {
        List<Order> orders = orderRepository.findAll();

        List<Order> deliveredOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.DELIVERED)
                .collect(Collectors.toList());

        Map<String, Double> productRevenue = deliveredOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.groupingBy(
                        orderItem -> orderItem.getLaptop().getCategories().stream()
                                .map(Category::getName)
                                .findFirst()
                                .orElse("Unknown"),
                        Collectors.summingDouble(item -> {
                            return item.getLaptop().getPrice() * item.getQuantity();
                        })
                ));

        double totalRevenue = deliveredOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(item -> item.getLaptop().getPrice() * item.getQuantity())
                .sum();

        if (totalRevenue == 0) {
            return new ArrayList<>();
        }

        return productRevenue.entrySet().stream()
                .map(entry -> new ProductRevenuePercentageDTO(entry.getKey(), (entry.getValue() / totalRevenue) * 100))
                .collect(Collectors.toList());
    }

    @Override
    public BudgetRes getBudgetRes() {
        List<Order> allOrders = orderRepository.findAll();

        Double realRevenue = allOrders.stream()
                .filter(order -> order.getOrderStatus() == Order.OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotalDiscountedPrice)
                .sum();

        Long totalOrdersCount = (long) allOrders.size();

        BudgetRes budgetRes = new BudgetRes(
                userRepository.totalUser(),
                realRevenue,
                laptopRepository.getTotalProduct(),
                totalOrdersCount
        );
        return budgetRes;
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history