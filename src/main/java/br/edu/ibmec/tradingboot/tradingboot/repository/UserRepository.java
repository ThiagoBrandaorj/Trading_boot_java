package br.edu.ibmec.tradingboot.tradingboot.repository;

import br.edu.ibmec.tradingboot.tradingboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}