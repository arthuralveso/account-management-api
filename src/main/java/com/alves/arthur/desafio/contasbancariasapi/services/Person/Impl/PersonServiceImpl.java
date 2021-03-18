package com.alves.arthur.desafio.contasbancariasapi.services.Person.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.PersonAlreadyExistsException;
import com.alves.arthur.desafio.contasbancariasapi.models.Person;
import com.alves.arthur.desafio.contasbancariasapi.repositories.PersonRepository;
import com.alves.arthur.desafio.contasbancariasapi.services.Person.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
	
	
	@Override
	public Person create(Person person) throws PersonAlreadyExistsException {
		Person existingPerson = personRepository.findByCpf(person.getCpf()); 

		if(existingPerson != null) {
			throw new PersonAlreadyExistsException("Person already registered");
		}
		
		return personRepository.save(person);
	}


	@Override
	public List<Person> list() {
		return personRepository.findAll();
	}

}
