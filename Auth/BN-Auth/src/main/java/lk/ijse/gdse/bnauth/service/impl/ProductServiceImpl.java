package lk.ijse.gdse.bnauth.service.impl;

import lk.ijse.gdse.bnauth.entity.Product;
import lk.ijse.gdse.bnauth.repository.ProductRepository;
import lk.ijse.gdse.bnauth.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product updatedProduct = productRepository.getProductById(product.getId());
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setQuantity(product.getQuantity());
        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.getProductById(id);
        productRepository.delete(product);
    }
}
