package com.alves.arthur.desafio.contasbancariasapi.controllers.Person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.PersonAlreadyExistsException;
import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.Person;
import com.alves.arthur.desafio.contasbancariasapi.services.Person.PersonService;

@RestController
public class PersonController {
	
	PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	
	@RequestMapping(value = "/pessoas", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> listAccounts() {

		List<Person> personsList = personService.list();

		return new ResponseEntity<List<Person>>(personsList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pessoas", method = RequestMethod.POST)
	public ResponseEntity<Person> createAccount(@RequestBody Person person) {

		try {
			Person newPerson = personService.create(person);

			return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);

		} catch (PersonAlreadyExistsException e) {

			e.printStackTrace();
			return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	
}
