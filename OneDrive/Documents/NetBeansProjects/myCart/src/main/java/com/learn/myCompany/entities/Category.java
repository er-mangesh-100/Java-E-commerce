
package com.learn.myCompany.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryTitle;
    private String CategoryDescription;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
    
    public Category(int categoryId, String categoryTitle, String CategoryDescription) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.CategoryDescription = CategoryDescription;
    }

    public Category(String categoryTitle, String CategoryDescription,List<Product> products) {
        this.categoryTitle = categoryTitle;
        this.CategoryDescription = CategoryDescription;
        this.products= products;
    }

    public Category() {
    }

    
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String CategoryDescription) {
        this.CategoryDescription = CategoryDescription;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
       
    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + ", CategoryDescription=" + CategoryDescription + '}';
    }
    
}





