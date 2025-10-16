package com.produtos.produtos.repository;

import com.produtos.produtos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {
    Optional<ProdutoModel> findByNameContainingIgnoreCase(String name);
}
