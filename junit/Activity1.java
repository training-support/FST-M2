package activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList<String> list;

    @BeforeAll
    public static void setUp()
    {
        // Initialize a new ArrayList
        list = new ArrayList<String>();

        // Add values to the list
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest()
    {
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");
        list.add("user");
        // Assert the updated size
        assertEquals(3, list.size(), "Wrong size");
        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("user", list.get(2), "Wrong element");
    }
    @Test
    public  void replaceTest()
    {
        //update the string at 1st place of list
        list.set(1,"newBeta");
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");
        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("newBeta", list.get(1), "Wrong element");
    }
}




