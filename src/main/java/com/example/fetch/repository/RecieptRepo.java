package com.example.fetch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fetch.model.Reciept;

@Repository
public interface RecieptRepo extends CrudRepository<Reciept, Long> {

   
    public Reciept findByUidString(String uidString);

}
