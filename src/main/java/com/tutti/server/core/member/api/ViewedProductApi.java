package com.tutti.server.core.member.api;

import static com.tutti.server.core.member.utils.TokenExtractor.extractToken;

import com.tutti.server.core.member.application.ViewedProductServiceImpl;
import com.tutti.server.core.member.domain.Member;
import com.tutti.server.core.member.infrastructure.MemberRepository;
import com.tutti.server.core.member.jwt.JWTUtil;
import com.tutti.server.core.product.payload.response.ProductResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/viewed_products")
@SecurityRequirement(name = "Bearer Authentication")
public class ViewedProductApi implements ViewedProductApiSpec {

    private final ViewedProductServiceImpl viewedProductService;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getViewedProducts(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String token = extractToken(authHeader);
        jwtUtil.validateAccessToken(token);

        Long memberId = jwtUtil.getMemberId(token);
        Member member = memberRepository.findOne(memberId);
        List<ProductResponse> result = viewedProductService.getViewedProductResponses(member, page,
                size);

        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "viewed_products", result
        ));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteViewedProduct(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authHeader) {

        String token = extractToken(authHeader);
        jwtUtil.validateAccessToken(token);

        Long memberId = jwtUtil.getMemberId(token);
        Member member = memberRepository.findOne(memberId);

        viewedProductService.deleteViewedProduct(member, productId);

        return ResponseEntity.ok(Map.of(
                "message", "해당 상품이 최근 본 상품 목록에서 삭제되었습니다.",
                "product_id", productId
        ));
    }
}
