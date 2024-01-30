package com.javi.kjtpfinalproject.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCsvRecord {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "brand")
    private String brand;
    @CsvBindByName(column = "quantity")
    private Integer quantity;
    @CsvBindByName(column = "price")
    private BigDecimal price;
}
