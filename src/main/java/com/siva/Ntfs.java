package com.siva;

public class Ntfs {
    public Ntfs() {
        
    }

     static {
         try {
//             System.loadLibrary("libfile");
            System.load("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\bin\\libfile1.dll");
         } catch (Throwable t) {
             throw t;
         }
         
     }
    
    public native void get_all_files_names_within_folder(String folder);
    
    public static void main(String[] args) {
        Ntfs obj = new Ntfs();
        obj.get_all_files_names_within_folder("C:/Users/Vicky/Desktop");     
    }
}
