package com.alves.arthur.desafio.contasbancariasapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Account findByIdPerson(Long id);
}
