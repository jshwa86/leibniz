package com.joshbailey.leibniz.testrunner;

import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.joshbailey.leibniz.servers.LeibnitzServer;

public class ServerDependentTestRunner extends BlockJUnit4ClassRunner{

	private LeibnitzServer LeibnitzServer;
	
	public ServerDependentTestRunner(Class<?> klass, LeibnitzServer LeibnitzServer) throws InitializationError {
		super(klass);
		this.LeibnitzServer = LeibnitzServer;
	}
	
	@Override
	protected Object createTest() throws Exception {
		return getTestClass().getJavaClass().getConstructor(LeibnitzServer.getClass()).newInstance(LeibnitzServer);
	}
	
	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		//FIXME: Ignoring these for now, might want to add this back in, in some capacity
//		super.collectInitializationErrors(errors);

//		validateConstructor(errors);
//		validateInstanceMethods(errors);
	}
	
}
