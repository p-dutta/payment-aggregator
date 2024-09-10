package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.RefundTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundTransactionRepository extends JpaRepository<RefundTransactionEntity, Long>{
}
