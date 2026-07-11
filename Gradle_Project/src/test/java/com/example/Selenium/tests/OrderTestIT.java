package com.example.Selenium.tests;

import com.example.Selenium.data.OrderFactory;
import com.example.Selenium.data.Orderbuilder;
import com.example.Selenium.repository.OrderRepository;
import io.qameta.allure.Allure;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Testcontainers(disabledWithoutDocker = true)
 class OrderTestIT {


    private static final Logger log =
            LoggerFactory.getLogger(OrderTestIT.class);

    // Provision an isolated MySQL container to eliminate shared database
// dependencies and ensure reliable integration test execution.

     @Container
     static MySQLContainer mySQL = new MySQLContainer(DockerImageName.parse("mysql:8.0"))
             .withDatabaseName("retail_test")
             .withUsername("root")
             .withPassword("Cipi@0971");
     static OrderRepository repository;
     static OrderFactory factory;

    // Apply Flyway migrations and seed reference data required
// for application and test initialization.
     @BeforeAll
     static void migrateSchema(){
         Allure.story("Order Test for Isolated MySQL Container");
         log.info("Started the Migration");
         Allure.step("Started the Migration");
         Flyway.configure()
                 .dataSource(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword())
                 .locations("classpath:db/migration")
                 .load()
                 .migrate();

         repository = new OrderRepository(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword());
         factory = new OrderFactory(repository);
     }

    // Ensure each test starts from a clean state to prevent data leakage
// and guarantee repeatable test execution.
     @BeforeEach
     void reset(){
         log.info("Reset the Tables");
         Allure.step("Reset the Tables");
         repository.resetMutableTables();
     }


   //Verify that Seeding Reference data so that flyway
    //ensures migration and empty test orders because of no test data.
     @Test
     void flywaySeedingReferenceDataButNoPerTestOrders(){
         log.info("Asserting the table count after the migration");
         Allure.step("Asserting the table count after the migration");
         assertEquals(4,repository.referenceStatusCount());
         assertEquals(0,repository.count());
     }

    // Verify that orders created through the test-data factory
// are successfully persisted into the isolated MySQL container.
     @Test
    void persistedBuilderDataAgainstIsolatedMysql(){

         long id = factory.persisted(Orderbuilder.newOrder().withQuantity(3));
         log.info("Verifying that the persisted  data created through isolated Mysql in id",id);
         Allure.step("Verifying that the persisted  data created through isolated Mysql in id"+id);
         assertTrue(id>0);
         assertEquals(1,repository.count());
     }

    // Verify that repository counts only the orders created during
// the current test execution and ignores reference seed data.
    @Test
    void countsOnlyPersistedTestOrders(){
         log.info("Verifying the repository counts the current test data");
        Allure.step("Verifying the repository counts the current test data");
         factory.persisted(Orderbuilder.newOrder());
         factory.persisted(Orderbuilder.newOrder().withName("SKU-2").withQuantity(2));

         assertEquals(2,repository.count());
    }

    // Verify test independence by confirming that data from previous
// tests is removed before execution and does not affect results.
    @Test
    void resetMakesTestOrderIndependent(){
         log.info("Verify that the reset makes the Test order Independent");
        Allure.step("Verify that the reset makes the Test order Independent");
         assertEquals(0,repository.count());
         factory.persisted(Orderbuilder.newOrder().withRefunded());

         assertEquals(1,repository.count());
         assertEquals(1,repository.countByStatus("REFUNDED"));
    }



}
