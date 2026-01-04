package com.id.akn.controller;

import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.model.UserRole; // Giả sử bạn có Enum này
import com.id.akn.response.BudgetRes;
import com.id.akn.response.ProductRevenuePercentageDTO;
import com.id.akn.response.YearlyRevenueDTO;
import com.id.akn.response.YearlyRevenueRes;
import com.id.akn.service.RevenueStatisticsService;
import com.id.akn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600) // Cho phép Frontend truy cập
@AllArgsConstructor
@Tag(name = "Dashboard Statistics", description = "API thống kê doanh thu và ngân sách")
public class DashboardController {

    private final RevenueStatisticsService revenueStatisticsService;
    private final UserService userService;

    // Helper method để kiểm tra Admin (nếu cần)
    private void validateAdminAccess(String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        
    }

    @Operation(summary = "Lấy tổng doanh thu theo từng năm (List)")
    @GetMapping("/revenue/yearly-summary")
    public ResponseEntity<List<YearlyRevenueRes>> getYearlyRevenueSummary(
            @RequestHeader("Authorization") String jwt) throws UserException {
        // Kiểm tra quyền truy cập trước
        validateAdminAccess(jwt);
        
        List<YearlyRevenueRes> yearlyRevenues = revenueStatisticsService.calculateTotalRevenuePerYear();
        return ResponseEntity.ok(yearlyRevenues);
    }

    @Operation(summary = "Lấy chi tiết doanh thu của một năm cụ thể")
    @GetMapping("/revenue/yearly-detail")
    public ResponseEntity<YearlyRevenueDTO> getYearlyRevenueDetail(
            @RequestParam int year, 
            @RequestHeader("Authorization") String jwt) throws UserException {
        validateAdminAccess(jwt);

        YearlyRevenueDTO yearlyRevenue = revenueStatisticsService.calculateYearlyRevenueWithMonthlyData(year);
        return ResponseEntity.ok(yearlyRevenue);
    }

    @Operation(summary = "Lấy tỷ lệ phần trăm doanh thu theo sản phẩm")
    @GetMapping("/product-percentages")
    public ResponseEntity<List<ProductRevenuePercentageDTO>> getProductRevenuePercentages(
            @RequestHeader("Authorization") String jwt) throws UserException {
        validateAdminAccess(jwt);

        List<ProductRevenuePercentageDTO> stats = revenueStatisticsService.calculateProductRevenuePercentages();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Lấy thông tin ngân sách tổng quan")
    @GetMapping("/budget-overview")
    public ResponseEntity<BudgetRes> getTotalBudget(
            @RequestHeader("Authorization") String jwt) throws UserException {
        validateAdminAccess(jwt);

        BudgetRes budgetRes = revenueStatisticsService.getBudgetRes();
        return ResponseEntity.ok(budgetRes);
    }
}