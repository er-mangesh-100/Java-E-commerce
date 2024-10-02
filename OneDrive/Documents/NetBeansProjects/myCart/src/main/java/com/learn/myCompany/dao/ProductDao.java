package com.learn.myCompany.dao;

import org.hibernate.SessionFactory;
import com.learn.myCompany.entities.Product;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
public class ProductDao {
    private SessionFactory factory;

    public ProductDao(SessionFactory factory) {
        this.factory = factory;
    }
    
    public boolean saveProduct (Product product)
    {
        boolean f = false; //default false!
         Transaction tx = null;
        try {
            
            Session session = this.factory.openSession();
            tx = session.beginTransaction();
            
            
            session.save(product); //product ka object diya!
            tx.commit();
            session.close();
             f=true;     
            
        } catch (Exception e) {
            if (tx != null) {
            tx.rollback(); // Rollback the transaction in case of an exception
        }
            e.printStackTrace();
            f=false;
        }
    
      return f;
    }     
    
    
    
    //get all products
    public List<Product> getAllProducts()
    {
        Session s = this.factory.openSession();
        Query query = s.createQuery("from Product");
        List<Product> list = query.list();
        return list;
    
    
    }
    
    //get all products by categoryId
    public List<Product> getAllProductsById(int cid)
    {
        Session s = this.factory.openSession();
        Query query = s.createQuery("from Product as p where p.category.categoryId =: id");
        query.setParameter("id", cid);
        List<Product> list = query.list();
        return list;
    
    
    }
    
}











