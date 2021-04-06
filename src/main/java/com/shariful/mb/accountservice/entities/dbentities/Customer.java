package com.shariful.mb.accountservice.entities.dbentities;

 import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer")
public class Customer {
    private static final String SEQ_CUSTOMER = "seq_customer_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_CUSTOMER)
    @SequenceGenerator(name=SEQ_CUSTOMER, sequenceName = SEQ_CUSTOMER, allocationSize = 1)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;
}
