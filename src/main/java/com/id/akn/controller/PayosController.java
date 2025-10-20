package com.id.akn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.id.akn.response.ApiRes;
import com.id.akn.service.PayosService;

import lombok.AllArgsConstructor;
import vn.payos.PayOS;
import vn.payos.model.webhooks.WebhookData;

@RestController
@RequestMapping("/api/payos")
@AllArgsConstructor
public class PayosController {

    private final PayosService payosService;
    private final PayOS payOS;

    @PostMapping("/webhook")
    public ResponseEntity<ApiRes> handleWebhook(@RequestBody ObjectNode body) {
        try {

            WebhookData webhookData = payOS.webhooks().verify(body);

            payosService.handleWebhook(webhookData);

            return new ResponseEntity<>(new ApiRes("Webhook processed successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiRes("Webhook processing failed: " + e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history