package com.siva;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.*;
// import javax.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class UserLogin extends HttpServlet {
    private String pass;
    private String uname;
    private User validated;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        uname = request.getParameter("uname");
		pass = request.getParameter("pass");

        System.out.println("uname : " + uname + "\npass : " + pass);

//      Changing the password to the encoded value so that it can be compared with the encrypted value in database
		pass = SecureUtil.encode(pass);

        System.out.println("uname : " + uname + "\npass : " + pass);
		
		
//		Verifying the username and password and initializing a session

		if (Logindao.validate(uname, pass)) {
            System.out.println("came if validated");
			validated = Logindao.getUser(uname, pass);
		} else {
            validated = new User("", false, 1);
		}

        //To Prevent cors missing allow origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        

        String validatedUserJsonString = new Gson().toJson(validated);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(validatedUserJsonString);
        out.flush();

    }
}
