package disk.structure;

import java.util.ArrayList;

public class Linked extends AllocationStrategy {

    public Linked(){
        status = LINKED_BLOCK;
    }


    @Override
    public int allocate(int[] diskArray, int siz) {
        ArrayList empty = new ArrayList();
        for(int i=0 ; i<diskArray.length; i++){
            if(diskArray[i] == EMPTY_BLOCK){
                empty.add(i);
            }
        }
        int parent = -1;

        if(empty.size() >= siz){
            parent = (int) empty.get(0);
            for(int i=0; i<siz-1; i++){
                diskArray[(int) empty.get(i)] = (int) empty.get(i+1);
            }
            diskArray[siz-1] = LINKED_BLOCK;
        }
        return parent;
    }

    @Override
    public boolean deallocate(int[] diskArray, int fistBlock) {
        if(blockes.containsKey(fistBlock)){
            int next = diskArray[fistBlock];
            diskArray[fistBlock] = EMPTY_BLOCK;
            while (next != LINKED_BLOCK){
                int next2 = diskArray[next];
                diskArray[next] = EMPTY_BLOCK;
                next = next2;
            }
            return true;
        }
        return false;
    }

}
