package com.magastoreapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magastoreapi.models.FaturamentoModel;

@Repository
public interface FaturamentoRepository extends JpaRepository<FaturamentoModel, UUID> {
}
