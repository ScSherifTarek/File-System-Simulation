import FileSystem.*;
import disk.structure.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static FileSystem fileSystem = new FileSystem();
    public static Parser parser = new Parser();
    public static CMD cmd;
    public static AllocationStrategy allocationStrategy; /** will be determined in the runtime depends allocation strategy**/
    public static Scanner read = new Scanner(System.in);/** to read from user **/
    public static Disk disk;
    public static String fileSystemFile = "files/fileSystem.json";

    public static void main(String[] args) {
        fileSystem.load(fileSystemFile);

        System.out.print("Enter the size of the disk in KB : ");
        int diskSize = read.nextInt();
        System.out.println("Enter the number of allocation method");
        System.out.println("(1) Contiguous");
        System.out.println("(2) Indexed");
        System.out.println("(3) Linked");

        int choice = read.nextInt();
        switch (choice){
            case 1:
                allocationStrategy = new Contiguous();
                break;
            case 2:
                allocationStrategy = new Indexed();
                break;
            case 3:
                allocationStrategy = new Linked();
                break;
        }

        /** disk object will deal with disk **/
        disk = Disk.getInstance(allocationStrategy , diskSize);

        /** choice the user can do in our application **/
        while (true){
            cmd = parser.readLine();
            if(cmd.action == "CreateFile")
            {
                fileSystem.createFile(cmd.path, cmd.fileName, cmd.size, disk);
            }
            else if(cmd.action == "CreateFolder")
            {
                fileSystem.createDir(cmd.path, cmd.fileName);
            }
            else if(cmd.action == "DeleteFile")
            {
                fileSystem.deleteFile(cmd.path, cmd.fileName, disk);
            }
            else if(cmd.action == "DeleteFolder")
            {
                fileSystem.deleteDir(cmd.path, cmd.fileName, disk);
            }
            else if(cmd.action == "DisplayDiskStatus")
            {
                disk.dispalyDiskStatus();
            }
            else if(cmd.action == "DisplayDiskStructure")
            {
                fileSystem.displayDiskStructure();
            }
            else
            {
                System.out.println("Error");
                break;
            }
            fileSystem.save(fileSystemFile);
        }
    }
}
