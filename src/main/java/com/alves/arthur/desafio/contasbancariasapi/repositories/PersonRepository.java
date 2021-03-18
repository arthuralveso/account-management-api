package com.alves.arthur.desafio.contasbancariasapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alves.arthur.desafio.contasbancariasapi.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	public Person findByCpf(String cpf);
}
