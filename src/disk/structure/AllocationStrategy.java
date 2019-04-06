package disk.structure;

import FileSystem.ExternalFile;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract public class AllocationStrategy {
    protected static final int EMPTY_BLOCK = 0;
    protected static final int CONTIGUOUS_BLOCK = 1;
    protected static final int INDEXED_BLOCK = 2;
    protected static final int LINKED_BLOCK = 3;

    protected static Map<Integer , ArrayList<Integer>> blockes = new HashMap<>();


    abstract public int allocate(int[] diskArray, int siz);

    abstract public boolean deallocate(int[] diskArray, int fistBlock);

    /**
     * @return JSON String represents the root directory
     */
    public String toJson()
    {
        JSONSerializer serializer = new JSONSerializer();
        String json = serializer.deepSerialize(this.blockes);
        return json;
    }

    /**
     * @param json the JSON string to create the root directory from
     */
    public void fromJson(String json) {

        this.blockes = new JSONDeserializer<Map<Integer , ArrayList<Integer>>>().deserialize( json );
    }

    /**
     * @param path the path to save the root(JSON) in
     * @return true if saved correctly and false if not
     */
    public Boolean save(String path)
    {
        String json = this.toJson();
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
    public Boolean load(String path)
    {
        try {
            String json = ExternalFile.readFromFile(path);
            this.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
