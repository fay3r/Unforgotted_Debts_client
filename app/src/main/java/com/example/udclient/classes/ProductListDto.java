package com.example.udclient.classes;

import java.io.Serializable;
import java.util.List;

public class ProductListDto implements Serializable {
    private List<ProductDto> productDtoList;

    public ProductListDto() {
    }

    public ProductListDto(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }
}
