package com.joshbailey.leibniz.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.runner.RunWith;

import com.joshbailey.leibniz.testrunner.ServerDependentSuite;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RunWith(ServerDependentSuite.class)
public @interface LiebnizTest {

//	Class<? extends leibnitzServer> serverConfig();
	
}
