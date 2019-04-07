package disk.structure;


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

    public static int allocate(int size)
    {
        int temp = strategy.allocate(diskArray , size);
        size += size;
        freeSpace -= size;
        return temp;
    }

    public static boolean deallocate(int blocks , int fileSize){
        if(strategy.deallocate(diskArray , blocks)) {
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
            if(diskArray[i] > 0)
                System.out.print(1 + " ");
            else
                System.out.print(0 + " ");
        }
    }

    public static AllocationStrategy getStrategy() {
        return strategy;
    }
}
