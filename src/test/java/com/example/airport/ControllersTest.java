package com.example.airport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add-user-admin.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails("admin@admin.admin")
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProductController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testSearchAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search").param("destination", "")
                        .param("departure", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAdminController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testUserController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}