package FileSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DirectoryStructure {
	private String name;
	private Map<String, FileStructure> files=new HashMap<String, FileStructure>();
	private Map<String, DirectoryStructure> dirs= new HashMap<String, DirectoryStructure>();
	
	public DirectoryStructure()
	{
		
	}
	public DirectoryStructure(String n)
	{
		name = n;
	}
	
	// modify combine
	ArrayList<Integer> allocate(int siz)
	{
		ArrayList<Integer> arr = new ArrayList<>(siz);
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		return arr;
	}
	
	void deallocate(ArrayList<Integer> blocks){}
	
		
	/**
	 * 
	 * @param name the name of the file
	 * @return true if file exists in this directory and false if not
	 */
	private Boolean isFileExist(String name)
	{
		name = name.toLowerCase();
		return files.containsKey(name);
	}
	
	/**
	 * 
	 * @param name the name of the file to be created
	 * @return true if file created Successfully
	 */
	public Boolean createFile(String name, int siz)
	{
		if(!isFileExist(name))
		{
			// modify combine
			files.put(name.toLowerCase(), new FileStructure(name,siz,allocate(siz)));
			return true;
		}
		else
		{
			System.out.println("Not A Valid Name");
			return false;
		}
	}
	
	/**
	 * 
	 * @param name the file to be deleted
	 * @return true if deleted successfully and false if not
	 */
	public Boolean deleteFile(String name)
	{
		name = name.toLowerCase();
		if(!isFileExist(name))
		{
			System.out.println("No file with this name");
			return false;
		}
		
		FileStructure file = getFile(name);
		removeFile(file);
		return true;
	}
	
	/**
	 * 
	 * @param file the file to remove from the current directory
	 */
	private void removeFile(FileStructure file)
	{
		//deallocate the file blocks from disc
		deallocate(file.getAllocated());
		files.remove(file.name.toLowerCase());
	}
	/**
	 * 
	 * @param name the name of the file to get
	 * @return the file needed or null if not exist
	 */
	public FileStructure getFile(String name)
	{
		return files.get(name.toLowerCase());
	}
	

	/**
	 * 
	 * @param name the file to check for
	 * @return true if the file exists and false if not
	 */
	private Boolean isDirExist(String name)
	{
		name = name.toLowerCase();
		return dirs.containsKey(name);
	}
	
	/**
	 * 
	 * @param name directory name
	 * @return true if file created successfully and false if not
	 */
	public Boolean createDir(String name)
	{
		if(!isDirExist(name))
		{
			// modify combine
			dirs.put(name.toLowerCase(), new DirectoryStructure(name));
			return true;
		}
		else
		{
			System.out.println("Not A Valid Name");
			return false;
		}
	}
	
	/**
	 * 
	 * @param name the name of the directory to be deleted
	 * @return true if deleted successfully and false if not
	 */
	public Boolean deleteDir(String name)
	{
		name = name.toLowerCase();
		if(!isDirExist(name))
		{
			System.out.println("No Directory with this name");
			return false;
		}
		
		DirectoryStructure dir = getDir(name);
		removeDir(dir);
		return true;
	}
	
	/**
	 * 
	 * @param dir the directory to be deleted
	 */
	private void removeDir(DirectoryStructure dir)
	{
		// remove all files from this directory
		for(Map.Entry<String, FileStructure> m:dir.files.entrySet()){  
			removeFile(m.getValue());
		}
		
		// remove the directories from this directory
		for(Map.Entry<String, DirectoryStructure> m:dir.dirs.entrySet()){  
			removeDir(m.getValue());  
		} 
		
		// remove the directory itself
		dirs.remove(dir.name.toLowerCase());
	}
	/**
	 * 
	 * @param name the name of the file to get
	 * @return the file needed or null if not exist
	 */
	public DirectoryStructure getDir(String name)
	{
		return dirs.get(name.toLowerCase());
	}
	
	public void display()
	{
		for(Map.Entry<String, FileStructure> m:files.entrySet()){  
			System.out.println(m.getValue());  
		}  
		
		for(Map.Entry<String, DirectoryStructure> m:dirs.entrySet()){  
			System.out.println(m.getValue());  
		} 
	}
	
	/**
	 * prints the structure of the directory
	 */
	public void structureDisplay()
	{
		System.out.println(this);
		printStructure(this, 0);
	}
	/**
	 * 
	 * @param dir the directory to display
	 * @param level the level the user want to display this directory in
	 */
	private void printStructure(DirectoryStructure dir, int level)
	{	
		String treeSpaces = "";
		for(int i=-1;i<level;i++)
			treeSpaces += "  ";
		
		for(Map.Entry<String, FileStructure> m:dir.files.entrySet()){  
			System.out.println(treeSpaces+m.getValue());  
		}
		for(Map.Entry<String, DirectoryStructure> m:dir.dirs.entrySet()){ 
			System.out.println(treeSpaces+m.getValue());
			printStructure(m.getValue(), level+1);
		} 
		
	}
	public String toString()
	{
		return "+"+this.name;
	}
	
	public String getName()
	{
		return this.name;
	}
//	public Boolean renameFile(String oldName, String newName)
//	{
//		oldName = oldName.toLowerCase();
//		FileStructure file= files.get(oldName);
//		if(file==null)
//		{
//			System.out.println("No file with this name");
//			return false;
//		}
//		
//		if(isFileExist(newName))
//		{
//			System.out.println("Not A Valid File Name");
//			return false;
//		}
//		
//		file.name = newName;
//		files.remove(oldName);
//		
//		files.put(newName.toLowerCase(), file);
//		return true;
//		
//	}
//	public Boolean fileWrite(String name,int siz)
//	{
//		FileStructure file= files.get(name);
//		if(file == null)
//		{
//			System.out.println("No file with this name");
//			return false;
//		}
//		
//		file.siz += siz;
//		return true;
//	}
//	
//	public Boolean truncate(String name)
//	{
//		FileStructure file= files.get(name);
//		if(file == null)
//		{
//			System.out.println("No file with this name");
//			return false;
//		}
//			
//		
//		file.siz = 0;
//		return true;
//	}

}
