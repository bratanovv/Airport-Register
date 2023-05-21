package com.example.airport;



import com.example.airport.models.Ticket;
import com.example.airport.models.User;
import com.example.airport.repositories.UserRepository;
import com.example.airport.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/clear-db.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-db.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // Проверяем, что сервис не равен null
        assertNotNull(userService);
        User u = new User();
        u.setPassword("1");

        // Вызываем метод сервиса и проверяем результат
        boolean result = userService.createUser(u);
        List<User> list = userRepository.findAll();
        assertEquals(1,list.size());
        assertTrue(result);
    }

    @Test
    public void testTiketList() {
        // Проверяем, что сервис не равен null
        assertNotNull(userService);
        User u = new User();
        u.setPassword("1");

        // Вызываем метод сервиса и проверяем результат
        boolean result = userService.createUser(u);
        List<User> list = userRepository.findAll();
        assertEquals(1,list.size());
        List<Ticket> ticketList = userService.getTicketsAfterNow(list.get(0));
        List<Ticket> ticketList2 = userService.getTicketsBeforeNow(list.get(0));
        assertEquals(0,ticketList2.size());
        assertEquals(0,ticketList.size());
        assertTrue(result);
    }

    @Test
    public void testBanUser() {
        // Проверяем, что сервис не равен null
        assertNotNull(userService);
        User u = new User();
        u.setPassword("1");

        // Вызываем метод сервиса и проверяем результат
        boolean result = userService.createUser(u);
        List<User> list = userService.list();
        assertEquals(1,list.size());
       userService.banUser( list.get(0).getId());
        assertTrue(result);
    }



}
