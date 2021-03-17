package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.domain.dto.ParameterRequest;
import br.com.isaquebrb.votesession.domain.enums.parameters.IntegerParameter;
import br.com.isaquebrb.votesession.exception.StandardError;
import br.com.isaquebrb.votesession.service.ParameterService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ParameterController.class)
public class ParameterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParameterService service;

    private static final String CONTENT_TYPE = "application/json";

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        ParameterRequest request = new ParameterRequest(IntegerParameter.SESSION_DURATION_MINUTES.name(),
                IntegerParameter.SESSION_DURATION_MINUTES.getDefaultValue());

        mockMvc.perform(patch("/parameter")
                .content(objectMapper.writeValueAsString(request))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenNullName_thenReturns400AndNotBlankConstraint() throws Exception {
        ParameterRequest request = new ParameterRequest(null,
                IntegerParameter.SESSION_DURATION_MINUTES.getDefaultValue());

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.NAME_NOT_BLANK);
    }

    @Test
    public void whenNullValue_thenReturns400AndNotNullConstraint() throws Exception {
        ParameterRequest request = new ParameterRequest(IntegerParameter.SESSION_DURATION_MINUTES.name(), null);

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.VALUE_NOT_BLANK);
    }

    private MvcResult getBadResult(ParameterRequest request) throws Exception {
        return mockMvc.perform(patch("/parameter")
                .content(objectMapper.writeValueAsString(request))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
