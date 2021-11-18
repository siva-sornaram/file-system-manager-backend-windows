package com.siva;

public class CreateDirectory {
    public CreateDirectory() {

    }

    static {
        try {
            System.load("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\bin\\libfile3.dll");
        } catch (Throwable t) {
            throw t;
        }
       
    }

    private native boolean createDirectory(String dirPath, String dirName);

    public boolean createDir(String dirPath, String dirName) {
        return createDirectory(dirPath, dirName);
    }
    
    public static void main(String[] args) {
        CreateDirectory isCreated = new CreateDirectory();
        isCreated.createDirectory("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\Users", "vicky");
    }
}
