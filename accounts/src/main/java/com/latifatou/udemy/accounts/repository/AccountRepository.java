package com.latifatou.udemy.accounts.repository;

import com.latifatou.udemy.accounts.entity.Account;
import com.latifatou.udemy.accounts.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(Long customerId);

    @Transactional //pour exécuter la requête dans une transaction s'il y a une erreuuur l'operation est annulé
    @Modifying// car customerId n'est pas la clé primaire
    void deleteByCustomerId(Long customerId);
}
