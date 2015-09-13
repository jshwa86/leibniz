package com.joshbailey.leibniz.tests;

import org.junit.runner.RunWith;

import com.joshbailey.leibniz.annotations.LiebnizTest;
import com.joshbailey.leibniz.testrunner.ServerDependentSuite;

@RunWith(ServerDependentSuite.class)
@LiebnizTest
public abstract class ServerDependentTest {

//	public abstract Class<? extends leibnitzServer> obtainRequiredServer();
	
}
