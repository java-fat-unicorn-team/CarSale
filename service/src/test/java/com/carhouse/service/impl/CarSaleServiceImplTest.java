package com.carhouse.service.impl;

import com.carhouse.dao.CarSaleDao;
import com.carhouse.model.CarSale;
import com.carhouse.model.dto.CarSaleDto;
import com.carhouse.service.exception.WrongReferenceException;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarSaleServiceImplTest {

    @Mock
    private CarSaleDao carSaleDao;

    @InjectMocks
    private CarSaleServiceImpl carSaleService;

    private static List<CarSale> listCarSale;
    private static List<CarSaleDto> listCarSaleDto;

    @BeforeAll
    static void addCarSales() {
        listCarSale = new ArrayList<>() {{
            add(new CarSale(1));
            add(new CarSale(2));
            add(new CarSale(3));
        }};
        listCarSaleDto = new ArrayList<>() {{
            add(new CarSaleDto().setCarSaleId(0));
            add(new CarSaleDto().setCarSaleId(1));
        }};
    }

    @Test
    void getCarSales() {
        when(carSaleDao.getCarSales()).thenReturn(listCarSale);
        assertEquals(listCarSale.size(), carSaleService.getCarSales().size());
        verify(carSaleDao, times(1)).getCarSales();
    }

    @Test
    void getCarSalesDto() {
        Map<String, String> conditionParams = new HashMap<>();
        conditionParams.put("carMakeId", "1");
        conditionParams.put("yearFrom", "2017-01-01");
        conditionParams.put("yearTo", "abcd");
        Map<String, String> validConditionParams = new HashMap<>();
        validConditionParams.put("carMakeId", "1");
        validConditionParams.put("yearFrom", "2017-01-01");
        when(carSaleDao.getCarSalesDto(validConditionParams)).thenReturn(listCarSaleDto);
        assertEquals(listCarSaleDto.size(), carSaleService.getCarSalesDto(conditionParams).size());
        verify(carSaleDao, times(1)).getCarSalesDto(validConditionParams);
    }

    @Test
    void getCarSale() throws NotFoundException {
        int carSaleId = 2;
        when(carSaleDao.getCarSale(carSaleId)).thenReturn(listCarSale.get(carSaleId));
        assertEquals(listCarSale.get(carSaleId).getCarSaleId(), carSaleService.getCarSale(carSaleId).getCarSaleId());
    }

    @Test
    void getNonExistentCarSale() {
        int carSaleId = 10;
        when(carSaleDao.getCarSale(carSaleId)).thenThrow(EmptyResultDataAccessException.class);
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> carSaleService.getCarSale(carSaleId));
        assertTrue(thrown.getMessage().contains("there is not car sale with id = " + carSaleId));

    }

    @Test
    void addCarSale() {
        Integer newCarSaleId = 5;
        CarSale carSale = new CarSale(7);
        when(carSaleDao.addCarSale(carSale)).thenReturn(newCarSaleId);
        assertEquals(newCarSaleId, carSaleService.addCarSale(carSale));
        verify(carSaleDao, times(1)).addCarSale(carSale);
    }

    @Test
    void addCarSaleWithWrongReference() {
        CarSale carSale = new CarSale(7);
        when(carSaleDao.addCarSale(carSale)).thenThrow(DataIntegrityViolationException.class);
        WrongReferenceException thrown = assertThrows(WrongReferenceException.class,
                () -> carSaleService.addCarSale(carSale));
        assertTrue(thrown.getMessage().contains("there is wrong references in your car sale"));
    }

    @Test
    void updateCarSale() throws NotFoundException {
        CarSale carSale = new CarSale(5);
        when(carSaleDao.updateCarSale(carSale)).thenReturn(true);
        carSaleService.updateCarSale(carSale);
        verify(carSaleDao, times(1)).updateCarSale(carSale);
    }

    @Test
    void updateCarSaleWithWrongReference() {
        CarSale carSale = new CarSale(4);
        doThrow(DataIntegrityViolationException.class).when(carSaleDao).updateCarSale(carSale);
        WrongReferenceException thrown = assertThrows(WrongReferenceException.class,
                () -> carSaleService.updateCarSale(carSale));
        assertTrue(thrown.getMessage().contains("there is wrong references in your car sale"));
    }

    @Test
    void updateNotExistCarSale() {
        CarSale carSale = new CarSale(17);
        when(carSaleDao.updateCarSale(carSale)).thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> carSaleService.updateCarSale(carSale));
        assertTrue(thrown.getMessage().contains("there is not car sale with id = " + carSale.getCarSaleId()));
    }

    @Test
    void deleteCarSale() throws NotFoundException {
        int carSaleId = 3;
        when(carSaleDao.deleteCarSale(carSaleId)).thenReturn(true);
        carSaleService.deleteCarSale(carSaleId);
        verify(carSaleDao, times(1)).deleteCarSale(carSaleId);
    }

    @Test
    void deleteNotExistCarSale() {
        int carSaleId = 12;
        when(carSaleDao.deleteCarSale(carSaleId)).thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> carSaleService.deleteCarSale(carSaleId));
        assertTrue(thrown.getMessage().contains("there is not car sale with id = " + carSaleId + " to delete"));
    }
}