package com.kodilla.stockpricemonitorwithalert.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "crypto")
public class CryptoInfoSnapshotEty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cryptoName;
    private BigDecimal price;
    private LocalDateTime timestamp;
}
