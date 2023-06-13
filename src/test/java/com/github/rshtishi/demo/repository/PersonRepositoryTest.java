package com.github.rshtishi.demo.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.rshtishi.demo.entity.Person;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest {

	@Autowired
	private PersonRepository personRepository;

	@Test
	@Order(1)
	void testFindAll() {
		// setup
		// execute
		List<Person> persons = personRepository.findAll();
		// verify
		int expectedSize = 1;
		assertEquals(expectedSize, persons.size());
	}

	@Test
	@Order(2)
	void testFindById() {
		// setup
		long id = 1L;
		// execute
		Person person = personRepository.findById(id);
		// verify
		String expectedName = "Rando";
		assertEquals(id, person.getId());
		assertEquals(expectedName, person.getFirstname());
	}

//	@Test
//	@Order(3)
//	void testSave() {
//		// setup
//		Person person = new Person("Linus", "Torvalds", LocalDate.of(1969, 12, 28));
//		// execute
//		personRepository.save(person);
//		// verify
//		List<Person> persons = personRepository.findAll();
//		int expectedSize = 2;
//		assertEquals(expectedSize, persons.size());
//	}

	@Test
	@Order(3)
	void testDeleteById() {
		// setup
		long id = 2L;
		// execute
		personRepository.deleteById(id);
		// verify
		List<Person> persons = personRepository.findAll();
		int expectedSize = 1;
		assertEquals(expectedSize, persons.size());
	}

	@Test
	@Order(4)
	void testFindByFirstName() {
		// setup
		String firstName = "Rando";
		// execute
		List<Person> persons = personRepository.findByFirstname(firstName);
		// verify
		assertEquals(firstName, persons.get(0).getFirstname());
	}

	@Test
	@Order(5)
	void testFindByName() {
		// setup
		String name = "Shtishi";
		// execute
		List<Person> persons = personRepository.findByName(name);
		// verify
		assertEquals(name, persons.get(0).getLastname());
	}

	@Test
	@Order(6)
	void testUpdatePersonName() {
		// setup
		long id = 1L;
		String firstname = "Steve";
		String lastname = "Jobs";
		// execute
		personRepository.updatePersonName(1L, firstname, lastname);
		// verify
		Person person = personRepository.findById(id);
		assertEquals(firstname, person.getFirstname());
		assertEquals(lastname, person.getLastname());
	}







	}







