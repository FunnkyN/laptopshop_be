package com.id.akn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayosWebhookDTO {
    private String code;
    private String desc;
    private boolean success;
    private PayosWebhookDataDTO data;
    private String signature;
}
