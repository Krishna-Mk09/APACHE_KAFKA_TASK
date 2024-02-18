package com.solix.com.consumer_service_one.repository;

import com.solix.com.consumer_service_one.domain.Customer;
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
