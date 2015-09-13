package com.joshbailey.leibniz.testrunner;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

import com.joshbailey.leibniz.servers.LeibnitzServer;

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
