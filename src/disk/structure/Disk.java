package disk.structure;

import java.util.ArrayList;

public class Disk {
    private int actualSize; /** the whole size of my disk **/
    private int freeSpace; /** the current unused space of my disk **/
    private int size; /** the current used space of my disk**/
    private int []diskArray; /** virtual disk **/

    private static Disk obj = null; /** the only instance of the disk in my app **/
    private AllocationStrategy strategy;


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
            this.diskArray[i] = -1;
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

    ArrayList<Integer> allocate(int size)
    {
        ArrayList<Integer> temp = strategy.allocate(size);
        this.size += size;
        this.freeSpace -= size;
        return temp;
    }

    void deallocate(ArrayList<Integer> blocks){
        strategy.deallocate(blocks);
        this.size -= blocks.size();
        this.freeSpace += blocks.size();
    }

    public int getActualSize() {
        return actualSize;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getSize() {
        return size;
    }

}
