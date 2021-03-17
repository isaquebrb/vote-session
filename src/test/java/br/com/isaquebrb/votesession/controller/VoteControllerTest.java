package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.domain.dto.VotingRequest;
import br.com.isaquebrb.votesession.exception.StandardError;
import br.com.isaquebrb.votesession.service.AssociateVoteService;
import br.com.isaquebrb.votesession.utils.StringUtils;
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
@WebMvcTest(controllers = VoteController.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssociateVoteService service;

    private static final String CONTENT_TYPE = "application/json";
    private static final String CPF = "52725885094";
    private static final String VOTE_CHOICE = "NÃO";

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        VotingRequest request = new VotingRequest(CPF, VOTE_CHOICE, 1L);

        mockMvc.perform(post("/vote")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void whenNullDocument_thenReturns400AndNotBlankConstraint() throws Exception {
        VotingRequest request = new VotingRequest(null, VOTE_CHOICE, 1L);

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        assertThat(error.getErrors()).contains(MessageConstraints.DOCUMENT_NOT_BLANK);
    }

    @Test
    public void whenInvalidVoteChoice_thenReturns400AndVoteConstraint() throws Exception {
        VotingRequest request = new VotingRequest(CPF, "TALVEZ", 1L);

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
        String voteChoice = StringUtils.normalize(error.getErrors().get(0)); //removing special characters
        String constraintMessage = StringUtils.normalize(MessageConstraints.VOTE_CHOICE);

        assertThat(voteChoice).isEqualToIgnoringWhitespace(constraintMessage);
    }

    @Test
    public void whenNullSession_thenReturns400AndNotNullConstraint() throws Exception {
        VotingRequest request = new VotingRequest(CPF, VOTE_CHOICE, null);

        MvcResult result = getBadResult(request);

        StandardError error = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);

        assertThat(error.getErrors()).contains(MessageConstraints.SESSION_ID_NOT_BLANK);
    }

    private MvcResult getBadResult(VotingRequest request) throws Exception {
        return mockMvc.perform(post("/vote")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();
    }
}
