package com.id.akn.service;

import vn.payos.model.webhooks.WebhookData;

public interface PayosService {
    void handleWebhook(WebhookData webhookData) throws Exception;
}
