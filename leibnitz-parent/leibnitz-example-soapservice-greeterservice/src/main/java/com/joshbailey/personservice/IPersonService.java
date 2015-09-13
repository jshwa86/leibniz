package com.joshbailey.personservice;

import javax.jws.WebService;

@WebService
public interface IPersonService {

    public String greetPerson(String name);

}
