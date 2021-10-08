package com.siva;

public class NtfsSet {
    public NtfsSet() {

    }

    static {
        try {
            System.load("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\bin\\libfile2.dll");
        } catch (Throwable t) {
            throw t;
        }
       
    }
    
    public native boolean setPermission(String file, int permissionval);

    public static void main(String[] args) {
        NtfsSet obj = new NtfsSet();
        obj.setPermission("C:\\Users\\Vicky\\Desktop\\sample.txt", 2);
    }
}
