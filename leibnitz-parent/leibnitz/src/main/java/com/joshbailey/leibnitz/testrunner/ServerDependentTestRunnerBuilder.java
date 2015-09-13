package com.joshbailey.leibnitz.testrunner;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

import com.joshbailey.leibnitz.servers.LeibnitzServer;

public class ServerDependentTestRunnerBuilder extends RunnerBuilder {

	private LeibnitzServer leibnitzServer;
	
	public ServerDependentTestRunnerBuilder(LeibnitzServer leibnitzServer){
		this.leibnitzServer = leibnitzServer;
	}
	
	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new ServerDependentTestRunner(testClass,leibnitzServer);
	}

}
