package com.id.akn.controller;

import com.id.akn.config.VNPAYConfig;
import com.id.akn.exception.OrderException;
import com.id.akn.model.Order;
import com.id.akn.repository.OrderRepository;
import com.id.akn.response.PaymentLinkRes;
import com.id.akn.response.VNPAYRes;
import com.id.akn.service.OrderService;
import com.id.akn.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private VNPAYConfig vnpayConfig;
    private OrderService orderService;
    private PaymentService paymentService;
    private OrderRepository orderRepository;

    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentLinkRes> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt,
                                                            HttpServletRequest req) throws IOException, OrderException {

        Order order = orderService.findOrderById(orderId);
        long amount = (long)(order.getTotalDiscountedPrice() * 100);
        String bankCode = order.getPaymentMethod().toString();
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = vnpayConfig.getIpAddress(req);

        PaymentLinkRes response = paymentService.createPaymentLink(amount, bankCode, vnp_IpAddr, vnp_TxnRef);
        order.setPaymentStatus(Order.PaymentStatus.PROCESSING);
        orderRepository.save(order);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/ipn")
    public ResponseEntity<VNPAYRes> handleIPN(@RequestParam Map<String, String> allParams) {
        try {
            String responseCode = paymentService.handleVnpayIpn(allParams);
            return switch (responseCode) {
                case "00" -> new ResponseEntity<>(new VNPAYRes("00", "Confirm Success"), HttpStatus.OK);
                case "02" -> new ResponseEntity<>(new VNPAYRes("02", "Order already confirmed"), HttpStatus.OK);
                case "97" -> new ResponseEntity<>(new VNPAYRes("97", "Invalid Checksum"), HttpStatus.OK);
                default -> new ResponseEntity<>(new VNPAYRes("99", "Unknown error"), HttpStatus.OK);
            };
        } catch (OrderException e) {
            return new ResponseEntity<>(new VNPAYRes("01", e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new VNPAYRes("99", "Unknown error"), HttpStatus.OK);
        }
    }

    @GetMapping("/return")
    public ResponseEntity<VNPAYRes> handlePaymentReturn(@RequestParam Map<String, String> allParams) {
        Map<String, String> fields = new HashMap<>(allParams);
        String vnp_SecureHash = allParams.get("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String signValue = vnpayConfig.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(allParams.get("vnp_ResponseCode"))) {
                return new ResponseEntity<>(new VNPAYRes("00", "Giao dịch thành công"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new VNPAYRes("09", "Giao dịch không thành công"), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new VNPAYRes("97", "Lỗi: Chữ ký không hợp lệ"), HttpStatus.OK);
        }
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history