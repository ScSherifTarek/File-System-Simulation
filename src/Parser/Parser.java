package Parser;

import java.util.ArrayList;

public class Parser {

    ArrayList<String> path = new ArrayList<>() ;
    Validation validation = new Validation() ;
    String action ;
    public void splitCommand(String command)
    {
        String [] splittedCommand = command.split(" ");
        action = splittedCommand[0] ;
        if (validation.validate(splittedCommand))
        {
            String type = validation.actionType(action) ;
            putData(type , splittedCommand) ;
        }
        //System.out.println(str[3]) ;
    }

    private void putData(String type, String[] splittedCommand)
    {
        if (type.equals("CreateFile"))
        {
            CreateFileCommand createFileCommand = new CreateFileCommand() ;
            createFileCommand.action = splittedCommand[0] ;
            String buffer  = splittedCommand[1];
            String []splittedBuffer =buffer.split("/") ;

            for (int i=0 ; i<splittedBuffer.length ; ++i)
            {
                createFileCommand.path.add(splittedBuffer[i]) ;
            }

            createFileCommand.fileName = createFileCommand.path.get(createFileCommand.path.size()-1) ;
            createFileCommand.size = Integer.parseInt(splittedCommand[2])  ;

            System.out.println("action : " + createFileCommand.action);
            System.out.println("file name : " + createFileCommand.fileName);
            System.out.println("path : " + createFileCommand.path);
            System.out.println("size : " + createFileCommand.size);

        }

        else if (type.equals("CreateFolderAndDelete"))
        {
            CreateFolderAndDeleteCommand createFolderAndDeleteCommand = new CreateFolderAndDeleteCommand() ;
            createFolderAndDeleteCommand.action = splittedCommand[0] ;
            String buffer  = splittedCommand[1];
            String []splittedBuffer =buffer.split("/") ;
            for (int i=0 ; i<splittedBuffer.length ; ++i)
            {
                createFolderAndDeleteCommand.path.add(splittedBuffer[i]) ;
            }

        }

        else if (type.equals("Display"))
        {
            CMD cmd = new CMD();
            cmd.action = splittedCommand[0] ;
            System.out.println("action : " + cmd.action);
        }
    }
}
