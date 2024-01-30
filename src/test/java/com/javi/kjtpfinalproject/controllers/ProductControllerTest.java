package com.javi.kjtpfinalproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.kjtpfinalproject.dto.product.ProductDTO;
import com.javi.kjtpfinalproject.mappers.ProductMapper;
import com.javi.kjtpfinalproject.services.ProductService;
import com.javi.kjtpfinalproject.services.csv.impl.ProductCsvServiceImpl;
import com.javi.kjtpfinalproject.shared.constants.ControllerPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Spy
    ProductCsvServiceImpl productCsvService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<ProductDTO> productArgumentCaptor;

    @DisplayName("Product Controller Test - Testing getting list of products")
    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void testListProducts() throws Exception {
        File file = ResourceUtils.getFile("classpath:csv/product.csv");
        List<ProductDTO> products = productCsvService.convertCSV(file).stream()
                .map(ProductMapper.INSTANCE::productCsvRecordToProductDto)
                .toList();

        given(productService.findAll(anyInt(), anyInt())).willReturn(new PageImpl<>(products));

        mockMvc.perform(get(ControllerPaths.PRODUCTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("pageNumber", "1")
                        .queryParam("pageSize", "20")
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", greaterThanOrEqualTo(20)));

        then(productService).should().findAll(anyInt(), anyInt());
    }
}