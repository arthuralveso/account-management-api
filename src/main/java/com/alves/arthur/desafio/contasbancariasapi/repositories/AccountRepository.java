  
package com.alves.arthur.desafio.contasbancariasapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	public Account findByPersonId(Long id);
}