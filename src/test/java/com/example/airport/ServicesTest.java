package com.example.airport;

import com.example.airport.models.Flight;
import com.example.airport.services.FlyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/clear-db.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-db.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ServicesTest {
    @Autowired
    private FlyService productService;

    @Test
    public void testEmpty() {
        // Проверяем, что сервис не равен null
        assertNotNull(productService);
        // Вызываем метод сервиса и проверяем результат
        List<Flight> result = productService.getFlightList("","",false);
        assertEquals(0, result.size());
    }


    @Test
    public void testAddProduct() {
        // Проверяем, что сервис не равен null
        assertNotNull(productService);

        Flight p = new Flight();
        p.setAirplane("1");
        p.setDescription("1");
        p.setPrice(1);
        p.setCityDest("2");
        p.setCityFrom("2");
        p.setAmount(3);

        productService.saveFlight(p);
        List<Flight> result = productService.getFlightList("","",false);
        assertEquals(1, result.size());
    }

    @Test
    public void testRemoveProduct() {
        // Проверяем, что сервис не равен null
        assertNotNull(productService);


        Flight p = new Flight();
        p.setAirplane("1");
        p.setDescription("1");
        p.setPrice(1);
        p.setCityDest("2");
        p.setCityFrom("2");


        productService.saveFlight(p);
        List<Flight> result = productService.getFlightList("","",false);
        productService.deleteFlight(result.get(0).getId());
        result = productService.getFlightList("","",false);

        assertEquals(0, result.size());
    }

    @Test
    public void testStatistic() {
        // Проверяем, что сервис не равен null
        assertNotNull(productService);
        List<Integer> result =   productService.statistics();
        int sum=0;
        for (int i = 0; i < result.size() ; i++) {
            sum+=result.get(i);
        }

        assertEquals(10, result.size());
        assertEquals(0, sum);

    }



}
