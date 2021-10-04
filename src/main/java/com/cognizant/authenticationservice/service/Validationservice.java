package com.cognizant.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.authenticationservice.model.AuthenticationResponse;
import com.cognizant.authenticationservice.repository.UserRepository;

@Component
public class Validationservice {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private UserRepository userRepo;

	public AuthenticationResponse validate(String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		String jwt = token;
		if (token.startsWith("Bearer ")) {
			jwt = token.substring(7);
		}
		if (jwtutil.validateToken(jwt)) {    // token returns 3 things
			authenticationResponse.setUserid(jwtutil.extractUsername(jwt));
			authenticationResponse.setValid(true);
			authenticationResponse.setName(userRepo.findById(jwtutil.extractUsername(jwt)).get().getUsername());
		} else {
			authenticationResponse.setValid(false);
		}
		return authenticationResponse;
	}
}