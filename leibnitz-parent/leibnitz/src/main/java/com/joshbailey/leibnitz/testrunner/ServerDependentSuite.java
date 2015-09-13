package com.joshbailey.leibnitz.testrunner;

import java.lang.reflect.InvocationTargetException;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.joshbailey.leibnitz.cargo.CargoOrchestrator;
import com.joshbailey.leibnitz.servers.LeibnitzServer;

public class ServerDependentSuite extends Suite{
	
	private final String suiteName;
	private final LeibnitzServer leibnitzServer;
	private boolean done;
	
	//This constructor is used by the LiebnizTestSuiteRunner (AKA when more than one test class executes in a test run)
	public ServerDependentSuite(LeibnitzServer LeibnitzServer
								,RunnerBuilder builder
								,Class<?>[] classes) throws InitializationError{
		super(null, builder.runners(null, classes));
		this.suiteName = LeibnitzServer.getClass().getSimpleName();
		this.leibnitzServer = LeibnitzServer;
		this.done = false;
		System.out.println("Created new ServerDependentSuite (1)");
	}
	
	//This constructor is used when a single test class is run by itself
	public ServerDependentSuite(Class testClass) throws InitializationError, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super(testClass,new Class[0]);
		System.out.println("** Size of children: "+super.getChildren().size());
		this.leibnitzServer = LeibnizTestSuiteRunner.extractLiebnizServerFrom(testClass);
		this.suiteName = leibnitzServer.getClass().getSimpleName();
		this.done = false;
		this.getChildren().add(new ServerDependentTestRunner(testClass,leibnitzServer));
		System.out.println("Created new ServerDependentSuite (2)");
	}
	
	@Override
	protected String getName() {
		return suiteName;
	}
	
	public boolean isDone() {
		return done;
	}

	@Override
	public void run(final RunNotifier notifier) {
		runSynchronously(notifier);
	}
	
	private void runSynchronously(final RunNotifier notifier){
		CargoOrchestrator.startTomcat(leibnitzServer);
		super.run(notifier);
		CargoOrchestrator.stopTomcat(leibnitzServer);
		done = true;
		System.out.println("** DONE SET **");
	}
	
	private void runASynchronously(final RunNotifier notifier){
		AsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.submit(new Runnable() {
			@Override
			public void run() {
				CargoOrchestrator.startTomcat(leibnitzServer);
				runTests(notifier);
				CargoOrchestrator.stopTomcat(leibnitzServer);
				System.out.println("** DONE SET **");
				done = true;
			}
		});
	}
	
	private void runTests(final RunNotifier notifier){
		super.run(notifier);
	}
	
}