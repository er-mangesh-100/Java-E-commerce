package com.mycompany.mycart.servlets;

import com.learn.myCompany.entities.User;
import com.mycompany.mycart.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {   
            try {
                
               String userName =  request.getParameter("user_name");
               String userEmail =  request.getParameter("user_email");
               String userPassword =  request.getParameter("user_password");
               String userPhone =  request.getParameter("user_phone");
               String userAddress =  request.getParameter("user_address");
               //String userType =  request.getParameter("user_type");
                              
              // validations
              if(userName.isEmpty())
              {
                  out.println("Name is blank");
                  return;
              }
              if(userEmail.isEmpty())
              {
                  out.println("Email is blank");
                  return;
              }
              if(userPassword.isEmpty())
              {
                  out.println("Password is blank");
                  return;
              }
              if(userPhone.isEmpty())
              {
                  out.println("Phone is blank");
                  return;
              }
              if(userAddress.isEmpty())
              {
                  out.println("Address is blank");
                  return;
              }
              
              //creating user object to store the data
              User user = new User(userName, userEmail, userPassword, userPhone, "default.jpg", userAddress,"normal");
              //hiberbate me user data ko save karenge using user object
                Session hibernateSession =  FactoryProvider.getFactory().openSession();
                
                Transaction tx = hibernateSession.beginTransaction();
                
                int userId = (int) hibernateSession.save(user);//ye ek object expect karta hai and ye user table me data ko feka ek aayega!
                
                tx.commit();
                hibernateSession.close();
                
                //using http session to store the message
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Registeration Successfull!! user id is: "+userId);
                
                response.sendRedirect("register.jsp");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
