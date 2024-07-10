package com.example.fetch.service;


import com.example.fetch.model.Reciept;

public interface FetchService {

    public String saveRecipet(Reciept myReciept); 
    
   public Double getPoints(String id);
      
}
