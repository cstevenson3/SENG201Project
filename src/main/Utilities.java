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

/**
 * Utilities contains utility methods such as value clamping and file serialization
 * @author Cameron Stevenson
 *
 */
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
	
	/**
	 * Clamp a float value into a range
	 * @param value The value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The value clamped between minimum and maximum
	 */
	public static float clamp(float value, float min, float max) {
	    return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * Clamp an int value into a range
	 * @param value The value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The value clamped between minimum and maximum
	 */
	public static int clamp(int value, int min, int max) {
	    return Math.max(min, Math.min(max, value));
	}

	/**
	 * Get all file names (without extension) in a directory
	 * @param directory The directory to look in
	 * @return An ArrayList of file names
	 */
	public static ArrayList<String> namesInDirectory(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> result = new ArrayList<String>();
		for(File file : listOfFiles){
			result.add(FilenameUtils.removeExtension(file.getName()));
		}
		return result;
	}

	/**
	 * Write a serializable object to file
	 * @param directory The file location
	 * @param object The object to write
	 */
	public static void writeSerializable(String directory, Serializable object) {
		byte[] serialized = objectToBytes(object);
		writeBytesToFile(directory, serialized);
	}
	
	/**
	 * Write a byte array to file
	 * @param directory The file location
	 * @param bytes The byte array to write
	 */
	private static void writeBytesToFile(String directory, byte[] bytes) {
		createNecessaryDirectories(directory);
		try {
			FileUtils.writeByteArrayToFile(new File(directory), bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create any necessary parent directories for a file
	 * @param filePath The file location
	 */
	public static void createNecessaryDirectories(String filePath){
		File file = new File(filePath);
		File directory = new File(file, "..");
		if (!(directory.isDirectory())){
			directory.mkdirs();
		}
	}
	
	/**
	 * Read a file into a byte array
	 * @param directory The file location
	 * @return The byte array
	 */
	private static byte[] fileToBytes(String directory){
		try {
			return Files.readAllBytes(new File(directory).toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Convert a serializable object to a byte array
	 * @param object The object
	 * @return The byte array
	 */
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
	
	/**
	 * Convert a serialized byte array back to its object
	 * @param bytes The byte array
	 * @return The object
	 */
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

	/**
	 * Read a serialized file to an object
	 * @param directory The file location
	 * @return The object
	 */
	public static Object readSerializable(String directory) {
		return bytesToObject(fileToBytes(directory));
	}

	/**
	 * Clone a serializable object
	 * @param serializable The object
	 * @return The clone
	 */
	public static Object clone(Serializable serializable) {
		return bytesToObject(objectToBytes(serializable));
	}
}
