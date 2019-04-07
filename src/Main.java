import FileSystem.*;
import disk.structure.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static FileSystem fileSystem = new FileSystem();
    public static Parser parser = new Parser();
    public static AllocationStrategy allocationStrategy; /** will be determined in the runtime depends allocation strategy**/
    public static Scanner read = new Scanner(System.in);/** to read from user **/
    public static Disk mydisk;

    public static void main(String[] args) {
        /** eldeeb part to initialize and prepare the disk and allocation strategy **/
        CMD cmd = parser.readLine();

        System.out.print("Enter the size of the disk in KB : ");
        int diskSize = read.nextInt();
        System.out.println("Enter the number of allocation choice");
        System.out.println("(1) Contiguous");
        System.out.println("(2) Indexed");
        System.out.println("(3) Liked");

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

        /** mydisk object will deal with disk **/
        mydisk = Disk.getInstance(allocationStrategy , diskSize);

/**********************************************************************************/
        /** choice the user can do in our application **/
        while (true){
            /** inform the user that he should enter a command **/
            System.out.print("$$ ");
            String command = read.nextLine();

            if(command.equals("exit"))
                break;
            /**else
             should execute the command ***/
        }
/********************************************************************************/
        FileSystem f = new FileSystem();
        ArrayList<String> path= new ArrayList<>();
        path.add("root");
        f.createFile(path, "File",5);
        f.createFile(path, "File1",10);
        f.createFile(path, "File2",12);
        f.createDir(path, "New Folder");

        path.add("New Folder");
        f.createDir(path, "Sherif");
        f.createFile(path, "LOL", 5);

        path.add("sherif");
        f.createFile(path, "LOL", 5);
        f.createFile(path, "Good", 5);
        f.createDir(path, "Good");

        f.displayDiskStructure();


        f.deleteFile(path, "lol");
        f.displayDiskStructure();

        // remove last element
        path.remove(path.size()-1);
        f.deleteDir(path, "sherif");
        f.displayDiskStructure();

        String filePath = "files/fileSystem.json";
        f.save(filePath);
        f.load(filePath);
        System.out.println();
        f.displayDiskStructure();


    }
}
