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
@Entity(name = "account")
public class Account {
    private static final String SEQ_ACCOUNT = "seq_account_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_ACCOUNT)
    @SequenceGenerator(name= SEQ_ACCOUNT, sequenceName = SEQ_ACCOUNT, allocationSize = 1)
    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    List<Balance> balances;
}
