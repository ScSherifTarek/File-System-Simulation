package disk.structure;

import java.util.ArrayList;

abstract public class AllocationStrategy {

    abstract ArrayList<Integer> allocate(int siz);

    abstract void deallocate(ArrayList<Integer> blocks);
}
