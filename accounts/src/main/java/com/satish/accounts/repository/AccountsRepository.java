package com.satish.accounts.repository;

import com.satish.accounts.entity.Accounts;
import com.satish.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}
