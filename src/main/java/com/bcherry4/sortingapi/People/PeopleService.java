package com.bcherry4.sortingapi.People;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
  @Autowired
  private PeopleRepository peopleRepository;

  public Iterable<People> list(){
    return peopleRepository.findAll();
  }

  public Optional<People> findById(Long id){
    return peopleRepository.findById(id);
  }

  public People create(People people) {
    return peopleRepository.save(people);
  }

  public Optional<People> update(People people) {
    Optional<People> foundPeople = peopleRepository.findById(people.getId());

    if (foundPeople.isPresent()) {
        People updatedPeople = foundPeople.get();
        updatedPeople.setName(people.getName());
        updatedPeople.setTitle(people.getTitle());
        updatedPeople.setHouse(people.getHouse());

        peopleRepository.save(updatedPeople);
        return Optional.of(updatedPeople);
      } else {
        return Optional.empty();
      }
  }

  public void deleteById(Long id) {
    peopleRepository.deleteById(id);
  }
}