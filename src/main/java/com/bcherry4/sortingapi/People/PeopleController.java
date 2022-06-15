package com.bcherry4.sortingapi.People;

import java.util.Map;
import java.util.HashMap;

import com.bcherry4.sortingapi.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("api/people")
public class PeopleController {
  @Autowired
  private PeopleService peopleService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Iterable<People>> list() {
    Iterable<People> peopleList = peopleService.list();
    return createHashPlural(peopleList);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, People> read(@PathVariable Long id) {
    People people = peopleService
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No people with that ID"));
    return createHashSingular(people);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, People> create(@Validated @RequestBody People people) {
    People createdPeople = peopleService.create(people);
    return createHashSingular(createdPeople);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, People> update(@RequestBody People people, @PathVariable Long id) {
    People updatedPeople = peopleService
        .update(people)
        .orElseThrow(() -> new ResourceNotFoundException("No people with that ID"));

    return createHashSingular(updatedPeople);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    peopleService.deleteById(id);
  }

  private Map<String, People> createHashSingular(People people) {
    Map<String, People> response = new HashMap<String, People>();
    response.put("people", people);

    return response;
  }

  private Map<String, Iterable<People>> createHashPlural(Iterable<People> peopleList) {
    Map<String, Iterable<People>> response = new HashMap<String, Iterable<People>>();
    response.put("peopleList", peopleList);

    return response;
  }
}