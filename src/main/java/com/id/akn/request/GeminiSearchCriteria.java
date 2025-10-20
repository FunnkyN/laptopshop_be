package com.id.akn.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiSearchCriteria {
    private Long minPrice;
    private Long maxPrice;
    private String brandName;
    private Byte minRam;
    private String cpuModel;
    private String purpose;
    private String sortPrice;
    private String categoryName;

    private String conversationalResponse;

    public boolean hasAnySearchCriteria() {
        return minPrice != null || maxPrice != null ||
               (brandName != null && !brandName.isEmpty()) ||
               minRam != null ||
               (cpuModel != null && !cpuModel.isEmpty()) ||
               (categoryName != null && !categoryName.isEmpty()) ||
               (sortPrice != null && !sortPrice.isEmpty());
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history