package com.kapware;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.kapware.Vote.VoteResponse.YES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class YnclickerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void shouldReturnVotesByEventId() throws Exception {
		// given:
		mockMvc.perform(post("/votes")
				.content(mapper.writeValueAsString(new Vote("tok_example", YES)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		// when:
		mockMvc.perform(get("/votes").param("eventId", "1"))
				.andDo(print())

		// then:
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("tok_example"))
				.andExpect(jsonPath("$.eventId").value("1"))
				.andExpect(jsonPath("$.response").value("YES"))
		;
	}

	@Test
	public void shouldRespondWith404IfEventIsMissing() throws Exception {
		// given:

		// when:
		mockMvc.perform(get("/votes").param("eventId", "3"))
				.andDo(print())

		// then:
				.andExpect(status().isNotFound())
		;
	}
}
