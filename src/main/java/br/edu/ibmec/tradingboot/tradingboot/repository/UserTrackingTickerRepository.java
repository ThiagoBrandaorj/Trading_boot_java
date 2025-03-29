package br.edu.ibmec.tradingboot.tradingboot.repository;

import br.edu.ibmec.tradingboot.tradingboot.model.UserTrackingTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackingTickerRepository extends JpaRepository<UserTrackingTicker, Integer> {
}