package com.insurance.product_policy_service.repository;


import com.insurance.product_policy_service.entity.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
}
