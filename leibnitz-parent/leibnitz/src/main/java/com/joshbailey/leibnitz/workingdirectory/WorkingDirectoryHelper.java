package com.joshbailey.leibnitz.workingdirectory;

import java.io.IOException;

public final class WorkingDirectoryHelper {

	public static String workingDirectoryFilePath(){
		return identifyLocalTargetDirectory()+"\\leibnitz";
	}
	
	public static String identifyLocalTargetDirectory(){
		String current;
		try {
			current = new java.io.File(".").getCanonicalPath();//C:\dev\sts-bundle\workspaces\testing-playground\soapservice
			return current+"\\target"; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
}
