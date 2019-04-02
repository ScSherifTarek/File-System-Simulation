package FileSystem;
import java.util.ArrayList;
import java.util.Collection;

public class FileStructure {
	public String name;
	public int siz;
	private ArrayList<Integer> alloc = new ArrayList<>();
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
	public FileStructure(String n, int s, ArrayList<Integer> alloc)
	{
		name = n;
		siz = s;
		setAllocated(alloc);
	}
	public String toString()
	{
		return "-"+this.name;
	}
	public void setAllocated(Collection<Integer> alloc)
	{
		this.alloc.clear();
		this.alloc.addAll(alloc);
	}
	public ArrayList<Integer> getAllocated()
	{
		return this.alloc;
	}
}
