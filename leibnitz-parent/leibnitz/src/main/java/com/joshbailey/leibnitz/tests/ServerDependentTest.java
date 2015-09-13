package com.joshbailey.leibnitz.tests;

import org.junit.runner.RunWith;

import com.joshbailey.leibnitz.annotations.LiebnizTest;
import com.joshbailey.leibnitz.testrunner.ServerDependentSuite;

@RunWith(ServerDependentSuite.class)
@LiebnizTest
public abstract class ServerDependentTest {

//	public abstract Class<? extends leibnitzServer> obtainRequiredServer();
	
}
