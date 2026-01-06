package com.id.akn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CaptchaService {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);
    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    // Inject giá trị từ file application.properties
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate;

    // Sử dụng Constructor Injection cho RestTemplate (Best Practice)
    public CaptchaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Xác thực Google ReCaptcha Token
     *
     * @param recaptchaToken Token nhận được từ Client
     * @return true nếu xác thực thành công, false nếu thất bại
     */
    public boolean verifyCaptcha(String recaptchaToken) {
        // 1. Kiểm tra input đầu vào
        if (!StringUtils.hasText(recaptchaToken)) {
            logger.warn("Recaptcha token is empty or null");
            return false;
        }

        try {
            // 2. Chuẩn bị Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 3. Chuẩn bị Body
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("secret", recaptchaSecret);
            map.add("response", recaptchaToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            // 4. Gọi API Google
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    GOOGLE_RECAPTCHA_VERIFY_URL,
                    request,
                    Map.class
            );

            // 5. Xử lý kết quả trả về
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                logger.info("Captcha verified successfully");
                return true;
            } else {
                // Log lỗi chi tiết từ Google nếu có (error-codes)
                logger.warn("Captcha verification failed. Response: {}", responseBody);
                return false;
            }

        } catch (RestClientException e) {
            logger.error("Error calling Google Recaptcha API: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during captcha verification", e);
            return false;
        }
    }
}