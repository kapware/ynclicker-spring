package com.kapware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
class VoteController {

	private final VoteRepository voteRepository;

	@Autowired
	VoteController(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}

	@RequestMapping("/votes")
	ResponseEntity<?> votes(@RequestParam(value = "eventId") String eventId) {
		return voteRepository.findByEventId(eventId)
				.map(ResponseEntity::ok)
				.orElse(notFound().build());
	}

	@RequestMapping(value = "/votes" , method = POST)
	ResponseEntity<?> add(@RequestBody Vote vote) {
		Vote result = voteRepository.save(vote);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getEventId()).toUri();

		return created(location).build();
	}
}
