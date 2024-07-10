package com.example.fetch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fetch.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{

}
