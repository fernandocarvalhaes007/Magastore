package com.magastoreapi.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.magastoreapi.models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}