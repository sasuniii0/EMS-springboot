package lk.ijse.gdse.bnauth.service;

import lk.ijse.gdse.bnauth.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
}
