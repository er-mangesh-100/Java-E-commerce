<%@page import="com.mycompany.mycart.helper.Helper"%>
<%@page import="com.learn.myCompany.entities.Category"%>
<%@page import="com.learn.myCompany.dao.CategoryDao"%>
<%@page import="com.learn.myCompany.entities.Product"%>
<%@page import="java.util.List"%>  
<%@page import="com.learn.myCompany.dao.ProductDao"%>
<%@page import="com.mycompany.mycart.helper.FactoryProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MyCart-Home</title>
        <%@include file="components/common_css_js.jsp"%>
        <style>
             <%@include file="css/style.css" %>
             <%@include file="js/script.js"%>
        </style>
     
    </head>
    <body>
        <%@include file = "components/navbar.jsp"%>
        <div class="container-fluid">
        <div class="row mt-3 mx-2">
<!--            fetching all the products-->
            <%
                
                
               String cat =   request.getParameter("categorys");
              //  out.println(cat);
                
               ProductDao dao =  new ProductDao(FactoryProvider.getFactory());
                 
                  List<Product> list= null;
                 if( cat==null || cat.trim().equals("all"))
                 {
                
                        list = dao.getAllProducts();
            
                 }
                 else
                 {
                      
                    int cid = Integer.parseInt(cat.trim());
                    list = dao.getAllProductsById(cid); //will give all the product of given id !
            
                 }
                               
               CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
               List<Category> clist =  cdao.getCategories();
               
              %>
            
                                                                         
            
<!--            show categories-->
     <div class="col-md-2"> <!-- 12 grid me se 2 yha -->
                
<!--                <h1><%=clist.size()%></h1>-->
                
                
                <div class="list-group mt-4">
                    
                    <a href="index.jsp?categorys=all" class="list-group-item list-group-item-action active">
                        All Products
                    </a>
                  
                                  
                
                <%
                for(Category c:clist)
                {
                               
                %>
                
                 
                     <a href="index.jsp?categorys=<%=c.getCategoryId()%>" class="list-group-item list-group-item-action "><%=c.getCategoryTitle()%></a>
                  
                  
                  
                  
                  <%
                }
                       
                %>
                </div>
            </div>
            
                
                               
                
<!--            //show products-->
            <div class="col-md-10"> <!<!-- 12 grid me se 10 yha -->
               
                  
                <div class="row mt-4">
                    
                    <div class="col-md-12">
                        
                        <div class="card-columns">
                            
                            <!<!-- traversing products -->   
                            
                            <%
                            
                            for(Product p:list)
                            {
                             
                                                                                                       
                            %>
                            
                            
                            <div class="card product-card">
                                
                                <div class="container text-center">

                                    <img src="img/products/<%=p.getpPhoto()%>" style="max-height: 200px; max-width: 100%; width: auto;"   class="card-img-top m-2" alt="...">
                                </div>

                                <div class="card-body">
                                    
                                    <h5 class="card-title"><%=p.getpName() %></h5>
                                    <p class="card-text">
                                        
                                        <%= Helper.get10Words(p.getpDesc()) %>
                                                                              
                                    </p>
                                </div>   
                                
                                
                                        <div class="card-footer text-center">
                                            
                                            <button class="btn custom-bg text-white" onclick="add_to_cart(<%=p.getpId()%>,'<%=p.getpName() %>', <%=p.getPriceAfterDiscount() %>)">Add to Cart</button>
                                            <button class="btn btn-outline-success"> &#8377; <%= p.getPriceAfterDiscount() %>/- <span class="text-secondary discount-label"> &#8377; <%=p.getpPrice()%> , <%=p.getpDiscount() %>% off</span> </button>
                                                    
                                        </div>     
                                                                                                                      
                                        
                            </div>
                            
                                                       
                            
                            <%}
                            
                            if(list.size()==0)
                            {

                                out.println("<h3>No item in this category</h3>");
                            }
                            
                            
                            %>
                            
                            
                            
                        </div>
                    </div>
                    
                    
                </div>
                
                                
            </div>
        </div>
                            
        </div>        
                            
               <%@include file="components/common_modal.jsp" %>             
    </body>
</html>





