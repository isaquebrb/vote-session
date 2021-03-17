package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.TopicRequest;
import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicResult;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.exception.StandardError;
import br.com.isaquebrb.votesession.service.TopicService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TopicController.class)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TopicService service;

    private static final String CONTENT_TYPE = "application/json";

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        TopicRequest request = new TopicRequest("Pauta XY", "Descricao da pauta xy");

        mockMvc.perform(post("/topic/new")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNullName_thenReturns400AndNotBlankConstraint() throws Exception {
        TopicRequest request = new TopicRequest(null, "Descricao da pauta xy");

        MvcResult result = mockMvc.perform(post("/topic/new")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);

        assertThat(error.getErrors()).contains(MessageConstraints.NAME_NOT_BLANK);
    }

    @Test
    public void whenFindById_thenReturnsDto() throws Exception {
        Topic topic = Topic.builder()
                .id(1L)
                .name("Topic name")
                .description("Topic description")
                .status(TopicStatus.CLOSED)
                .result(TopicResult.APPROVED)
                .session(null).build();

        when(service.findById(anyLong())).thenReturn(topic);

        MvcResult result = mockMvc.perform(get("/topic/{id}", topic.getId())
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk()).andReturn();

        TopicResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TopicResponse.class);

        assertThat(response.getId()).isEqualTo(topic.getId());
        assertThat(response.getStatus()).isEqualTo(topic.getStatus().name());
        assertThat(response.getResult()).isEqualTo(topic.getResult().name());
    }
}
