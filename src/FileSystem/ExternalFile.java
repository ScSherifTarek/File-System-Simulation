package FileSystem;

import java.io.*;

public class ExternalFile {
    /**
     * @param path the path of the file to read from
     * @return the string within the file
     */
    public static String readFromFile(String path) throws IOException {
        String s="";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        s = reader.readLine();
        reader.close();
        return s;
    }

    /**
     * @param path the path of the file to write to
     * @param s the string to write in the file
     */
    public static void writeToFile(String path, String s) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(s);
        writer.close();
    }

}
