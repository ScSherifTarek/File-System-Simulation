package disk.structure;


import FileSystem.ExternalFile;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.*;
import java.util.ArrayList;

public class Disk {
    private static int actualSize; /** the whole size of my disk **/
    private static int freeSpace; /** the current unused space of my disk **/
    private static int size; /** the current used space of my disk**/
    private static int []diskArray; /** virtual disk **/
    private static Disk obj = null; /** the only instance of the disk in my app **/
    private static AllocationStrategy strategy;


    /**
     *
     * @param diskSize ==> the actual size of the disk
     */
    private Disk(AllocationStrategy strategy , int diskSize){
        /**
         * initialize all attributes
         */
        this.actualSize = diskSize;
        this.freeSpace = diskSize;
        this.size = 0;
        this.strategy = strategy;
        this.diskArray = new int[diskSize];
        /** initialize the whole disk as empty **/
        for(int i=0 ; i<actualSize; i++){
            this.diskArray[i] = 0;
        }
    }

    /**
     *
     * @param diskSize ==> the size of the disk
     * @return ==> the only instance of my disk
     */
    public static Disk getInstance(AllocationStrategy strategy , int diskSize){

        if(obj == null){
            obj = new Disk(strategy , diskSize);
        }
        return obj;
    }

    /**
     *
     * @return the only instance of the disk
     */
    public static Disk getInstance(){
        return obj;
    }

    public static int allocate(int siz)
    {
        int temp = strategy.allocate(diskArray , siz);
        if(temp != -1){
            size += siz;
            freeSpace -= siz;
        }
        return temp;
    }

    public static boolean deallocate(int blocks , int fileSize){
        if(strategy.deallocate(diskArray , blocks)) {
            System.out.println("LOL");
            size -= fileSize;
            freeSpace += fileSize;
            return true;
        }
        return false;
    }

    public static int getActualSize() {
        return actualSize;
    }

    public static int getFreeSpace() {
        return freeSpace;
    }

    public static int getSize() {
        return size;
    }

    public static void dispalyDiskStatus(){
        System.out.println("empty space ==> " + getFreeSpace());
        System.out.println("allocated space ==> " + getSize());
        System.out.println("*******disk**visualization********");

        for(int i=0; i<diskArray.length; i++){
//            for(int k=0; k<50; k++){
                if(diskArray[i] != strategy.EMPTY_BLOCK)
                    System.out.print(1 + " ");
                else
                    System.out.print(0 + " ");
//            }
            //System.out.println();
        }
        System.out.println("****************************");
    }

    public static AllocationStrategy getStrategy() {
        return strategy;
    }

    public static Boolean saveStatus() {
        try {
            //create FileOutputStream object
            FileOutputStream fos = new FileOutputStream("files/status.txt");

            /*
             * To create DataOutputStream object from FileOutputStream use,
             * DataOutputStream(OutputStream os) constructor.
             *
             */

            DataOutputStream dos = new DataOutputStream(fos);

            /** write the disk size on the file **/
            dos.writeInt(getActualSize());
            /** write the allocation strategy of the disk **/
            dos.writeInt(strategy.status);

            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean loadStatus() {
        try {
            //create FileInputStream object
            FileInputStream fin = new FileInputStream("files/status.txt");

            /*
             * To create DataInputStream object, use
             * DataInputStream(InputStream in) constructor.
             */

            DataInputStream din = new DataInputStream(fin);

            /*
             * To read a Java integer primitive from file, use
             * byte readInt() method of Java DataInputStream class.
             *
             * This method reads 4 bytes and returns it as a int value.
             */
            /** read int **/
            int s = din.readInt();
            /** read allocation strategy **/
            int a = din.readInt();

            if(a == strategy.CONTIGUOUS_BLOCK){
                System.out.println("Contiguous");
                getInstance(new Contiguous() , s);
            }else if (a == strategy.LINKED_BLOCK){
                System.out.println("Linked");
                getInstance(new Linked() , s);
            }else if(a == strategy.INDEXED_BLOCK){
                System.out.println("Indexed");
                getInstance(new Indexed() , s);
            }
            /*
             * To close DataInputStream, use
             * void close() method.
             */
            din.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @return JSON String represents the disk blocks
     */
    public static String toJson() {
        JSONSerializer serializer = new JSONSerializer();
        String json = serializer.deepSerialize(Disk.diskArray);
        return json;
    }

    /**
     * @param json the JSON string to create the blocks mapping from
     */
    public static void fromJson(String json) {

        ArrayList<Integer> temp = new JSONDeserializer<ArrayList>().deserialize( json );
        Disk.diskArray = new int[temp.size()];
        actualSize = temp.size();
        size=0;
        freeSpace=0;
        for(int i=0;i<temp.size();i++)
        {
            if(temp.get(i) != strategy.EMPTY_BLOCK )
            {
                size++;
            }
            else
            {
                freeSpace++;
            }
            diskArray[i] = temp.get(i);
        }
    }

    /**
     * @param path the path to save the root(JSON) in
     * @return true if saved correctly and false if not
     */
    public static Boolean save(String path) {
        String json = Disk.toJson();
        try {
            ExternalFile.writeToFile(path, json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param path the path to load the root(JSON) from
     * @return true if loaded correctly and false if not
     */
    public static Boolean load(String path) {
        try {
            String json = ExternalFile.readFromFile(path);
            Disk.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
