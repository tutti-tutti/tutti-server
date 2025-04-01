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
import com.tutti.server.core.product.payload.response.ProductSliceResponse;
import com.tutti.server.core.support.exception.DomainException;
import com.tutti.server.core.support.exception.ExceptionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ProductSliceResponse getAllProductsByCreatedWithPagination(
            @RequestParam(name = "cursorId", required = false) Long cursorId,
            @RequestParam(name = "size", defaultValue = "20") int size) {

        return productService.getAllProductsByCreated(cursorId, size);
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

    @Override
    @GetMapping("recommend")
    public List<ProductResponse> getProductsByLikes(
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return productService.getProductsByLikes(size);
    }
}
