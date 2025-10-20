package com.id.akn.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id.akn.model.Brand;
import com.id.akn.repository.BrandRepository;
import com.id.akn.request.GeminiSearchCriteria;
import com.id.akn.request.LaptopDTO;
import com.id.akn.request.SearchMetadataDTO;
import com.id.akn.service.GeminiService;
import com.id.akn.service.LaptopService;
import com.id.akn.service.MetadataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.apiUrl}")
    private String geminiApiUrl;

    @Value("${gemini.apiKey}")
    private String geminiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LaptopService laptopService;
    private final BrandRepository brandRepository;
    private final MetadataService metadataService;

    public GeminiServiceImpl(LaptopService laptopService, BrandRepository brandRepository, MetadataService metadataService) {
        this.laptopService = laptopService;
        this.brandRepository = brandRepository;
        this.metadataService = metadataService;
    }

    @Override
    public String processChat(String userQuery) {
        try {
            SearchMetadataDTO metadata = metadataService.getDatabaseMetadata();

            GeminiSearchCriteria criteria = extractSearchCriteria(userQuery, metadata);

            if (criteria != null && criteria.getConversationalResponse() != null && !criteria.getConversationalResponse().isEmpty()) {
                return criteria.getConversationalResponse();
            }

            if (criteria != null && criteria.hasAnySearchCriteria()) {
                List<LaptopDTO> foundLaptops = queryDatabase(criteria);
                return generateFinalResponse(userQuery, foundLaptops);
            }

            return callGeminiSimple(userQuery);

        } catch (Exception e) {
            e.printStackTrace();
            return "Hệ thống LAPTOP SHOP đang quá tải, vui lòng thử lại sau giây lát.";
        }
    }

    private GeminiSearchCriteria extractSearchCriteria(String userQuery, SearchMetadataDTO metadata) {
        String validBrands = String.join(", ", metadata.getBrands());
        String validCategories = String.join(", ", metadata.getCategories());
        String validCpuTechs = String.join(", ", metadata.getCpuTechs());

        String prompt = """
            Bạn là nhân viên tư vấn bán hàng chuyên nghiệp của hệ thống LAPTOP Shop.
            Chúng tôi chuyên cung cấp Laptop, PC và linh kiện máy tính chính hãng.

            ### Dữ liệu hệ thống hiện có:
            - Thương hiệu (Brands): [%s]
            - Danh mục (Categories): [%s]
            - Công nghệ CPU: [%s]

            ### Nhiệm vụ:
            Phân tích câu hỏi của khách hàng: "%s"

            ### Quy tắc xử lý quan trọng:
            1. Nếu khách tìm mua máy, hỏi cấu hình, so sánh giá: Hãy trích xuất tiêu chí vào JSON, trường 'conversationalResponse' để null.
            2. Nếu khách chào hỏi (VD: "hi", "shop ơi"), hỏi địa chỉ, hoặc các vấn đề xã giao:
               - Hãy viết câu trả lời thân thiện vào trường 'conversationalResponse'.
               - Xưng hô là "LAPTOP Shop" hoặc "em/mình".
               - Giới thiệu ngắn gọn shop có bán các dòng Laptop Gaming, Văn phòng, Đồ họa.
               - Để các trường tiêu chí lọc là null.

            ### Quy tắc Mapping:
            - Brand/Category: Phải khớp chính xác danh sách trên.
            - Price: "Rẻ" -> sortPrice: "increase". "Xịn/Cao cấp" -> sortPrice: "decrease".
            - RAM: Nếu khách nói "8GB", "16GB" -> trích xuất số 8, 16 vào minRam.

            ### Output Format (JSON Only):
            {
                "minPrice": long/null,
                "maxPrice": long/null,
                "brandName": "Exact String/null",
                "categoryName": "Exact String/null",
                "minRam": int/null,
                "cpuModel": "String/null",
                "sortPrice": "increase/decrease/null",
                "conversationalResponse": "String/null"
            }
            """.formatted(validBrands, validCategories, validCpuTechs, userQuery);

        String jsonResponse = callGeminiApi(prompt);
        return parseJsonFromGemini(jsonResponse);
    }

    private List<LaptopDTO> queryDatabase(GeminiSearchCriteria criteria) {
        if (criteria == null) return Collections.emptyList();

        Byte brandId = null;
        if (criteria.getBrandName() != null) {
            Brand brand = brandRepository.findByNameNormalize(criteria.getBrandName());
            if (brand != null) brandId = brand.getId();
        }

        Pageable pageable = PageRequest.of(0, 5);

        Page<LaptopDTO> result = laptopService.getAllLaptop(
                null,
                criteria.getCategoryName(),
                null,
                null,
                null,
                criteria.getMinPrice(),
                criteria.getMaxPrice(),
                (short) 1,
                criteria.getSortPrice(),
                criteria.getMinRam(),
                null,
                null,
                null,
                null,
                null,
                brandId,
                pageable
        );

        return result.getContent();
    }

    private String generateFinalResponse(String userQuery, List<LaptopDTO> laptops) {
        if (laptops.isEmpty()) {
            return callGeminiSimple("Khách hàng hỏi: '" + userQuery + "'. Tôi tìm trong kho LAPTOP Shop nhưng không thấy sản phẩm phù hợp. Hãy xin lỗi khách khéo léo và gợi ý họ xem các danh mục khác hoặc liên hệ hotline để đặt hàng riêng.");
        }

        StringBuilder productContext = new StringBuilder();
        for (LaptopDTO l : laptops) {
            String gpuInfo = l.getGpus().isEmpty() ? "Onboard" : l.getGpus().iterator().next().getModel();
            productContext.append(String.format("- %s | Giá: %s đ | CPU: %s | RAM: %dGB | GPU: %s | Giảm giá: %.0f%%\n",
                    l.getModel(),
                    String.format("%,d", l.getPrice()),
                    l.getCpu().getModel(),
                    l.getRamMemory(),
                    gpuInfo,
                    l.getDiscountPercent()));
        }

        String prompt = """
            Vai trò: Bạn là chuyên viên tư vấn công nghệ của LAPTOP Shop.
            Câu hỏi của khách: "%s"

            Dưới đây là các sản phẩm tốt nhất tìm thấy trong kho (Top 5):
            %s

            Yêu cầu trả lời:
            - Xưng hô: "LAPTOP Shop" hoặc "Em".
            - Tư vấn nhiệt tình, ngắn gọn, tập trung vào lợi ích (Gaming, Đồ họa, Văn phòng) dựa trên cấu hình máy.
            - Nếu có giảm giá, hãy nhấn mạnh để chốt đơn.
            - Sử dụng icon (💻, 🔥, 🚀, 🎁) để sinh động.
            - Tuyệt đối không bịa đặt thông tin không có trong danh sách.
            """.formatted(userQuery, productContext.toString());

        return callGeminiApi(prompt);
    }

    private String callGeminiSimple(String userQuery) {
        return callGeminiApi("Bạn là nhân viên LAPTOP Shop. Hãy trả lời câu hỏi này một cách thân thiện, ngắn gọn và hướng về việc mua hàng: " + userQuery);
    }

    private String callGeminiApi(String prompt) {
        try {
            Map<String, Object> geminiPayload = new HashMap<>();
            geminiPayload.put("contents", new Object[]{
                    new HashMap<String, Object>() {{
                        put("parts", new Object[]{
                                new HashMap<String, String>() {{
                                    put("text", prompt);
                                }}
                        });
                    }}
            });

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(geminiPayload), headers);
            String url = geminiApiUrl + "?key=" + geminiApiKey;

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

            return extractTextFromResponse(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
            return "Xin lỗi, kết nối đến trí tuệ nhân tạo đang gặp sự cố: " + e.getMessage();
        }
    }

    private String extractTextFromResponse(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return parts.get(0).get("text");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private GeminiSearchCriteria parseJsonFromGemini(String jsonResponse) {
        try {
            String cleanJson = jsonResponse.replace("```json", "").replace("```", "").trim();
            if (!cleanJson.startsWith("{")) return null;
            return objectMapper.readValue(cleanJson, GeminiSearchCriteria.class);
        } catch (Exception e) {
            System.err.println("Lỗi parse JSON từ Gemini: " + e.getMessage());
            return null;
        }
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history