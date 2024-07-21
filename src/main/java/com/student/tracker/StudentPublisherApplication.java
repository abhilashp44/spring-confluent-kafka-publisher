package com.student.tracker;

import com.student.tracker.service.StudentPublisherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main application class for the Student Publisher Application. This class is responsible for bootstrapping the
 * Spring Boot application and initializing the StudentPublisherService to publish Student events.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.student.tracker"})
public class StudentPublisherApplication {

    /**
     * The entry point of the Student Publisher Application. It initializes the Spring Application Context, retrieves
     * the StudentPublisherService bean, and triggers the asynchronous publishing of Student events.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(StudentPublisherApplication.class, args);

        StudentPublisherService publisherService = context.getBean(StudentPublisherService.class);

        publisherService.publishStudentEvent();
        context.close();
    }
}