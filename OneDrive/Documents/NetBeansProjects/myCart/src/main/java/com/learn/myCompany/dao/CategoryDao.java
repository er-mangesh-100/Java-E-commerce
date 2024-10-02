package com.learn.myCompany.dao;                              
import com.learn.myCompany.entities.Category;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class CategoryDao {
    
    private SessionFactory factory;

    public CategoryDao(SessionFactory factory) {
        this.factory = factory;
    }
    //saves category to DB
    public int saveCategory(Category cat) //jo bhi id aayegi return ho jayega!
    {
        Session session = this.factory.openSession();
        Transaction tx = session.beginTransaction();
       int catId = (int) session.save(cat);       
        tx.commit();
        session.close();
        return catId;
    }
    
    public List<Category> getCategories()
    {
        Session s = this.factory.openSession();
        Query query = s.createQuery("from Category");
        List <Category> list= query.list();
        return list;
            
    }
    
    public Category getCategoryById(int cid) //returning single category
    {
      
        Category cat = null;//local variable bana doya!
        try {
            
            Session session = this.factory.openSession();
            cat = session.get(Category.class, cid); //it will return the product ka category!
            session.close();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
      return cat;
    }
}
















