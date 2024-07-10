package com.example.fetch;

import com.example.fetch.model.Reciept;
import com.example.fetch.repository.ItemRepository;
import com.example.fetch.repository.RecieptRepo;
import com.example.fetch.model.Item;

import com.example.fetch.service.serviceImpl.FetchServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FetchApplicationTests {

    @Mock
    private RecieptRepo recieptRepo;

    @Mock
    private ItemRepository itemRepo;

    @InjectMocks
    private FetchServiceImpl fetchService;

    @Test
    public void testSaveReciept_ValidReciept() {
        Reciept myReciept = createValidReciept();
        when(recieptRepo.findByUidString(myReciept.getUidString())).thenReturn(null);
        when(recieptRepo.save(any(Reciept.class))).thenReturn(myReciept);
        when(itemRepo.saveAll(any(Iterable.class))).thenReturn(myReciept.getItems());

        String result = fetchService.saveRecipet(myReciept);

        assertNotNull(result);
        assertEquals(myReciept.getUidString(), result);
    }

    @Test()
    public void testSaveReciept_InvalidRecieptWithoutItems() {
        Reciept invalidReciept = new Reciept();
        invalidReciept.setUidString("789012");
        when(recieptRepo.findByUidString(invalidReciept.getUidString())).thenReturn(invalidReciept);
        assertEquals("Invalid Recipet without Items ", fetchService.saveRecipet(invalidReciept));
    }

    @Test
    public void testGetPoints_ExistingReciept() {
        Reciept myReciept = createValidReciept();
        myReciept.calculatePoints();

        when(recieptRepo.findByUidString(myReciept.getUidString())).thenReturn(myReciept);
        Double points = fetchService.getPoints(myReciept.getUidString());

        assertNotNull(points);
        assertEquals(myReciept.getTotalPoints(), points, 0.001);
    }

    @Test
    public void testGetPoints_NonExistingReciept() {
        when(recieptRepo.findByUidString("non_existing_id")).thenReturn(null);

        Double points = fetchService.getPoints("non_existing_id");

        assertEquals(-1.0, points, 0.001);
    }

    private Reciept createValidReciept() {
        Reciept myReciept = new Reciept();
        Item item = new Item();
        item.setItemId(1L);
        item.setPrice(2.0);
        item.setShortDescription("FetchStocks");
        item.setReciept(myReciept);
        List<Item> items = new ArrayList<>();
        items.add(item);
        myReciept.setItems(items);
        myReciept.setUidString("123456");
        myReciept.setRetailer("7 11");
        myReciept.setTotal(25.0);
        myReciept.setPurchaseDate(new LocalDate(2024,07,15));
        myReciept.setPurchaseTime(new LocalTime(15, 0)); // 2:00 PM
        return myReciept;
    }
}