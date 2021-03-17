package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import br.com.isaquebrb.votesession.exception.StandardError;
import br.com.isaquebrb.votesession.service.AssociateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AssociateController.class)
public class AssociateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssociateService service;

    private static final String CONTENT_TYPE = "application/json";
    private static final String CPF = "52725885094";


    @Test
    public void whenValidInput_thenReturns201() throws Exception {
        AssociateRequest request = new AssociateRequest(CPF, "Name 1");
        mockMvc.perform(post("/associate/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNullDocument_thenReturns400AndNotBlankConstraint() throws Exception {
        AssociateRequest request = new AssociateRequest(null, "Name 1");
        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.DOCUMENT_NOT_BLANK);
    }

    @Test
    public void whenInvalidDocument_thenReturns400AndCPFConstraint() throws Exception {
        AssociateRequest request = new AssociateRequest("12345678910", "Name 1");

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.CPF);
    }

    @Test
    public void whenInvalidDocument_thenReturns400AndSizeConstraint() throws Exception {
        AssociateRequest request = new AssociateRequest("1234567", "Name 1");

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.DOCUMENT_SIZE);
    }


    private MvcResult getBadResult(AssociateRequest request) throws Exception {
        return mockMvc.perform(post("/associate/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
