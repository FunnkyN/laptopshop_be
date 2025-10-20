package com.id.akn.serviceimpl;

import java.time.LocalDateTime;
import java.util.*;

import com.id.akn.exception.*;
import com.id.akn.model.*;
import com.id.akn.repository.*;
import com.id.akn.request.CartItemDTO;
import com.id.akn.request.OrderDTO;
import com.id.akn.service.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private CartItemService cartItemService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemRepository orderItemRepository;
	private LaptopRepository laptopRepository;
	private ColorService colorService;
	private LaptopColorRepository laptopColorRepository;

	@Transactional
	@Override
	public Order createOrder(User user, OrderDTO orderDTO) throws UserException, LaptopException, ColorException, CartItemException {

		Address shippingAddress = orderDTO.getShippingAddress();
		Address savedAddress = null;

		if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
			for (Address existingAddr : user.getAddresses()) {
				boolean isNameMatch = existingAddr.getName().trim().equalsIgnoreCase(shippingAddress.getName().trim());
				boolean isStreetMatch = existingAddr.getStreetAddress().trim().equalsIgnoreCase(shippingAddress.getStreetAddress().trim());
				boolean isCityMatch = existingAddr.getCity().trim().equalsIgnoreCase(shippingAddress.getCity().trim());
				boolean isPhoneMatch = existingAddr.getPhoneNumber().trim().equals(shippingAddress.getPhoneNumber().trim());

				if (isNameMatch && isStreetMatch && isCityMatch && isPhoneMatch) {
					savedAddress = existingAddr;
					break;
				}
			}
		}

		if (savedAddress == null) {
			shippingAddress.setUser(user);
			savedAddress = addressRepository.save(shippingAddress);
			user.getAddresses().add(savedAddress);
		}

		List<OrderItem> orderItems = new ArrayList<>();
		float totalPrice = 0;
		float totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItemDTO item : orderDTO.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			Laptop laptop = laptopRepository.findById(item.getLaptopId())
					.orElseThrow(() -> new LaptopException("Laptop not found"));
			orderItem.setLaptop(laptop);
			orderItem.setColor(colorService.getColorById(item.getColorId()));
			orderItem.setQuantity(item.getQuantity());

			float itemTotalPrice = laptop.getPrice() * item.getQuantity();
			totalPrice += itemTotalPrice;

			float itemDiscountedPrice = itemTotalPrice * (100 - laptop.getDiscountPercent()) / 100;
			totalDiscountedPrice += itemDiscountedPrice;

			totalItem += item.getQuantity();

			cartItemService.removeCartItem(user.getId(), item.getId());
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);

			Color color = orderItem.getColor();

			LaptopColor laptopColor = laptopColorRepository.findByLaptopAndColor(laptop, color)
					.orElseThrow(() -> new LaptopException("Không tìm thấy kho hàng cho sản phẩm: " + laptop.getModel() + " - " + color.getName()));

			if (laptopColor.getQuantity() < orderItem.getQuantity()) {
				throw new LaptopException("Không đủ hàng. Sản phẩm: " + laptop.getModel() + " - " + color.getName() + ". Chỉ còn: " + laptopColor.getQuantity());
			}

			laptopColor.setQuantity((short) (laptopColor.getQuantity() - orderItem.getQuantity()));
			laptopColorRepository.save(laptopColor);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);

		createdOrder.setShippingAddress(savedAddress);

		createdOrder.setTotalPrice(totalPrice);
		createdOrder.setTotalDiscountedPrice(totalDiscountedPrice);
		createdOrder.setTotalItem(totalItem);
		createdOrder.setCreatedAt(LocalDateTime.now());
		createdOrder.setPaymentMethod(orderDTO.getPaymentMethod());
		createdOrder.setPaymentStatus(Order.PaymentStatus.PENDING);
		createdOrder.setOrderStatus(Order.OrderStatus.PENDING);
		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}

		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not foundwith id "+orderId));
	}

	@Override
	public Page<Order> userOrdersHistory(Long userId, Order.OrderStatus  paymentStatus, int page, int size)  {
		Pageable pageable = PageRequest.of(page - 1, size);
		if (paymentStatus != null) {
			return orderRepository.findByOrderStatusAndUserIdOrderByCreatedAtDesc(paymentStatus, userId, pageable);
		} else {
			return orderRepository.findByUserId(userId, pageable);
		}
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.CONFIRMED);

		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.DELIVERED);
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);

		for (OrderItem orderItem : order.getOrderItems()) {
			Laptop laptop = orderItem.getLaptop();
			Color color = orderItem.getColor();

			LaptopColor laptopColor = laptopColorRepository.findByLaptopAndColor(laptop, color)
					.orElseThrow(() -> new OrderException("Không tìm thấy kho hàng để hoàn trả cho sản phẩm: " + laptop.getModel() + " - " + color.getName()));

			laptopColor.setQuantity((short) (laptopColor.getQuantity() + orderItem.getQuantity()));
			laptopColorRepository.save(laptopColor);
		}

		order.setOrderStatus(Order.OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}
	@Override
	public void updatePaymentStatus(Long txnRef, Order.PaymentStatus status) throws OrderException {
		Order order = findOrderById(txnRef);

		if(status.equals(Order.PaymentStatus.FAILED)) {
			for (OrderItem orderItem : order.getOrderItems()) {
				Laptop laptop = orderItem.getLaptop();
				Color color = orderItem.getColor();

				LaptopColor laptopColor = laptopColorRepository.findByLaptopAndColor(laptop, color)
						.orElseThrow(() -> new OrderException("Không tìm thấy kho hàng để hoàn trả cho sản phẩm: " + laptop.getModel() + " - " + color.getName()));

				laptopColor.setQuantity((short) (laptopColor.getQuantity() + orderItem.getQuantity()));
				laptopColorRepository.save(laptopColor);
			}
		}

		order.setPaymentStatus(status);
		orderRepository.save(order);
	}

	@Transactional
	@Override
	public Order updateOrderStatus(Long orderId, Long userId, Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus) throws OrderException {
		int rowsAffected = orderRepository.updateOrderStatus(orderId, userId, orderStatus, paymentStatus);
		if (rowsAffected > 0) {
			return orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not found."));
		} else {
			throw new OrderException("Order not found or user not authorized to update this order.");
		}
	}

	@Transactional
	@Override
	public void deleteOrder(Long orderId, Long userId) {
		orderRepository.deleteById(orderId);
	}

}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history