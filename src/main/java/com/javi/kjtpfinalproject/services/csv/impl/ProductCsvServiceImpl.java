package com.javi.kjtpfinalproject.services.csv.impl;

import com.javi.kjtpfinalproject.model.ProductCsvRecord;
import com.javi.kjtpfinalproject.services.csv.ProductCsvService;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class ProductCsvServiceImpl implements ProductCsvService {
    @Override
    public List<ProductCsvRecord> convertCSV(File csvFile) {
        try {
            return new CsvToBeanBuilder<ProductCsvRecord>(new FileReader(csvFile))
                    .withType(ProductCsvRecord.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
