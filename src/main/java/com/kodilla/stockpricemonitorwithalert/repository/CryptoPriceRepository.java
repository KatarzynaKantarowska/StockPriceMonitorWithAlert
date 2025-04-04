package com.kodilla.stockpricemonitorwithalert.repository;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CryptoPriceRepository extends JpaRepository<CryptoInfoSnapshotEty, Long> {
    CryptoInfoSnapshotEty findCryptoById(Long cryptoId);
    @Query(value = "SELECT c FROM CryptoInfoSnapshotEty c ORDER BY c.id DESC LIMIT 1")
    CryptoInfoSnapshotEty findTopByOrderByIdDesc();
    List<CryptoInfoSnapshotEty> findAllByCryptoName(String symbol);


}
