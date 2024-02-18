package com.solix.com.producer_service.repository;

import com.solix.com.producer_service.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
