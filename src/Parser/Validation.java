package Parser;

public class Validation {


    public boolean validate(String[] splittedCommand)
    {
        if (    splittedCommand[0].equals("CreateFile")||
                splittedCommand[0].equals("CreateFolder")||
                splittedCommand[0].equals("DeleteFile")||
                splittedCommand[0].equals("DeleteFolder")||
                splittedCommand[0].equals("DisplayDiskStatus")||
                splittedCommand[0].equals("DisplayDiskStructure"))
        {
            if (splittedCommand[0].equals("CreateFile"))
            {
                if(splittedCommand.length==3)
                {
                    if (splittedCommand[1].length()> 0 ) //path
                    {
                        if (splittedCommand[2].length()>0) //size
                        {
                            int size = Integer.parseInt(splittedCommand[2]) ;
                            if (size>0)
                            {
                                    return true ;
                            }
                        }
                    }
                }
            }

            else if (splittedCommand[0].equals("CreateFolder") ||
                    splittedCommand[0].equals("DeleteFile")||
                    splittedCommand[0].equals("DeleteFolder"))
            {
                if (splittedCommand[1].length()> 0 )
                {
                    if (splittedCommand.length==2)
                    {
                        return true ;
                    }
                }

            }

            else if (splittedCommand[0].equals("DisplayDiskStatus")||
                        splittedCommand[0].equals("DisplayDiskStructure"))
            {
                if (splittedCommand.length==1)
                {
                    return true ;
                }

            }
        }

        System.out.println("Error-->this is wrong command");
        return false ;


    }

    public String actionType(String action) {

        if (action.equals("CreateFile"))
        {
            return "CreateFile" ;
        }
        else if (action.equals("CreateFolder")||
                    action.equals("DeleteFile")||
                    action.equals("DeleteFolder"))
        {
            return "CreateFolderAndDelete" ;
        }
        else
        {
            return "Display" ;
        }

    }
}
