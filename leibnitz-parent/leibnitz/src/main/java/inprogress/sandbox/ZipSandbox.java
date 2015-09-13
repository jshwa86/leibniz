package inprogress.sandbox;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipSandbox {
//TODO: This is a work-in-progess sample demonstrating how to take a .war, post-process it, and replace a specific file with specific values.
//This idea could be developed further as part of a larger process that starts up multiple servers for an integration test.
	public static void main(String[] args) throws IOException {
		String current = new java.io.File(".").getCanonicalPath();
		System.out.println(current);

		String modifiedPropertiesFilePath = current + "\\wars\\modified.properties";
		PrintWriter writer = new PrintWriter(modifiedPropertiesFilePath, "UTF-8");
		writer.println("endpoint=http://localhost:8082/CXFExampleService/personService");
		writer.close();

		String warFilePath = current + "\\wars\\leibnitz-example-springmvc-app-0.0.1-SNAPSHOT.war";
		ZipFile war = new ZipFile(new File(warFilePath));

		unzip(warFilePath, current + "\\wars\\extracted");
		Collection<String> files = determineZipFiles(current + "\\wars\\extracted", "");

		FileOutputStream fos = new FileOutputStream(current + "\\wars\\modified.war");
		ZipOutputStream zos = new ZipOutputStream(fos);
		for (String file : files) {
			if(file.toString().endsWith("leibnitz.properties")){
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);
				FileInputStream in = new FileInputStream(modifiedPropertiesFilePath);
				System.out.println("**MODIFIED** "+file);
				int len;
				byte[] buffer = new byte[1024];
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				in.close();
			}else{//TODO Collapse logic in these blocks into a single method
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);
				FileInputStream in = new FileInputStream(current+"\\wars\\extracted\\"+file);
				System.out.println("---> "+file);
				int len;
				byte[] buffer = new byte[1024];
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();	
			}
		}
		zos.closeEntry();
		zos.close();
	}

	
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified
	 * by destDirectory (will be created if does not exists)
	 * Credit:
	 * http://www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Extracts a zip entry (file entry)
	 * Credit:
	 * http://www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	private static Collection<String> determineZipFiles(String folderToCompressPath, String destinationFilePath) {
		File folderToCompress = new File(folderToCompressPath);
		Collection<String> fileCollection = new LinkedList<String>();
		processFolder(folderToCompressPath, folderToCompress, fileCollection);
		return fileCollection;
	}

	private static void processFolder(String folderToCompressPath, File thisFolder, Collection<String> fileCollection) {
		String files;
		File[] listOfFiles = thisFolder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				processFolder(folderToCompressPath, listOfFiles[i], fileCollection);
			} else {
				files = listOfFiles[i].getAbsolutePath();
				getZipFilePath(folderToCompressPath, files, fileCollection);
			}
		}
	}

	private static void getZipFilePath(String folderToCompressPath, String file, Collection<String> fileCollection) {
		fileCollection.add(file.substring(folderToCompressPath.length() + 1));
	}
}