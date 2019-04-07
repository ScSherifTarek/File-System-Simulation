package Parser;

import java.util.ArrayList;

public class CreateFolderAndDeleteCommand extends CMD {
    public String fileName ;
    public ArrayList<String> path ;

    public CreateFolderAndDeleteCommand()
    {
        path = new ArrayList<>() ;
    }
}
