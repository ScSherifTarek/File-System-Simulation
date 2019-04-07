package Parser;

import java.util.ArrayList;

public class CMD {
    public String action ;
    public String fileName ;  // file.txt
    public int size ;  //100 KB
    public ArrayList<String> path ;

    public CMD()
    {
        path = new ArrayList<>() ;
    }
}
