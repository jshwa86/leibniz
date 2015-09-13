package com.joshbailey.personservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.joshbailey.personservice.IPersonService;
import com.joshbailey.personservice.PersonServiceImpl;


public class PersonServiceImplTest {

    private IPersonService service;
    
    @Before
    public void setUp() {
        service = new PersonServiceImpl();
    }
    
//    @Test FIXME
    public void testGreetPerson() {
        String expected = "Hello World!";
        
        String actual = service.greetPerson("World");
        
        assertEquals(expected, actual);
    }
    
}
