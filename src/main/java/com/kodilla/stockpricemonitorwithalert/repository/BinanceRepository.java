package com.kodilla.stockpricemonitorwithalert.repository;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinanceRepository extends JpaRepository<CryptoInfoSnapshotEty, Long> {
}
