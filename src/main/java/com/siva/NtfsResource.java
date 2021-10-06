package com.siva;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vicky
 */

@Path("ntfs")
public class NtfsResource {
    private final Ntfs obj = new Ntfs();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFiles() {
        ArrayList<Files> fileArr = new ArrayList<>();
        obj.get_all_files_names_within_folder("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\logs");
        
        try {
            File data = new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\outfile.dat");
            Scanner reader = new Scanner(data);
            while (reader.hasNextLine()) {
                String fileData = reader.nextLine();
                String[] fileStr = fileData.split(",");
                fileArr.add(new Files(Integer.parseInt(fileStr[0]), fileStr[1], fileStr[2].split(":")));
//                System.out.println(fileData);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            ex.printStackTrace();
        }
        return new Gson().toJson(fileArr);
    }
}
