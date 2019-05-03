import FileSystem.*;
import disk.structure.*;
import Parser.*;


import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Main {
    public static FileSystem fileSystem = new FileSystem();
    public static Parser parser = new Parser();
    public static CMD cmd = new CMD();
    public static AllocationStrategy allocationStrategy; /** will be determined in the runtime depends allocation strategy**/
    public static Scanner read = new Scanner(System.in);/** to read from user **/
    public static Disk disk;
    public static String fileSystemFile = "files/fileSystem.json";

    public static void init()
    {
        // init
        try {
            //create FileOutputStream object
            FileOutputStream fos = new FileOutputStream("status.txt");

            /*
             * To create DataOutputStream object from FileOutputStream use,
             * DataOutputStream(OutputStream os) constructor.
             *
             */

            DataOutputStream dos = new DataOutputStream(fos);

            /** write the disk size on the file **/
            dos.writeInt(0);
            dos.writeInt(0);
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
            //  return false;
        }
    }

    public static void main(String[] args) {
        //init();
        Disk.loadStatus();
        if(Disk.getActualSize() != 0) {
            fileSystem.load(fileSystemFile);
            disk = Disk.getInstance();
        }else {
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

        }




        /** choice the user can do in our application **/
        while (true){
            cmd = parser.readLine();
            if(cmd == null)
            {
                System.out.println("Error");
            }
            else if(cmd.action.equals("CreateFile"))
            {
                fileSystem.createFile(cmd.path, cmd.fileName, cmd.size, disk);
                fileSystem.save(fileSystemFile);
            }
            else if(cmd.action.equals("CreateFolder"))
            {
                fileSystem.createDir(cmd.path, cmd.fileName);
                fileSystem.save(fileSystemFile);
            }
            else if(cmd.action.equals("DeleteFile"))
            {
                fileSystem.deleteFile(cmd.path, cmd.fileName, disk);
                fileSystem.save(fileSystemFile);
            }
            else if(cmd.action.equals("DeleteFolder"))
            {
                fileSystem.deleteDir(cmd.path, cmd.fileName, disk);
                fileSystem.save(fileSystemFile);
            }
            else if(cmd.action.equals("DisplayDiskStatus"))
            {
                disk.dispalyDiskStatus();
            }
            else if(cmd.action.equals("DisplayDiskStructure"))
            {
                fileSystem.displayDiskStructure();
            }
            else
            {
                System.out.println("Error");
            }

        }
    }

}
