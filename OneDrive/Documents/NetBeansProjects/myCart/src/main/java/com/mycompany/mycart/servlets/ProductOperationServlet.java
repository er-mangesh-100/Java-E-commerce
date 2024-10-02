package com.mycompany.mycart.servlets;

import com.learn.myCompany.entities.Category;
import com.learn.myCompany.dao.CategoryDao;
import com.learn.myCompany.dao.ProductDao;
import com.learn.myCompany.entities.Product;
import com.mycompany.mycart.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.lang.String;


@MultipartConfig 
public class ProductOperationServlet extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
                        
//            server:2
//            add category
//            add product
            String op = request.getParameter("operation");
            if(op.trim().equals("addcategory"))
            {
                //add category
                //fetching category data
            String title = request.getParameter("catTitle");
            String description = request.getParameter("catDescription");
            
                Category category = new Category();
                category.setCategoryTitle(title);
                category.setCategoryDescription(description);
                
                //category ko database me save karnan hai
                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
                
             int catId =  categoryDao.saveCategory(category);
                // out.println("Category Saved");
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Category added successfully :" +catId);
                response.sendRedirect("admin.jsp");
                return;
                               
            }
            else if(op.trim().equals("addproduct")) 
            {
                 // Log the parameters to debug
    System.out.println("pName: " + request.getParameter("pName"));
    System.out.println("pDesc: " + request.getParameter("pDesc"));
    System.out.println("pPriceStr: " + request.getParameter("pPrice"));
    System.out.println("pDiscountStr: " + request.getParameter("pDiscount"));
    System.out.println("pQuantityStr: " + request.getParameter("pQuantity"));
    System.out.println("catIdStr: " + request.getParameter("catId"));

    //add product
    String pName = request.getParameter("pName");
    String pDesc = request.getParameter("pDesc");
    String pPriceStr = request.getParameter("pPrice");
    String pDiscountStr = request.getParameter("pDiscount");
    String pQuantityStr = request.getParameter("pQuantity");
    String catIdStr = request.getParameter("catId");
    
    if (pPriceStr != null && pDiscountStr != null && pQuantityStr != null && catIdStr != null) {
        try {
            int pPrice = Integer.parseInt(pPriceStr);
            int pDiscount = Integer.parseInt(pDiscountStr);
            int pQuantity = Integer.parseInt(pQuantityStr);
            int catId = Integer.parseInt(catIdStr);
            Part part = request.getPart("pPic");
            
            Product p = new Product();
            p.setpName(pName);
            p.setpDesc(pDesc);
            p.setpPrice(pPrice);
            p.setpDiscount(pDiscount);
            p.setpQuantity(pQuantity);
            p.setpPhoto(part.getSubmittedFileName());
            
            // Get category by id
            CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
            Category category = cdao.getCategoryById(catId);
            p.setCategory(category);
            
            // Save the product
            ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
             pdao.saveProduct(p);
            
            //pic upload
            
            //find out the path to upload the photo
          // Get the context path of the application
            String path = getServletContext().getRealPath("img")+File.separator+"products"+File.separator+part.getSubmittedFileName();
            System.out.println(path);
              
            //uploading code..
            
            try {
                 FileOutputStream fos = new FileOutputStream(path); //path pe data write
                 InputStream is = part.getInputStream(); //data read
             
            //reading data
            byte []data = new byte[is.available()];//utni size ki array ban jayegi jitni size ki humari photo hogi!
            is.read(data); //means photo humara is se read hoke data array me write ho jayegi means main photo data array me rakhi hui hai,ab is data ko is fos me write karenge see,
              
            //writing the data
            fos.write(data);
            fos.close();
            is.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
                      
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("message", "Product is added successfully.");
            response.sendRedirect("admin.jsp");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integers: " + e.getMessage());
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("message", "Failed to add product: Invalid number format.");
            response.sendRedirect("admin.jsp");
            return;
        } catch (ServletException | IOException e) {
            System.out.println("Error handling file upload: " + e.getMessage());
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("message", "Failed to add product: File upload error.");
            response.sendRedirect("admin.jsp");
            return;
        }
    } else {
        System.out.println("One or more parameters are missing or null.");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("message", "Failed to add product: Missing or incorrect fields.");
        response.sendRedirect("admin.jsp");
        return;
    }
           
                            
            }  
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


