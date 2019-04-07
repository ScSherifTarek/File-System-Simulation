package Parser;

import java.util.ArrayList;

public class CreateFileCommand extends CMD {

    //example CreateFile root/folder/file.txt 100
     //create file
    public String fileName ;  // file.txt
    public int size ;  //100 KB
    public ArrayList<String> path ; //root/folder

    public CreateFileCommand ()
    {
        path = new ArrayList<>() ;
    }

}
