package com.solix.com.producer_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customer", schema = "producer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "name")
    private String name;
}



