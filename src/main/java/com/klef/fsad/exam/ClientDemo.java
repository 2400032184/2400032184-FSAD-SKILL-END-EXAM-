package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;

public class ClientDemo {
    
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        
        try {
            // I. Insert a new record
            System.out.println("=== Inserting new record ===");
            CustomerAccount account = new CustomerAccount(
                "John Doe",
                "Premium Customer Account",
                LocalDate.now(),
                "Active"
            );
            
            session.beginTransaction();
            int id = (int) session.save(account);
            session.getTransaction().commit();
            System.out.println("Record inserted with ID: " + id);
            
            // II. Update fields based on ID
            System.out.println("\n=== Updating record ===");
            session.beginTransaction();
            CustomerAccount existingAccount = session.get(CustomerAccount.class, id);
            if (existingAccount != null) {
                existingAccount.setName("Jane Doe");
                existingAccount.setStatus("Inactive");
                session.update(existingAccount);
                session.getTransaction().commit();
                System.out.println("Record updated: " + existingAccount);
            }
            
        } finally {
            session.close();
            factory.close();
        }
    }
}
