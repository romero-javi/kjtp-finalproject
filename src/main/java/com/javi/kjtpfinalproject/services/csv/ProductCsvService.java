package com.javi.kjtpfinalproject.services.csv;

import com.javi.kjtpfinalproject.model.ProductCsvRecord;

import java.io.File;
import java.util.List;

public interface ProductCsvService {
    List<ProductCsvRecord> convertCSV(File csvFile);
}
