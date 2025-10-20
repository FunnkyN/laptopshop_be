package com.id.akn.serviceimpl;

import com.id.akn.model.Brand;
import com.id.akn.model.Category;
import com.id.akn.model.Color;
import com.id.akn.model.CpuTech;
import com.id.akn.repository.BrandRepository;
import com.id.akn.repository.CategoryRepository;
import com.id.akn.repository.ColorRepository;
import com.id.akn.repository.CpuTechRepository;
import com.id.akn.request.SearchMetadataDTO;
import com.id.akn.service.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CpuTechRepository cpuTechRepository;
    private final ColorRepository colorRepository;

    private SearchMetadataDTO cachedMetadata;
    private long lastFetchTime = 0;
    private static final long CACHE_DURATION = 3600000;

    @Override
    public synchronized SearchMetadataDTO getDatabaseMetadata() {
        long currentTime = System.currentTimeMillis();

        if (cachedMetadata != null && (currentTime - lastFetchTime < CACHE_DURATION)) {
            return cachedMetadata;
        }

        List<String> brands = brandRepository.findAll().stream()
                .map(Brand::getName).collect(Collectors.toList());

        List<String> categories = categoryRepository.findAll().stream()
                .map(Category::getName).collect(Collectors.toList());

        List<String> cpuTechs = cpuTechRepository.findAll().stream()
                .map(CpuTech::getName).collect(Collectors.toList());

        List<String> colors = colorRepository.findAll().stream()
                .map(Color::getName).collect(Collectors.toList());

        cachedMetadata = new SearchMetadataDTO(brands, categories, cpuTechs, colors);
        lastFetchTime = currentTime;

        return cachedMetadata;
    }

    @Override
    public void clearCache() {
        this.cachedMetadata = null;
        this.lastFetchTime = 0;
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history