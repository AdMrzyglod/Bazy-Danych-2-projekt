package com.example.project.Providers;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Provider {
    private static final EntityManagerFactory emf;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            emf= Persistence.
                    createEntityManagerFactory("myDatabaseConfig");

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManager getManager(){
        return emf.createEntityManager();
    }
    public static void main(final String[] args) throws Exception {
        final EntityManager manager = getManager();
        try {
            EntityTransaction etx = manager.getTransaction();
            etx.begin();


            etx.commit();
        } finally {
            manager.close();
        }
    }
}
