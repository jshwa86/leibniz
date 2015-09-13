package com.joshbailey.personservice;


import static org.junit.Assert.*;

import org.junit.Test;

import com.joshbailey.personservice.Person;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person("foo");
        assertEquals("foo", p.getName());
        
        p.setName("bar");
        assertEquals("bar", p.getName());
    }

}
