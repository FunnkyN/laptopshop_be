package com.id.akn.serviceimpl;

import com.id.akn.model.Order;
import com.id.akn.service.OrderService;
import com.id.akn.service.PayosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.model.webhooks.WebhookData;

@Service
@RequiredArgsConstructor
public class PayosServiceImpl implements PayosService {

    private final OrderService orderService;

    @Override
    public void handleWebhook(WebhookData webhookData) throws Exception {
        String code = webhookData.getCode();
        String desc = webhookData.getDesc();
        long orderCode = webhookData.getOrderCode();

        System.out.println("Webhook received for Order: " + orderCode + " | Code: " + code + " | Desc: " + desc);

        if ("00".equals(code)) {
            try {

                orderService.updatePaymentStatus(orderCode, Order.PaymentStatus.COMPLETED);
                System.out.println("Order " + orderCode + " updated to COMPLETED.");
            } catch (Exception e) {

                System.err.println("Webhook Warning: Không cập nhật được đơn hàng " + orderCode + ". Lỗi: " + e.getMessage());

            }
        }
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history