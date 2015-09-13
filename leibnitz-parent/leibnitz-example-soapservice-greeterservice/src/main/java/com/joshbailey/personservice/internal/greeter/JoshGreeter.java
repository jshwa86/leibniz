package com.joshbailey.personservice.internal.greeter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("josh")
public class JoshGreeter implements IGreeter {

	@Override
	public String greetPerson(String name) {
		return "Hello Josh!!";
	}

}
