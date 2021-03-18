package com.alves.arthur.desafio.contasbancariasapi.services.Person;

import java.util.List;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.PersonAlreadyExistsException;
import com.alves.arthur.desafio.contasbancariasapi.models.Person;

public interface PersonService {
	public Person create(Person person) throws PersonAlreadyExistsException;
	public List<Person> list();
}
