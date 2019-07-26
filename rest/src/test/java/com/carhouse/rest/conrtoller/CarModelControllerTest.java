package com.carhouse.rest.conrtoller;

import com.carhouse.model.CarMake;
import com.carhouse.model.CarModel;
import com.carhouse.service.CarModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarModelControllerTest {

    @Mock
    private CarModelService carModelService;

    @InjectMocks
    private CarModelController carModelController;

    private List<CarModel> listCarModel;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carModelController).build();
        listCarModel = new ArrayList<>() {{
            add(new CarModel(1, new CarMake(1, "Mercedes")));
            add(new CarModel(2, new CarMake(2, "BMW")));
        }};
    }

    @Test
    void getCarModels() throws Exception {
        when(carModelService.getCarModels()).thenReturn(listCarModel);
        mockMvc.perform(get("/carSale/car/carModel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
        verify(carModelService, times(1)).getCarModels();
    }
}