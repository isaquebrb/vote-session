package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService service;

    private static final String CONTENT_TYPE = "application/json";

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/session/start/{topicId}", 1L)
                .contentType(CONTENT_TYPE))
                .andExpect(status().isCreated());
    }
}
