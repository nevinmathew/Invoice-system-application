package com.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByTransactionId(String id);

	void deleteAllByTransactionId(String id);

	void deleteByTransactionId(String id);

}
