package com.id.akn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id.akn.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiClientController {

    private final GeminiService geminiService;
    private final PayOS payOS;

    public ApiClientController(GeminiService geminiService, PayOS payOS) {
        this.geminiService = geminiService;
        this.payOS = payOS;
    }

    @PostMapping("/chatbot/ask")
    public ResponseEntity<Map<String, String>> askChatbot(@RequestBody Map<String, Object> requestBody) {
        try {
            String userQuery = (String) requestBody.get("userQuery");
            String botResponse = geminiService.processChat(userQuery);
            return ResponseEntity.ok(Map.of("response", botResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/payment/create-payos")
    public ResponseEntity<Map<String, Object>> createPayOSPayment(@RequestBody Map<String, Object> paymentData) {
        try {

            long orderCode = Long.parseLong(String.valueOf(paymentData.get("orderCode")));
            long amount = (long) Long.parseLong(String.valueOf(paymentData.get("amount")));
            String description = String.valueOf(paymentData.get("description"));
            String cancelUrl = String.valueOf(paymentData.get("cancelUrl"));
            String returnUrl = String.valueOf(paymentData.get("returnUrl"));

            PaymentLinkItem item = PaymentLinkItem.builder()
                    .name("Thanh toan don hang #" + orderCode)
                    .quantity(1)
                    .price((long) amount)
                    .build();

            CreatePaymentLinkRequest request = CreatePaymentLinkRequest.builder()
                    .orderCode(orderCode)
                    .amount(amount)
                    .description(description)
                    .cancelUrl(cancelUrl)
                    .returnUrl(returnUrl)
                    .item(item)
                    .expiredAt((long) (System.currentTimeMillis() / 1000 + 15 * 60))
                    .build();

            CreatePaymentLinkResponse response = payOS.paymentRequests().create(request);

            return ResponseEntity.ok(Map.of(
                    "checkoutUrl", response.getCheckoutUrl(),
                    "paymentLinkId", response.getPaymentLinkId()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/payment/payos-info/{orderCode}")
    public ResponseEntity<Map<String, Object>> getPayOSPaymentInfo(@PathVariable long orderCode) {
        try {

            var paymentLink = payOS.paymentRequests().get(orderCode);

            return ResponseEntity.ok(Map.of(
                "status", paymentLink.getStatus(),
                "amount", paymentLink.getAmount(),
                "amountPaid", paymentLink.getAmountPaid(),
                "transactions", paymentLink.getTransactions()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
