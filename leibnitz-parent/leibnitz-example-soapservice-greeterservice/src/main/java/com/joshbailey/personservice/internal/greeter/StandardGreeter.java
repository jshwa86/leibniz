package com.joshbailey.personservice.internal.greeter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class StandardGreeter implements IGreeter{

	@Override
	public String greetPerson(String name) {
		return "Hello " + name + "!";
	}

}
