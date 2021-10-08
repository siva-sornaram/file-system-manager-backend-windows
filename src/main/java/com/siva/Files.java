package com.siva;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vicky
 */
@XmlRootElement
public class Files {
    private int id;
    private String fname;
    private String[] permissions;
    private int permissionval;
    private String path;
    public Files() {

    }
    public int getId() {
        return id;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getPermissionval() {
        return permissionval;
    }
    public void setPermissionval(int permissionval) {
        this.permissionval = permissionval;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String[] getPermissions() {
        return permissions;
    }
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
    public Files(int id, String fname, String[] permissions, int permissionval, String path) {
        this.id = id;
        this.fname = fname;
        this.permissions = permissions;
        this.permissionval = permissionval;
        this.path = path;
    }
    public String toString() {
        return "[ id : " + this.id + ", fname : " + this.fname + ", permissionval : " + this.permissionval + ", path : " + this.path + " ]";
    }
}
