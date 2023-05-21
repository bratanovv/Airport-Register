package com.example.airport;

import com.example.airport.models.Flight;
import com.example.airport.services.FlyService;
import com.example.airport.services.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class MailSenderTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Test
    public void testMail() {
        // Проверяем, что сервис не равен null
        assertNotNull(mailSenderService);
        // Вызываем метод сервиса и проверяем результат
        mailSenderService.send("bratanov.misha@mail.ru","TEST","TEST_M");
    }
}
