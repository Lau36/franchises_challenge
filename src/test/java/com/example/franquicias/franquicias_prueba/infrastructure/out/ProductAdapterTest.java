package com.example.franquicias.franquicias_prueba.infrastructure.out;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.out.adapter.ProductAdapter;
import com.example.franquicias.franquicias_prueba.infrastructure.out.dto.ProductStockDTO;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductBranchEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductBranchRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductAdapterTest {
    @Mock
    private IProductBranchRepository productBranchRepository;

    @Mock
    private IProductRespository productRespository;

    @InjectMocks
    private ProductAdapter productAdapter;

    @Test
    public void addProduct_shouldSaveProductTest() {
        Product product = new Product();
        product.setName("Test Product");
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name("Test Product")
                .build();

        when(productRespository.save(any(ProductEntity.class))).thenReturn(Mono.just(productEntity));

        Mono<Product> result = productAdapter.addProduct(product);

        StepVerifier.create(result)
                .expectNextMatches(savedProduct -> savedProduct.getId().equals(1L) && savedProduct.getName().equals("Test Product"))
                .verifyComplete();

        verify(productRespository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void saveProductBranch_shouldSaveTest() {
        Product product = new Product();
        product.setBranchId(2L);
        product.setStock(10);

        ProductBranchEntity productBranchEntity = ProductBranchEntity.builder()
                .productId(1L)
                .branchId(2L)
                .stock(10)
                .build();

        when(productBranchRepository.save(any(ProductBranchEntity.class))).thenReturn(Mono.just(productBranchEntity));

        Mono<Void> result = productAdapter.saveProductBranch(1L, product);

        StepVerifier.create(result).verifyComplete();

        verify(productBranchRepository, times(1)).save(any(ProductBranchEntity.class));
    }

    @Test
    public void existsProductByName_shouldReturnTrueTest() {
        when(productRespository.existsProductByName("Test Product")).thenReturn(Mono.just(true));

        Mono<Boolean> result = productAdapter.existsProductByName("Test Product");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(productRespository, times(1)).existsProductByName("Test Product");
    }

    @Test
    public void deleteProductById_shouldDeleteTest() {
        when(productRespository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = productAdapter.deleteProductById(1L);

        StepVerifier.create(result).verifyComplete();

        verify(productRespository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteProductBranchByIds_shouldDeleteTest() {
        when(productBranchRepository.deleteByProductIdAndBranchId(1L, 2L)).thenReturn(Mono.empty());

        Mono<Void> result = productAdapter.deleteProductBranchByIds(1L, 2L);

        StepVerifier.create(result).verifyComplete();

        verify(productBranchRepository, times(1)).deleteByProductIdAndBranchId(1L, 2L);
    }

    @Test
    public void getProductStockByFranchiseId_shouldReturnProductStockTest() {
        Long franchiseId = 1L;
        ProductStockDTO dto1 = new ProductStockDTO("Product 1", "Branch 1", 100);
        ProductStockDTO dto2 = new ProductStockDTO("Product 2", "Branch 2", 50);

        when(productRespository.findTopStockedProductsByFranchiseId(franchiseId))
                .thenReturn(Flux.just(dto1, dto2));

        Mono<ProductStockByFranchise> result = productAdapter.getProductStockByFranchiseId(franchiseId);

        StepVerifier.create(result)
                .expectNextMatches(productStock ->
                        productStock.getFranchiseId().equals(franchiseId) &&
                                productStock.getProducts().size() == 2 &&
                                productStock.getProducts().get(0).getName().equals("Product 1") &&
                                productStock.getProducts().get(1).getName().equals("Product 2"))
                .verifyComplete();

        verify(productRespository, times(1)).findTopStockedProductsByFranchiseId(franchiseId);
    }

    @Test
    public void findProductBranchByIds_shouldReturnProductTest() {
        ProductBranchEntity productBranchEntity = new ProductBranchEntity(1L, 10, 2L, 3L);
        when(productBranchRepository.findByProductIdAndBranchId(2L, 3L)).thenReturn(Mono.just(productBranchEntity));

        Mono<ProductBranch> result = productAdapter.findProductBranchByIds(2L, 3L);

        StepVerifier.create(result)
                .expectNextMatches(product -> product.getId().equals(1L) && product.getProductId().equals(2L) && product.getBranchId().equals(3L) && product.getStock() == 10)
                .verifyComplete();

        verify(productBranchRepository, times(1)).findByProductIdAndBranchId(2L, 3L);
    }

    @Test
    public void updateProduct_shouldUpdateTest() {
        ProductBranch productBranch = new ProductBranch();
        productBranch.setId(1L);
        productBranch.setBranchId(3L);
        productBranch.setProductId(2L);
        ProductBranchEntity entity = ProductBranchEntity.builder()
                .id(1L)
                .stock(20)
                .productId(1L)
                .branchId(2L)
                .build();

        when(productBranchRepository.save(any(ProductBranchEntity.class))).thenReturn(Mono.just(entity));

        Mono<ProductBranch> result = productAdapter.updateProduct(productBranch);

        StepVerifier.create(result)
                .expectNextMatches(updatedProduct -> updatedProduct.getId().equals(1L) && updatedProduct.getStock() == 20)
                .verifyComplete();

        verify(productBranchRepository, times(1)).save(any(ProductBranchEntity.class));
    }
}
