package com.example.Selenium.tests;

import com.example.Selenium.Record.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.Selenium.data.Orderbuilder.newOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderStructureTest {


    private static final Logger log =
            LoggerFactory.getLogger(OrderStructureTest.class);

    // Verify that the builder creates an order with the expected
// default values when no custom values are provided.
    @Test
    void builderBySensibleDefaults(){
        log.info("Builder starts with Default Values");
        Order order = newOrder().build();

        assertEquals("SKU-1",order.name());
        assertEquals(1,order.quantity());
        assertEquals(1299_00,order.price());
        assertEquals("NEW",order.status());
        assertEquals(LocalDate.now(),order.date_on());
        assertEquals(false,order.refunded());
    }
    // Verify that custom values supplied to the builder override
// the default order configuration.
    @Test
    void builderWithDynamicValues(){
        log.info("Builder starts with Dynamic Values");

        Order order = newOrder().withQuantity(3).build();

        assertEquals(3,order.quantity());
        assertEquals("SKU-1",order.name());
        assertEquals("NEW",order.status());
    }
}