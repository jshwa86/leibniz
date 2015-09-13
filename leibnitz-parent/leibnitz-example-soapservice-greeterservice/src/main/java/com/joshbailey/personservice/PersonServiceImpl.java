package com.joshbailey.personservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.joshbailey.personservice.internal.greeter.IGreeter;

@WebService(endpointInterface = "com.joshbailey.personservice.IPersonService")
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private IGreeter greeterService;
	
    public String greetPerson(String name) {
        Person person = new Person(name);
        return greeterService.greetPerson(person.getName());
    }

}
