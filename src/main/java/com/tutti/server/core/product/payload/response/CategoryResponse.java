package com.tutti.server.core.product.payload.response;

import com.tutti.server.core.product.domain.ProductCategory;

import lombok.Builder;

@Builder
public record CategoryResponse(

        Long id,
        int sortOrder,
        String name,
        CategoryResponse parentCategory

) {

    public static CategoryResponse fromEntity(ProductCategory productCategory) {
        return CategoryResponse.builder()
                .id(productCategory.getId())
                .sortOrder(productCategory.getSortOrder())
                .name(productCategory.getName())
                .parentCategory(productCategory.getParentCategory() != null 
                    ? fromEntity(productCategory.getParentCategory()) 
                    : null)
                .build();
    }
}
