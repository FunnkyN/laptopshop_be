package com.id.akn.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDTO {
    private Integer id;
    private Byte brandId;
    private String brandName;
    @NotNull
  ;
    private Short osVersionId;
    private String osVersion;
    private String keyboardType;
    private String batteryCharger;
    private String design;
    private Set<LaptopColorDTO> laptopColors;
    private Set<CategoryDTO> categories;
    private String origin;
    private byte warranty;
    prrivate Set<LaptopColorDTO> laptopColors;
    private Set<CategoryDTO> categories;
    private String origin;
    private byte warrantivate long price;
    private Set<String> imageUrls;
    private float discountPercent;
    private Short status;
}
