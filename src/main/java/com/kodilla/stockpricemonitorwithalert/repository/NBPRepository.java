package com.kodilla.stockpricemonitorwithalert.repository;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NBPRepository extends JpaRepository<CryptoInfoSnapshotEty, Long> {
}
