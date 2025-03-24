package com.tutti.server.core.product.payload.response;

import com.tutti.server.core.product.domain.ProductCategory;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        int sortOrder,
        String name,
        ParentCategoryResponse parentCategory
) {
    public static CategoryResponse fromEntity(ProductCategory productCategory) {
        return CategoryResponse.builder()
                .id(productCategory.getId())
                .sortOrder(productCategory.getSortOrder())
                .name(productCategory.getName())
                .parentCategory(productCategory.getParentCategory() != null 
                    ? ParentCategoryResponse.fromEntity(productCategory.getParentCategory()) 
                    : null)
                .build();
    }
}

@Builder
record ParentCategoryResponse(
        Long id,
        ParentCategoryResponse parentCategory
) {
    public static ParentCategoryResponse fromEntity(ProductCategory productCategory) {
        return ParentCategoryResponse.builder()
                .id(productCategory.getId())
                .parentCategory(productCategory.getParentCategory() != null 
                    ? fromEntity(productCategory.getParentCategory()) 
                    : null)
                .build();
    }
}
