package com.banglalink.toffee.repository;

import com.banglalink.toffee.models.schema.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<ImageEntity, Long>, JpaSpecificationExecutor<ImageEntity> {
}
