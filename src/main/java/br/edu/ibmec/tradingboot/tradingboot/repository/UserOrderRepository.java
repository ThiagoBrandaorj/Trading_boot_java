package br.edu.ibmec.tradingboot.tradingboot.repository;
import br.edu.ibmec.tradingboot.tradingboot.model.UserOrderReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrderReport, Integer> {
}
