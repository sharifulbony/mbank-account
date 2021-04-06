package com.shariful.mb.accountservice.entities.dbentities;

 import com.shariful.mb.accountservice.utilities.util.TransactionSourceDestination;
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
@Entity(name = "transaction")
public class Transaction {
    private static final String SEQ_TRANSACTION = "seq_transaction_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_TRANSACTION)
    @SequenceGenerator(name= SEQ_TRANSACTION, sequenceName = SEQ_TRANSACTION, allocationSize = 1)
    private Long transactionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "direction_of_transaction")
    private TransactionSourceDestination transactionSourceDestination;

    @Column(name = "description")
    private String description;
}
