package com.shariful.mb.accountservice.entities.dbentities;

 import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "balance")
public class Balance {
    private static final String SEQ_BALANCE = "seq_balance_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_BALANCE)
    @SequenceGenerator(name= SEQ_BALANCE, sequenceName = SEQ_BALANCE, allocationSize = 1)
    @Column(name = "balance_id")
    private Long balanceId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
}
