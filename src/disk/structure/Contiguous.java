package disk.structure;

import java.util.ArrayList;
import java.util.Collection;

public class Contiguous extends AllocationStrategy {

    public Contiguous(){
        status = CONTIGUOUS_BLOCK;
    }

    @Override
    public int allocate(int[] diskArray, int siz) {
        ArrayList<Integer> temp = new ArrayList<>();
        boolean empty = false;
        for(int i=0 ; i<=diskArray.length - siz; i++){
            empty = true;
            for(int j=i; j<i+siz; j++){
                if(diskArray[j] == EMPTY_BLOCK){
                    empty &= true;
                }else {
                    empty &= false;
                    break;
                }
            }
            if(empty){
                for(int j=i; j<i+siz; j++){
                    diskArray[j] = CONTIGUOUS_BLOCK;
                    temp.add(j);
                }
                break;
            }
        }
        int parent = -1;
        ArrayList<Integer> child = new ArrayList<>();
        if(temp.size() >= siz) {

            parent = temp.get(0);

            for (int i = 1; i < siz; i++) {
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
