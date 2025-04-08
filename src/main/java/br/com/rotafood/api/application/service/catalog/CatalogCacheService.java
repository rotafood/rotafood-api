package br.com.rotafood.api.application.service.catalog;

import br.com.rotafood.api.application.dto.catalog.FullCategoryDto;
import br.com.rotafood.api.domain.repository.CategoryRepository;
import br.com.rotafood.api.infra.minio.MinioStorageService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;


@Service
public class CatalogCacheService {

    private final MinioStorageService minioStorageService;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public CatalogCacheService(MinioStorageService minioStorageService, CategoryRepository categoryRepository, ObjectMapper objectMapper) {
        this.minioStorageService = minioStorageService;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Async("virtualThreadExecutor")
    @Transactional
    public void updateCatalogCache(UUID merchantId) {
        try {
            System.out.println("⏳ Atualizando cache do catálogo do merchant " + merchantId + " em background...");
            var categories = categoryRepository.findAllByMerchantIdWithItems(merchantId)
                                    .stream()
                                    .map(FullCategoryDto::new)
                                    .toList();

            String json = objectMapper.writeValueAsString(categories);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

            String objectName = "catalogs/" + merchantId + ".json";

            minioStorageService.uploadFile(bucketName, objectName, inputStream, "application/json");

            System.out.println("✅ Cache do catálogo atualizado para merchant " + merchantId);
        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar cache do catálogo (merchant " + merchantId + "): " + e.getMessage());
            e.printStackTrace();
        }
    }

}
