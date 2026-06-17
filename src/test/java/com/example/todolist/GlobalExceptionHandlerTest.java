package com.example.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMalformedJson() throws Exception {
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Task\", \"completed\": invalid}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTypeMismatch() throws Exception {
        mockMvc.perform(get("/api/tasks/invalid-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNoResourceFound() throws Exception {
        mockMvc.perform(get("/api/unknown-endpoint"))
                .andExpect(status().isNotFound());
    }
}
