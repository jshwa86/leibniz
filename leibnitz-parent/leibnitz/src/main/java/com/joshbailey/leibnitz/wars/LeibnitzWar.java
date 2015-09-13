package com.joshbailey.leibnitz.wars;

import org.codehaus.cargo.container.deployable.WAR;

import com.joshbailey.leibnitz.workingdirectory.WorkingDirectoryHelper;

public abstract class LeibnitzWar {

	public static enum WarFilePathResolutionStrategy{
		BUILT_LOCALLY {
			@Override
			public String resolve() {
				return WorkingDirectoryHelper.identifyLocalTargetDirectory();
			}
		},
		IMPORTED {
			@Override
			public String resolve() {
				return WorkingDirectoryHelper.workingDirectoryFilePath()+"\\imported-wars";
			}
		};
		
		public abstract String resolve();
	}
	
	private String filePath;
	private String associatedEndpoint;
	private WarFilePathResolutionStrategy fileResolutionStrategy;
	
	public LeibnitzWar(String warName
					   ,String associatedEndpoint
					   ,WarFilePathResolutionStrategy fileResolutionStrategy){
		this.fileResolutionStrategy = fileResolutionStrategy;
		this.filePath = constructFullPathToWar(warName);
		this.associatedEndpoint=associatedEndpoint;
	}
	
	public WAR buildDeployable(){
		WAR deployable = new WAR(filePath);
		return deployable;
	}
	
	private String constructFullPathToWar(String warFileName){
		return fileResolutionStrategy.resolve()+"\\"+warFileName;
	}
	
}
