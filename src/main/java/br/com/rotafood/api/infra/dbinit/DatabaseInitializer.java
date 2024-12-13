package br.com.rotafood.api.infra.dbinit;

import br.com.rotafood.api.application.service.DefaultProductPopulateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DefaultProductPopulateService populateService;

    public DatabaseInitializer(DefaultProductPopulateService populateService) {
        this.populateService = populateService;
    }

    @Override
    public void run(String... args) throws Exception {
        String jsonFilePath = "src/main/resources/db/populate/populateDbDefaultProduct.json";
        populateService.populateDatabase(jsonFilePath);
        System.out.println("Database populated successfully!");
    }
}
