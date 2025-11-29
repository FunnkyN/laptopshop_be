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
  ;
    private String categoryName;

    private String conversationalResponse;

    public boolean hasAnySearchCriteria() {
        return minPrice !=  private String categoryName;

    private String conver  private String categoryName;

    private String conver  private String categoryName;

    private String conver null || maxPrice != null ||
               (brandName != null && !brandName.isEmpty()) ||
               minRam != null ||
               (cpuModel != null && !cpuModel.isEmpty()) ||
               (categoryName != null && !categoryName.isEmpty()) ||
               (sortPrice != null && !sortPrice.isEmpty());
    }
