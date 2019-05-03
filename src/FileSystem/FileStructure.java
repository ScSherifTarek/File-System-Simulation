package FileSystem;
import java.util.ArrayList;
import java.util.Collection;

public class FileStructure {
	public String name;
	public int siz;
	private int alloc;
	public FileStructure()
	{
		siz=0;
	}
	public FileStructure(String n)
	{
		name = n;
		siz = 0;
	}
	public FileStructure(String n, int s)
	{
		name = n;
		siz = s;
	}
	public FileStructure(String n, int s, int alloc)
	{
		name = n;
		siz = s;
		this.alloc = alloc;
	}
	public String toString()
	{
		return "-"+this.name;
	}

	public int getAlloc() {
		return alloc;
	}

	public void setAlloc(int alloc) {
		this.alloc = alloc;
	}
}
