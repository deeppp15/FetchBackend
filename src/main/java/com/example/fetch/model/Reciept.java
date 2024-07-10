package com.example.fetch.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.example.fetch.config.LocalDateDeserializer;
import com.example.fetch.config.LocalTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;


@Entity
public class Reciept{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;
    private String uidString;
    private String retailer;

    //Not storing Date and Time into Inmemory since they are only used to calcuate points.
    @Transient
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate purchaseDate;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Transient
    private LocalTime purchaseTime;
    private Double total;
    private Double totalPoints;
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();


    public void addItem(Item item) {
        items.add(item);
        item.setReciept(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setReciept(null);
    }
        
    public Long getReciept_id() {
        return receiptId;
    }
    public void setReciept_id(Long reciept_id) {
        this.receiptId = reciept_id;
    }

    public Double getTotalPoints() {
        if(this.totalPoints==null){
            this.totalPoints= -1.0;
        }
        return totalPoints;
    }
    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }
    public String getUidString() {
        return uidString;
    }
    public void setUidString(String uidString) {
        this.uidString = uidString;
    }
    public String getRetailer() {
        return retailer;
    }
    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }
    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reciept{" +
                "reciept_id=" + receiptId +
                ", uidString='" + uidString + '\'' +
                ", retailer='" + retailer + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                ", total='" + total + '\'' +
                '}';
    }


    public void calculatePoints() throws IllegalArgumentException{
        try{
        this.totalPoints =  calculateItemDescriptionPoints() +
                            calculateItemsPoints()+
                            calculateMultipleOf25Points() +
                            calculateRoundDollarPoints() +
                            calculateAlphanumericPoints() +
                            oddDay() + 
                            purchaseTimeFrame();
        }catch(Exception e){
            throw new IllegalArgumentException("Reciept Incomplete");
        }
    }

    public Double calculateRoundDollarPoints() {
        if (this.total % 1 == 0) {
            return 50.0;
        }
        return 0.0;
    }

    public Double calculateMultipleOf25Points() {
        if (this.total % 0.25 == 0) {
            return 25.0;
        }
        return 0.0;
    }

    public Double calculateItemsPoints() {
        return Double.valueOf((double) (items.size() / 2 * 5));
    }

    public Double calculateItemDescriptionPoints() {
        Double points= 0.0;
        for(Item item: this.getItems()){
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                Double price = item.getPrice();
                points += Math.ceil(price * 0.2);
            }
        }
        
        return points;
    }
                
    public Double calculateAlphanumericPoints() {
        Double points = 0.0;
        for (char c : this.retailer.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }
        return points;
    }
    
    public Double oddDay(){
        return (this.purchaseDate.getDayOfMonth()%2!=0)? 6.0: 0.0;
    }
    
    public Double purchaseTimeFrame(){
        LocalTime startTime = new LocalTime(14, 0); // 2:00 PM
        LocalTime endTime = new LocalTime(16, 0);   // 4:00 PM
        return (purchaseTime.isAfter(startTime) && purchaseTime.isBefore(endTime))? 10.0:0.0;
    }
}


