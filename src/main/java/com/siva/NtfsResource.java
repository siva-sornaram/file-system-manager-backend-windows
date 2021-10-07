package com.siva;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Vicky
 */

@Path("ntfs")
public class NtfsResource {
    private final Ntfs obj = new Ntfs();

    private static int NONE = 0;
    private static int WRITE = 1;
    private static int READ = 2;
    private static int EXECUTE = 4;
    private static int FULLCONTROL = 8;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFiles() {
        ArrayList<Files> fileArr = new ArrayList<>();
        obj.get_all_files_names_within_folder("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\logs");
        
        try {
            File data = new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\outfile.dat");
            Scanner reader = new Scanner(data);
            while (reader.hasNextLine()) {
                int permissionval = NONE;
                String fileData = reader.nextLine();
                String[] fileStr = fileData.split(",");
                String[] fileper = new String[5];
                if(fileStr.length > 2) {
                    fileper = fileStr[2].split(":");
                    if (fileper.length > 0) {
                        for (String str: fileper) {
                            if(str.equals("Full Control"))   permissionval |= FULLCONTROL;
                            else if(str.equals("Read"))  permissionval |= READ;
                            else if(str.equals("Write")) permissionval |= WRITE;
                            else if(str.equals("Execute"))   permissionval |= EXECUTE;
                            else    permissionval |= NONE;
                        }
                    }
                }
                fileArr.add(new Files(Integer.parseInt(fileStr[0]), fileStr[1], fileper, permissionval));
//                System.out.println(fileData);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            ex.printStackTrace();
        }
        
        return new Gson().toJson(fileArr);
    }
}
