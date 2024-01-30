package com.javi.kjtpfinalproject.services.csv;

import com.javi.kjtpfinalproject.model.ProductCsvRecord;
import com.javi.kjtpfinalproject.services.csv.impl.ProductCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ProductCsvServiceTest {
    ProductCsvService productCsvService = new ProductCsvServiceImpl();

    @Test
    void testConvertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csv/product.csv");
        List<ProductCsvRecord> records = productCsvService.convertCSV(file);
        assertThat(records, notNullValue());
        assertThat(records.size(), greaterThanOrEqualTo(0));
    }
}