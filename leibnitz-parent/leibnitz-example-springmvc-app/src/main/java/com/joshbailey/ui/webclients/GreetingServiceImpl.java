package com.joshbailey.ui.webclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joshbailey.personservice.PersonService;
import com.joshbailey.ui.webclients.IGreetingService;

@Component("greetingService")
public class GreetingServiceImpl implements IGreetingService{

	@Autowired
	private PersonService personServiceClient;
	
	@Override
	public String retrieveGreeting(String name) {
		return personServiceClient.greetPerson(name);
	}

}
