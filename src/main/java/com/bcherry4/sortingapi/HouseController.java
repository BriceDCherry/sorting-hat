package com.bcherry4.sortingapi;

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
@RequestMapping("api/house")
public class HouseController {
  @Autowired
  private HouseService houseService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Iterable<House>> list() {
    Iterable<House> houseList = houseService.list();
    return createHashPlural(houseList);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, House> read(@PathVariable Long id) {
    House house = houseService
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No house with that ID"));
    return createHashSingular(house);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, House> create(@Validated @RequestBody House house) {
    House createdHouse = houseService.create(house);
    return createHashSingular(createdHouse);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, House> update(@RequestBody House house, @PathVariable Long id) {
    House updatedHouse = houseService
        .update(house)
        .orElseThrow(() -> new ResourceNotFoundException("No house with that ID"));

    return createHashSingular(updatedHouse);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    houseService.deleteById(id);
  }

  private Map<String, House> createHashSingular(House house) {
    Map<String, House> response = new HashMap<String, House>();
    response.put("house", house);

    return response;
  }

  private Map<String, Iterable<House>> createHashPlural(Iterable<House> houseList) {
    Map<String, Iterable<House>> response = new HashMap<String, Iterable<House>>();
    response.put("houseList", houseList);

    return response;
  }
}