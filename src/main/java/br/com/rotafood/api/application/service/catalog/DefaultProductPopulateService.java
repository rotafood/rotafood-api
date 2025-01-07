package br.com.rotafood.api.application.service.catalog;

import br.com.rotafood.api.domain.entity.catalog.DefaultProduct;
import br.com.rotafood.api.domain.repository.DefaultProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductPopulateService {

    @Autowired
    private DefaultProductRepository defaultProductRepository;

    public void populateDatabase(String jsonFilePath) throws IOException {
        if (defaultProductRepository.count() > 0) {
            System.out.println("Banco já populado com DefaultProduct. Nenhuma ação necessária.");
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));

        List<DefaultProduct> products = new ArrayList<>();
        for (JsonNode node : rootNode) {
            DefaultProduct product = new DefaultProduct();
            product.setId(UUID.fromString(node.get("id").asText()));
            product.setName(node.get("name").asText());
            product.setDescription(node.get("description").asText());
            product.setEan(node.get("productEan").asText());

            JsonNode imageResource = node.get("imageResource");
            if (imageResource != null) {
                String baseUrl = imageResource.get("baseUrl").asText();
                String path = imageResource.get("path").asText();
                product.setIFoodImagePath(baseUrl + path);
            }

            products.add(product);
        }

        defaultProductRepository.saveAll(products);
    }
}
