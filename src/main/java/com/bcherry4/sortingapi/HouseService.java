package com.bcherry4.sortingapi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
  @Autowired
  private HouseRepository houseRepository;

  public Iterable<House> list(){
    return houseRepository.findAll();
  }

  public Optional<House> findById(Long id){
    return houseRepository.findById(id);
  }

  public House create(House house) {
    return houseRepository.save(house);
  }

  public Optional<House> update(House house) {
    Optional<House> foundHouse = houseRepository.findById(house.getId());

    if (foundHouse.isPresent()) {
        House updatedHouse = foundHouse.get();
        updatedHouse.setName(house.getName());
        updatedHouse.setDetails(house.getDetails());
        updatedHouse.setImageUrl(house.getImageUrl());

        houseRepository.save(updatedHouse);
        return Optional.of(updatedHouse);
      } else {
        return Optional.empty();
      }
  }

  public void deleteById(Long id) {
    houseRepository.deleteById(id);
  }
}