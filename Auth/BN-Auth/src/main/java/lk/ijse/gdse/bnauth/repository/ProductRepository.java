package lk.ijse.gdse.bnauth.repository;

import lk.ijse.gdse.bnauth.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);
}
