package disk.structure;

import java.util.ArrayList;

public class Indexed extends AllocationStrategy{

    public Indexed(){
        status = INDEXED_BLOCK;
    }

    @Override
    public int allocate(int[] diskArray, int siz) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i=0 ; i<diskArray.length; i++){
            if(diskArray[i] == EMPTY_BLOCK){
                temp.add(i);
            }
        }

        int parent = -1;
        ArrayList<Integer> child = new ArrayList<>();
        if(temp.size()>= siz){
            parent = temp.get(0);
            diskArray[temp.get(0)] = INDEXED_BLOCK;


            for(int i=1; i<siz; i++){
                diskArray[temp.get(i)] = INDEXED_BLOCK;
                child.add(temp.get(i));
            }
            blockes.put(parent , child);
        }

        return parent;
    }

    @Override
    public boolean deallocate(int[] diskArray, int fistBlock) {
        if(blockes.containsKey(fistBlock)){
            ArrayList<Integer> tobefree = blockes.get(fistBlock);
            diskArray[fistBlock] = EMPTY_BLOCK;
            for(int i=0; i<tobefree.size(); i++){
                diskArray[tobefree.get(i)] = EMPTY_BLOCK;
            }
            return true;
        }
        return false;
    }


}
