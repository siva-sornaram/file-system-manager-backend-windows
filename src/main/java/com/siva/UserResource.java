package com.siva;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



@Path("users")
public class UserResource {
    
    UserRepository userRepo = new UserRepository();
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @POST
    @Path("createuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createUser(User user) {
        boolean isCreated = userRepo.createUser(user);
        System.out.println(isCreated);

        return isCreated;
    }

    @POST
    @Path("user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        return userRepo.getUser(id);
    }


    @PUT
    @Path("updateuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(User user) {

        if (userRepo.getUser(user.getUserId()) == null) {
            userRepo.createUser(user);
        }

        boolean isUpdated = userRepo.updateUser(user);
        System.out.println(isUpdated);

        return isUpdated;
    }

    @DELETE
    @Path("deleteuser/{id}")
    public boolean deleteUser(@PathParam("id") int id) {
        User user = userRepo.getUser(id);
        boolean isDeleted = false;

        if (id != 0) {
            isDeleted = userRepo.deleteUser(id);
        }

        return isDeleted;
    }

    // public static void main(String[] args) {
    //     UserRepository repo = new UserRepository();
    //     List<User> users = repo.getAllUsers();
    //     for (User user : users) {
    //         System.out.println(user.getUserId());
    //         System.out.println(user.getUserName());
    //         System.out.println(user.getRoleId());
    //     }
    // }

}
