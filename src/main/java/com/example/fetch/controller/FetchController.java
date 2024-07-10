package com.example.fetch.controller;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fetch.model.Reciept;
import com.example.fetch.response.InvalidInput;
import com.example.fetch.response.ResponsePoints;
import com.example.fetch.response.ResponseUID;
import com.example.fetch.service.FetchService;

@RestController
public class FetchController {
    
    @Autowired
    FetchService fetchService;

	protected static final Logger logger = LogManager.getLogger();

    /**
     * Precondition- A valid Reciept JSON where items cannot be empty
     * PostCondition- Returns the id of a reciept if the reciept is valid.
     *                   Else returns Invalid Recipt.
     * @param fetchModel
     * @return uidString 
     *          InvalidInput if reciept format is different
     *          400 Status code if Request fails during serialization 
     */
    @PostMapping(value = "/receipts/process" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processReciept(@RequestBody Reciept fetchModel){
        try{

            fetchModel.setUidString(UUID.randomUUID().toString());
            logger.debug(fetchModel.toString());
            String uid= fetchService.saveRecipet(fetchModel);
            if(uid == null)return new ResponseEntity<>(new InvalidInput("Invalid Recipet Format"), HttpStatus.BAD_REQUEST);
            ResponseUID response= new ResponseUID(uid);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    
    /**
     * Precondition- A valid UID which was passed with a valid structure of JSON
     * PostCondition- Returns the points of a reciept if the reciept is valid.
     *                   Else returns Invalid Recipt.
     *  
     * @param id uidString
     * @return points of a reciept
     *          400 if the JSON Input cannot be serialized
     */
    @GetMapping(value = "/receipts/{id}/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        try{
            Double points =fetchService.getPoints(id);
            logger.debug("Recieved: "+id);
            if(points ==-1)return new ResponseEntity<>(new InvalidInput("Invalid Recipet Id"), HttpStatus.BAD_REQUEST);
            ResponsePoints response = new ResponsePoints(points);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
