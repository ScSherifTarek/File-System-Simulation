package Parser;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    ArrayList<String> path = new ArrayList<>() ;
    Validation validation = new Validation() ;
    String action ;


    public CMD splitCommand(String command)
    {
        String [] splittedCommand = command.split(" ");
        action = splittedCommand[0] ;
        if (validation.validate(splittedCommand))
        {
            String type = validation.actionType(action) ;
            return putData(type , splittedCommand) ;
        }
        return null ;

        //System.out.println(str[3]) ;
    }

    private CMD putData(String type, String[] splittedCommand)
    {
        CMD cmd = new CMD() ;
        if (type.equals("CreateFile"))
        {
            cmd.action = splittedCommand[0] ;
            String buffer  = splittedCommand[1];
            String []splittedBuffer =buffer.split("/") ;

            for (int i=0 ; i<splittedBuffer.length ; ++i)
            {
                cmd.path.add(splittedBuffer[i]) ;
            }

            cmd.fileName = cmd.path.get(cmd.path.size()-1) ;
            cmd.size = Integer.parseInt(splittedCommand[2])  ;

            System.out.println("action : " + cmd.action);
            System.out.println("file name : " + cmd.fileName);
            System.out.println("path : " + cmd.path);
            System.out.println("size : " + cmd.size);

            return cmd;
        }

        else if (type.equals("CreateFolderAndDelete"))
        {
            cmd.action = splittedCommand[0] ;
            String buffer  = splittedCommand[1];
            String []splittedBuffer =buffer.split("/") ;

            for (int i=0 ; i<splittedBuffer.length ; ++i)
            {
                cmd.path.add(splittedBuffer[i]) ;
            }

            cmd.fileName = cmd.path.get(cmd.path.size()-1) ;
            cmd.size = -1  ;

            System.out.println("action : " + cmd.action);
            System.out.println("file name : " + cmd.fileName);
            System.out.println("path : " + cmd.path);
            System.out.println("size : " + cmd.size);

            return cmd;
        }

        else if (type.equals("Display"))
        {
            cmd.action = splittedCommand[0] ;
            cmd.path = null ;


            cmd.fileName = null ;
            cmd.size = -1  ;

            System.out.println("action : " + cmd.action);
            System.out.println("file name : " + cmd.fileName);
            System.out.println("path : " + cmd.path);
            System.out.println("size : " + cmd.size);

        }
        return cmd;
    }

    public CMD readLine ()
    {
        Scanner input = new Scanner(System.in);
        String command  = input.nextLine() ;
        CMD cmd = splitCommand(command) ;
        cmd.path.remove(cmd.size-1);
        return cmd;
    }
}
