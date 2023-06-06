package com.github.rshtishi.demo.controller;

import com.github.rshtishi.demo.entity.Person;
import com.github.rshtishi.demo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by User: Vu
 * Date: 06.06.2023
 * Time: 10:49
 */
@RestController
public class PersonController {

    private final PersonRepository personRepository;


    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @GetMapping("person")
    public ResponseEntity getAllPeople(){
        return ResponseEntity.ok(this.personRepository.findAll());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> gePersonByID(@PathVariable("id") int id) {
        Person person = personRepository.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person thePerson) {
        Person newPerson = personRepository.save(thePerson);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody Person thePerson) {
        Person existingPerson = personRepository.findById(id).get();

        if (existingPerson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the existing person with the new data
        existingPerson.setFirstname(thePerson.getFirstname());
        existingPerson.setLastname(thePerson.getLastname());
        existingPerson.setBirthdate(thePerson.getBirthdate());

        Person updatedPerson = personRepository.save(existingPerson);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping("person/{id}")
    public String deletePerson(@PathVariable("id")Long id){
        Person tempPerson = personRepository.findById(id).get();

        if(tempPerson==null){
            throw new RuntimeException("employee id not found " + id);
        }
        personRepository.deleteById(id);
        System.out.println("Deleted employee id - " + id);

        return "Deleted employee id ";
    }

//    getting count of people in dtb
    @GetMapping("person/count")
    public ResponseEntity<Long> getAllPersonsCount(){
        Long count = personRepository.countAllPersons();
        return ResponseEntity.ok(count);
    }


    }




