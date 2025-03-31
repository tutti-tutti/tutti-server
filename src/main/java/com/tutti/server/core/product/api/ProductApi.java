package com.tutti.server.core.product.api;

import com.tutti.server.core.member.application.CustomUserDetails;
import com.tutti.server.core.member.application.ViewedProductServiceSpec;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.product.application.ProductService;
import com.tutti.server.core.product.domain.Product;
import com.tutti.server.core.product.infrastructure.ProductRepository;
import com.tutti.server.core.product.payload.response.ProductItemResponse;
import com.tutti.server.core.product.payload.response.ProductResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApi implements ProductApiSpec {

    private final ProductService productService;
    private final ViewedProductServiceSpec viewedProductService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    @GetMapping("latest-list")
    public List<ProductResponse> getAllProductsByCreated() {
        return productService.getAllProductsByCreated();
    }

    @Override
    @GetMapping("latest-list/page")
    public Slice<ProductResponse> getAllProductsByCreatedWithPagination(
            @RequestParam(name = "cursorId", required = false) Long cursorId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // Validate sort parameters
        Sort sort = pageable.getSort();
        if (sort == null || sort.isEmpty()) {
            throw new DomainException(ExceptionType.INVALID_SORT_PARAMETER);
        }

        // Validate pagination parameters
        if (pageable.getPageSize() <= 0 || pageable.getPageSize() > 100) {
            throw new DomainException(ExceptionType.INVALID_PAGINATION_PARAMETER);
        }

        // Ensure we're using a valid sort
        Sort validSort = Sort.by(Sort.Direction.DESC, "createdAt", "id");
        Pageable validPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                validSort);

        return productService.getAllProductsByCreated(cursorId, validPageable);
    }

    @Override
    @GetMapping("/{productId}")
    public ProductItemResponse getProductItemsWithOptions(
            @PathVariable(name = "productId") long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ProductItemResponse response = productService.getProductItemsWithOptions(productId);

        if (userDetails != null) {
            Long memberId = userDetails.getMemberId();
            Member member = memberRepository.findOne(memberId);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DomainException(ExceptionType.PRODUCT_NOT_FOUND));

            viewedProductService.saveViewedProduct(member, product);
        }
        return response;
    }
}
