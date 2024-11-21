package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populator;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        // Start server (routes tilføjes automatisk i ApplicationConfig)
        Javalin dat = ApplicationConfig.startServer(7070);

        // Setup database
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(false);
        
        // Kør populator
        new Populator().main(new String[]{});

        // TODO: Change DB name in config.properties file
        // TODO: Create a DB before the program can run
    }
}