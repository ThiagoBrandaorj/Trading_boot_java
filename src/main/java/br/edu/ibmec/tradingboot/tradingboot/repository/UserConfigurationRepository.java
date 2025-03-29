package br.edu.ibmec.tradingboot.tradingboot.repository;


import br.edu.ibmec.tradingboot.tradingboot.model.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Integer> {
}