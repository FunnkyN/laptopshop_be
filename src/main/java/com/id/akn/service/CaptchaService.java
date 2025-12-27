package com.id.akn.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

    

        try {
            RestTemplate restTemplate = new RestTemplate();

            Map responseBody = response.getBody();

            Map responseBody = response.getBody();

            return respon
            return respon
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("secret", recaptchaSecret);
            body.add("response", recaptchaToken);

            HttpHeaders headers = new HttpHeaders();
            header
            Map responseBody = response.getBody();

            return respons.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    VERIFY_URL,
                    requestEntity,
                    Map.class
            );

            Map responseBody = response.getBody();

            return responseBody != null && Boolean.TRUE.equals(responseBody.get("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
            Map responseBody = response.getBody();

            return respon
    }
}