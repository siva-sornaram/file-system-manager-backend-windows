package com.siva;


/**
 *
 * @author Vicky
 */
public class Files {
    private int id;
    private String fname;
    private String[] permissions;
    private int permissionval;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public int getPermissionval() {
        return permissionval;
    }

    public void setPermissionval(int permissionval) {
        this.permissionval = permissionval;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the permissions
     */
    public String[] getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
    
    public Files(int id, String fname, String[] permissions, int permissionval) {
        this.id = id;
        this.fname = fname;
        this.permissions = permissions;
        this.permissionval = permissionval;
    }
    
}
