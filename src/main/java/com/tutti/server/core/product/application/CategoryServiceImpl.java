package com.tutti.server.core.product.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutti.server.core.product.infrastructure.CategoryRepository;
import com.tutti.server.core.product.payload.response.CategoryResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAllWithParent().stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
