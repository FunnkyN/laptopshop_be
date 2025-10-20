package com.id.akn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchMetadataDTO {
    private List<String> brands;
    private List<String> categories;
    private List<String> cpuTechs;
    private List<String> colors;
}
