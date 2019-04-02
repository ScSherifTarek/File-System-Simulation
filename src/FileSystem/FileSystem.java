package FileSystem;

import java.util.ArrayList;

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
	
	public void displayDiskStructure()
	{
		root.structureDisplay();
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

		
//		f.getDirOfPath(path).display();
	}

}
