package com.ecoomerce.ecommerce.repository;

import com.ecoomerce.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

    @Query("""
    SELECT p FROM Product p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY CASE 
                WHEN LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 0 
                ELSE 1 
             END
""")
    List<Product> searchProducts(String keyword);


}
