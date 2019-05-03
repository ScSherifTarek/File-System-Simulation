package FileSystem;

import disk.structure.*;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {
	private DirectoryStructure root = new DirectoryStructure("root");

	/**
	 *
	 * @param path to get the directory in it
	 * @return directory with this path
	 */
	private DirectoryStructure getDirOfPath(ArrayList<String> path)
	{
		if (!path.get(0).toLowerCase().equals(root.getName()))
		{
			return null;
		}

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
	public Boolean createFile(ArrayList<String> path, String name, int siz, Disk disk)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}

		if(!pathDir.createFile(name, siz, disk))
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
	public Boolean deleteFile(ArrayList<String> path, String name, Disk disk)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}

		if(!pathDir.deleteFile(name, disk))
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
	public Boolean deleteDir(ArrayList<String> path, String name, Disk disk)
	{
		DirectoryStructure pathDir = getDirOfPath(path);
		if(pathDir == null)
		{
			System.out.println("Incorrect Path");
			return false;
		}

		if(!pathDir.deleteDir(name, disk))
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
		if(!Disk.saveStatus())
			return  false;

		String json = this.toJson();
		try {
			ExternalFile.writeToFile(path, json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if(Disk.getStrategy().save("files/diskstatus.json") && Disk.save("files/diskStructure.json"))
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
			Disk.getStrategy().load("diskstatus.txt");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(Disk.getStrategy().load("files/diskstatus.json") && Disk.load("files/diskStructure.json"))
			return true;
		return false;
	}
}
