package lk.ijse.gdse.bnauth;

import lk.ijse.gdse.bnauth.entity.Product;
import lk.ijse.gdse.bnauth.repository.ProductRepository;
import lk.ijse.gdse.bnauth.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(100.0)
                .quantity(3000)
                .build();
    }

    @Test
    void shouldSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(
                product
        );

        Product savedProduct = productService.createProduct(product);

        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(product.getId(), savedProduct.getId());
        Assertions.assertEquals(product.getName(), savedProduct.getName());
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice());
        Assertions.assertEquals(product.getQuantity(), savedProduct.getQuantity());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldUpdateProduct() {
        when(productRepository.getProductById(1L)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        product.setName("Updated Product");
        Product updatedProduct = productService.updateProduct(product);

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).getProductById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.getProductById(1L)).thenReturn(product);
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct(1L);

        verify(productRepository, times(
                1))
                .getProductById(1L);
        verify(productRepository, times(
                1))
                .delete(any(Product.class));
    }

    @Test
    void shouldGetProductById() {
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));

        Product foundProduct = productService.getProduct(1L);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getId(), foundProduct.getId());
        Assertions.assertEquals(product.getName(), foundProduct.getName());
        Assertions.assertEquals(product.getPrice(), foundProduct.getPrice());
        Assertions.assertEquals(product.getQuantity(), foundProduct.getQuantity());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetAllProducts() {
        when(productRepository.findAll()).thenReturn(java.util.List.of(product));

        java.util.List<Product> products = productService.getAllProducts();

        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(product.getId(), products.get(0).getId());

        verify(productRepository, times(1)).findAll();
    }
}
