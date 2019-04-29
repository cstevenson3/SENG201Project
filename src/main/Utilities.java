package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Utilities {
	/**
	 * Loads a Properties object from a file
	 * @param path The relative or absolute path to a text file in the Properties format
	 * @return A Properties object containing the properties described by the file
	 */
	public static Properties loadPropertiesFile(String path){
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) { //A file missing should be fixed before the game is run
			e.printStackTrace();
			System.out.println("Fatal error, exiting now");
			System.exit(1);
		} catch (IOException e) { //Most likely from another process using the file, this needs to be resolved outside of the application
			e.printStackTrace();
			System.out.println("Fatal error, exiting now");
			System.exit(1);
		}
		return properties;
	}
	
	public static float clamp(float value, float min, float max) {
	    return Math.max(min, Math.min(max, value));
	}
	
	public static int clamp(int value, int min, int max) {
	    return Math.max(min, Math.min(max, value));
	}

	public static ArrayList<String> namesInDirectory(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> result = new ArrayList<String>();
		for(File file : listOfFiles){
			result.add(FilenameUtils.removeExtension(file.getName()));
		}
		return result;
	}

	public static void writeSerializable(String directory, Serializable object) {
		byte[] serialized = objectToBytes(object);
		writeBytesToFile(directory, serialized);
	}
	
	private static void writeBytesToFile(String directory, byte[] bytes) {
		createNecessaryDirectories(directory);
		try {
			FileUtils.writeByteArrayToFile(new File(directory), bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createNecessaryDirectories(String filePath){
		File file = new File(filePath);
		File directory = new File(file, "..");
		if (!(directory.isDirectory())){
			directory.mkdirs();
		}
	}
	
	private static byte[] fileToBytes(String directory){
		try {
			return Files.readAllBytes(new File(directory).toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static byte[] objectToBytes(Serializable object){
		byte[] result = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(object);
		  out.flush();
		  result = bos.toByteArray();
		  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return result;
	}
	
	private static Object bytesToObject(byte[] bytes){
		if (bytes == null){
			return null;
		}
		Object result = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  result = in.readObject(); 
		  
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {
		  }
		}
		return result;
	}

	public static Object readSerializable(String saveDirectory) {
		return bytesToObject(fileToBytes(saveDirectory));
	}

	public static Object clone(Serializable serializable) {
		return bytesToObject(objectToBytes(serializable));
	}
}
