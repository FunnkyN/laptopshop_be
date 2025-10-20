package com.id.akn.service;

import com.id.akn.request.SearchMetadataDTO;

public interface MetadataService {
    SearchMetadataDTO getDatabaseMetadata();
    void clearCache();
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history