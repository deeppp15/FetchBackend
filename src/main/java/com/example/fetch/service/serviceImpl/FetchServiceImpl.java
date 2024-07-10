package com.example.fetch.service.serviceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fetch.model.Item;
import com.example.fetch.model.Reciept;
import com.example.fetch.repository.ItemRepository;
import com.example.fetch.repository.RecieptRepo;
import com.example.fetch.service.FetchService;


@Service  
public class FetchServiceImpl implements FetchService {
	protected static final Logger logger = LogManager.getLogger();

    @Autowired
    RecieptRepo recieptRepo;
    
    @Autowired
    ItemRepository itemRepo;
    
    @Transactional
   public String saveRecipet(Reciept myReciept)throws IllegalArgumentException{
        try{
            Reciept existingRecipet=  recieptRepo.findByUidString(myReciept.getUidString());
            if(myReciept.getItems().size()==0)return "Invalid Recipet without Items ";

            if(existingRecipet!=null){
                return "Recipet Already exists";
            }
            myReciept.calculatePoints();
            recieptRepo.save(myReciept);
            logger.debug("BEFORE INSERTING: "+myReciept.toString());

            logger.debug("Insesrted Recipet- "+myReciept.getReciept_id());
            List<Item>daItems= myReciept.getItems();

            for(Item i: daItems){
                i.setReciept(myReciept);
            }
            itemRepo.saveAll(daItems);
            return myReciept.getUidString();
        }catch(Exception e){
            throw e;
           // throw new IllegalArgumentException("Error processing current reciept");
        }
      
    }


    /*
     * Returns Points corresponding to a Reciept if exists
     *          If invalid Id returns -1
     *  Throws IllegalArgumentException
     */
    @Transactional
   public Double getPoints(String id) throws IllegalArgumentException{
        try{
            Reciept existingRecipet= recieptRepo.findByUidString(id);
            if(existingRecipet == null){
                System.out.println("Existing is null");
                return -1.0;
            }
            return existingRecipet.getTotalPoints();
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid argument passed");
        }
              
    }

}
