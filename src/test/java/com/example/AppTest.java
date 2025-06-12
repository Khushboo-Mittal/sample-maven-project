package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testAddPositiveNumbers() {
        assertEquals(5, App.add(2, 3));
    }

    @Test
    public void testAddNegativeNumbers() {
        assertEquals(-5, App.add(-2, -3));
    }

    @Test
    public void testAddZero() {
        assertEquals(0, App.add(0, 0));
    }

    @Test
    public void testAddPositiveAndNegative() {
        assertEquals(1, App.add(3, -2));
    }

    @Test
    public void testAddFailureExample() {
        // This test is intentionally incorrect to simulate a failure
        assertEquals(10, App.add(2, 2));
    }
}
