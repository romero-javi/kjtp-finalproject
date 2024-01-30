package com.javi.kjtpfinalproject.bootstrap;

import com.javi.kjtpfinalproject.entities.Product;
import com.javi.kjtpfinalproject.model.ProductCsvRecord;
import com.javi.kjtpfinalproject.repositories.ProductRepository;
import com.javi.kjtpfinalproject.services.csv.ProductCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final ProductCsvService productCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() < 20) {
            loadCsvProductData();
        }
    }

    private void loadCsvProductData() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csv/product.csv");
        List<ProductCsvRecord> productCsvRecords = productCsvService.convertCSV(file);

        productCsvRecords.forEach(
                productCsvRecord -> productRepository.save(
                        Product.builder()
                                .name(productCsvRecord.getName())
                                .brand(productCsvRecord.getBrand())
                                .quantity(productCsvRecord.getQuantity())
                                .price(productCsvRecord.getPrice())
                                .version(1L)
                                .build()
                )
        );
    }
}
