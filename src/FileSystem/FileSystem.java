package FileSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class FileSystem {
	private DirectoryStructure root = new DirectoryStructure("root");
	
	/**
	 * 
	 * @param path to get the directory in it
	 * @return directory with this path
	 */
	private DirectoryStructure getDirOfPath(ArrayList<String> path)
	{
		if(path.get(0).toLowerCase() != root.getName())
			return null;
		
		DirectoryStructure currentDir = root;
		for(int i=1;i<path.size();i++)
		{
			currentDir = currentDir.getDir(path.get(i));
		}
		return currentDir;
	}
	
	
	/**
	 * 
	 * @param path arraylist of directories' names
	 * @param name the name of the file
	 * @param siz the size of the file
	 * @return true if file created successfully and false if not
	 */
	public Boolean createFile(ArrayList<String> path, String name, int siz)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}
		
		if(!pathDir.createFile(name, siz))
			return false;
		
		
		System.out.println("File created successfully");
		return true;
	}
	
	/**
	 * 
	 * @param path arraylist of directories' names
	 * @param name the name of the file
	 * @return true if deleted and false if not
	 */
	public Boolean deleteFile(ArrayList<String> path, String name)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}
		
		if(!pathDir.deleteFile(name))
			return false;
		
		System.out.println("File deleted successfully");
		return true;
	}
	
	/**
	 * 
	 * @param path arraylist of directories' names
	 * @param name the name of the file
	 * @param siz the size of the file
	 * @return true if file created successfully and false if not
	 */
	public Boolean createDir(ArrayList<String> path, String name)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}
		
		if(!pathDir.createDir(name))
			return false;
		
		
		System.out.println("Directory created successfully");
		return true;
	}
	
	
	/**
	 * 
	 * @param path arraylist of directories' names
	 * @param name the name of the file
	 * @return true if deleted and false if not
	 */
	public Boolean deleteDir(ArrayList<String> path, String name)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}
		
		if(!pathDir.deleteDir(name))
			return false;
		
		System.out.println("Directory deleted successfully");
		return true;
	}
	
	/**
	 * Display the disk structure
	 */
	public void displayDiskStructure()
	{
		root.structureDisplay();
	}
	
	
	/**
	 * @return the root directory
	 */
	public DirectoryStructure getRoot()
	{
		return root;
	}
	
	/**
	 * @return JSON String represents the root directory
	 */
	public String toJson()
	{
		JSONSerializer serializer = new JSONSerializer();
		String json = serializer.deepSerialize(this.getRoot());
		return json;
	}
	
	/**
	 * @param json the JSON string to create the root directory from
	 */
	public void fromJson(String json)
	{
		this.root = new JSONDeserializer<DirectoryStructure>().deserialize( json );
	}
	
	/**
	 * @param path the path to save the root(JSON) in 
	 * @return true if saved correctly and false if not
	 */
	public Boolean save(String path)
	{
		String json = this.toJson();
		try {
			writeToFile(path, json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param path the path to load the root(JSON) from
	 * @return true if loaded correctly and false if not
	 */
	public Boolean load(String path)
	{
		try {
			String json = readFromFile(path);
			this.fromJson(json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileSystem f = new FileSystem();
		ArrayList<String> path= new ArrayList<>();
		path.add("root");
		f.createFile(path, "File",5);		
		f.createFile(path, "File1",10);
		f.createFile(path, "File2",12);
		f.createDir(path, "New Folder");
		
		path.add("New Folder");
		f.createDir(path, "Sherif");
		f.createFile(path, "LOL", 5);

		path.add("sherif");
		f.createFile(path, "LOL", 5);
		f.createFile(path, "Good", 5);
		f.createDir(path, "Good");

		f.displayDiskStructure();
		
		
		f.deleteFile(path, "lol");
		f.displayDiskStructure();

		// remove last element
		path.remove(path.size()-1);
		f.deleteDir(path, "sherif");
		f.displayDiskStructure();

		String filePath = "files/fileSystem.json";
		f.save(filePath);
		f.load(filePath);
		System.out.println();
		f.displayDiskStructure();
	}
	
	/**
	 * @param path the path of the file to read from
	 * @return the string within the file
	 */
	public static String readFromFile(String path) throws IOException
	{
		String s="";		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		s = reader.readLine();
		reader.close();
		return s;
	}
	
	/**
	 * @param path the path of the file to write to
	 * @param s the string to write in the file
	 */
	public static void writeToFile(String path, String s) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(s);
		writer.close();
	}

}
