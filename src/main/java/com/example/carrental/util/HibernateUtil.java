package com.example.carrental.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private SessionFactory sessionFactory;

    public HibernateUtil() {

        createSessionFactory();
    }

    private void createSessionFactory() {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public void shutdown() {

        getSessionFactory().close();
    }
}
