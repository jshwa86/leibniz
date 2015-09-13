package com.joshbailey.leibniz.testrunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.joshbailey.leibniz.annotations.LiebnizTest;
import com.joshbailey.leibniz.servers.LeibnitzServer;
import com.joshbailey.leibniz.tests.ServerDependentTest;

public class LeibnizTestSuiteRunner extends Suite{

	//TODO Making this immutable would be good
	private static final Set<LeibnitzServer> LeibnitzServers;
	
	static{
		LeibnitzServers = new HashSet<LeibnitzServer>();
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			     .setUrls(ClasspathHelper.forPackage(""))
			     .setScanners(new SubTypesScanner(),
			    		      new TypeAnnotationsScanner()));
		Set<Class<? extends LeibnitzServer>> servers = reflections.getSubTypesOf(LeibnitzServer.class);
		Set<Class<? extends LeibnitzServer>> LeibnitzServerTypes =  servers;
		for(Class<? extends LeibnitzServer> LeibnitzServerType : LeibnitzServerTypes){
			LeibnitzServer instantiated = instantiate(LeibnitzServerType);
			if(null != instantiated){
				LeibnitzServers.add(instantiated);				
			}
		}
	}
	
	private static LeibnitzServer instantiate(Class<? extends LeibnitzServer> LeibnitzServerType){
		try {
			return LeibnitzServerType.getConstructor().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public LeibnizTestSuiteRunner(Class testClass) throws InitializationError, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super(testClass,new Class[0]);
		Map<LeibnitzServer,Collection<Class<?>>> tests = new HashMap<LeibnitzServer,Collection<Class<?>>>();
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			     .setUrls(ClasspathHelper.forPackage(""))
			     .setScanners(new SubTypesScanner(),
			    		      new TypeAnnotationsScanner()));
		Set<Class<?>> subTypes = reflections.getTypesAnnotatedWith(LiebnizTest.class);
		for(Class clazz : subTypes){
			if(clazz.equals(ServerDependentTest.class)){
				continue;
			}
			LeibnitzServer LeibnitzServer = extractLiebnizServerFrom(clazz);
			
			if(!tests.containsKey(LeibnitzServer)){
				tests.put(LeibnitzServer,new LinkedList<Class<?>>());
			}
			tests.get(LeibnitzServer).add(clazz);
		}
		
		for(LeibnitzServer server : tests.keySet()){
			super.getChildren().add(	
				new ServerDependentSuite(server
						      ,new ServerDependentTestRunnerBuilder(server)
							  ,tests.get(server).toArray(new Class[tests.get(server).size()]))); //Ok, this line is really fugly
		}
	}
	
	public static LeibnitzServer extractLiebnizServerFrom(Class<? extends ServerDependentTest> liebnizTestClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//FIXME improve the way this handles abstract classes, for now this case is avoided by a hard-coded check but this won't work forever
		//TODO I am not particularly happy with these constructor invocations - is there some way to handle this better
		
		//TODO: Below line - can figure out how to scan all available leibnitz servers and identify which one the test needs
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			     .setUrls(ClasspathHelper.forPackage(""))
			     .setScanners(new SubTypesScanner(),
			    		      new TypeAnnotationsScanner()));
		Set<Class<? extends LeibnitzServer>> subTypes = reflections.getSubTypesOf(LeibnitzServer.class);
		LeibnitzServer requiredServer = identifyConstructorRequiredParameter(liebnizTestClass);
		return requiredServer;
	}
	
	private static LeibnitzServer identifyConstructorRequiredParameter(Class<? extends ServerDependentTest> liebnizTestClass){
		Constructor<?>[] constructors = liebnizTestClass.getConstructors();
		//TODO: Validate the constructors available on the test class here.
		//For now, assuming only one constructor, probably not a good thing
		Class<?>[] parameterTypes = constructors[0].getParameterTypes();
		
		//Assuming a single parameter here. This is dangerous.
		for(LeibnitzServer server : LeibnitzServers){
			if(parameterTypes[0].equals(server.getClass())){
				return server;
			}
		}
		//TODO Throw an exception or something here
		return null;
	}
	
	@Override
	public void run(final RunNotifier notifier) {
		super.run(notifier);
		//Begin: Ugly hack to force waiting until done
		boolean allDone = true;
		List<Runner> runners = super.getChildren();
		do{
			allDone = true;
			for(Runner runner : runners){
				if(!((ServerDependentSuite)runner).isDone()){
					allDone=false;
					continue;
				}
			}
		}while(!allDone);
	}
	
}