package FileSystem;

import disk.structure.*;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {
	private static DirectoryStructure root = new DirectoryStructure("root");
	/** eldeeb part for disk **/
	private static AllocationStrategy allocationStrategy; /** will be determined in the runtime depends allocation strategy**/
	private static Scanner read = new Scanner(System.in);/** to read from user **/
	private static Disk mydisk;

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
			ExternalFile.writeToFile(path, json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if(Disk.getStrategy().save("diskstatus.txt"))
			return true;
		return false;
	}
	
	/**
	 * @param path the path to load the root(JSON) from
	 * @return true if loaded correctly and false if not
	 */
	public Boolean load(String path)
	{
		try {
			String json = ExternalFile.readFromFile(path);
			this.fromJson(json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(Disk.getStrategy().load("diskstatus.txt"))
			return true;
		return false;
	}
	
	public static void main(String[] args) {

		/** eldeeb part to initialize and prepare the disk and allocation strategy **/

		System.out.print("Enter the size of the disk in KB : ");
		int diskSize = read.nextInt();
		System.out.println("Enter the number of allocation choice");
		System.out.println("(1) Contiguous");
		System.out.println("(2) Indexed");
		System.out.println("(3) Liked");

		int choice = read.nextInt();
		switch (choice){
			case 1:
				allocationStrategy = new Contiguous();
				break;
			case 2:
				allocationStrategy = new Indexed();
				break;
			case 3:
				allocationStrategy = new Linked();
				break;
		}

		/** mydisk object will deal with disk **/
		mydisk = Disk.getInstance(allocationStrategy , diskSize);

/**********************************************************************************/
		/** choice the user can do in our application **/
		while (true){
			/** inform the user that he should enter a command **/
			System.out.print("$$ ");
			String command = read.nextLine();

			if(command.equals("exit"))
				break;
			/**else
			 	should execute the command ***/
		}
/********************************************************************************/
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
	

}
