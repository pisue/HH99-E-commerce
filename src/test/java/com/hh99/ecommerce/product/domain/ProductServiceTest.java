package com.hh99.ecommerce.product.domain;

import com.hh99.ecommerce.product.domain.dto.ProductDomain;
import com.hh99.ecommerce.product.domain.exception.ProductNotFoundException;
import com.hh99.ecommerce.product.infra.Product;
import com.hh99.ecommerce.product.infra.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("조회된 상품을 가져옵니다.")
    void getProduct() {
        //Given
        Long id = 1L;
        Product product = Product.builder()
                .id(1L)
                .price(new BigDecimal("1000"))
                .name("냄비포트")
                .description("값싼 냄비포트 입니다.")
                .stock(100)
                .regDate(LocalDateTime.now())
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        //When
        ProductDomain productDomain = productService.getProduct(id);
        Product result = productDomain.toEntity();

        //Then
        verify(productRepository, times(1)).findById(id);
        assertEquals(result.getPrice(), product.getPrice());
        assertEquals(result.getId(), product.getId());
    }

    @Test
    @DisplayName("조회할 상품이 없습니다")
    void getProductNotFound() {
        // Given
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> productService.getProduct(id)
        );
        
        assertEquals("조회하신 상품을 찾을 수 없습니다.", exception.getMessage());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("재고업데이트 성공")
    void updateProductStock() {
        // Given
        Long id = 1L;
        int newStock = 98;
        Product product = Product.builder()
                .id(id)
                .price(new BigDecimal("1000"))
                .name("냄비포트")
                .description("값싼 냄비포트 입니다.")
                .stock(100)
                .regDate(LocalDateTime.now())
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        productService.updateProductStock(id, newStock);

        // Then
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(newStock, product.getStock());
        assertEquals(98, product.getStock());
    }

    @Test
    @DisplayName("재고업데이트 실패 - 상품이 존재하지 않음")
    void updateProductStockNotFound() {
        // Given
        Long id = 1L;
        int newStock = 98;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> productService.updateProductStock(id, newStock)
        );
        
        assertEquals("조회하신 상품을 찾을 수 없습니다.", exception.getMessage());
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("모든 상품 조회")
    void getProducts() {
        //Given
        Product product1 = Product.builder()
                .id(1L)
                .price(new BigDecimal("1000"))
                .name("냄비포트")
                .description("값싼 냄비포트 입니다.")
                .stock(100)
                .regDate(LocalDateTime.now())
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .price(new BigDecimal("2000"))
                .name("프라이팬")
                .description("고급 프라이팬 입니다.")
                .stock(50)
                .regDate(LocalDateTime.now())
                .build();

        List<Product> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        //When
        List<ProductDomain> productDomains = productService.getProducts();
        List<Product> results = productDomains.stream().map(ProductDomain::toEntity).toList();

        //Then
        verify(productRepository, times(1)).findAll();
        assertEquals(products.size(), results.size());
        assertEquals(products.get(0).getId(), results.get(0).getId());
    }

    @Test
    @DisplayName("상품 등록")
    void create() {
        //Given
        ProductDomain productDomain = ProductDomain.builder()
                .price(new BigDecimal("1000"))
                .name("냄비포트")
                .description("값싼 냄비포트 입니다.")
                .stock(100)
                .build();
        //When
        productService.create(productDomain);

        //Then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("인기 상품 조회")
    void getPopularProducts() {
        //Given
        LocalDateTime now = LocalDateTime.now();
        Integer topNumber = 2;
        Integer lastDays = 3;

        LocalDateTime startDateTime = now.minusDays(lastDays).with(LocalTime.MIN);
        LocalDateTime endDateTime = now.minusDays(1).with(LocalTime.MAX);

        Product product1 = Product.builder()
                .id(1L)
                .price(new BigDecimal("1000"))
                .name("냄비포트")
                .description("값싼 냄비포트 입니다.")
                .stock(100)
                .regDate(LocalDateTime.now())
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .price(new BigDecimal("2000"))
                .name("프라이팬")
                .description("고급 프라이팬 입니다.")
                .stock(50)
                .regDate(LocalDateTime.now())
                .build();

        List<Product> popularProducts = List.of(product1, product2);
        when(productRepository.findPopularProducts(topNumber, startDateTime, endDateTime)).thenReturn(popularProducts);

        //When
        List<ProductDomain> result = productService.getPopularProducts(topNumber, startDateTime, endDateTime);

        //Then
        verify(productRepository, times(1)).findPopularProducts(topNumber, startDateTime, endDateTime);
        assertEquals(popularProducts.size(), result.size());
        assertEquals(popularProducts.get(0).getId(), result.get(0).getId());
    }
}