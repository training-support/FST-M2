package test;
import java.util.ArrayList;
	
import org.junit.jupiter.api.BeforeEach;
	
import org.junit.jupiter.api.Test;
	
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

	static ArrayList<String> list;
	
	@BeforeEach
	public void setup()
	{
		// Initialize a new ArrayList
		list = new ArrayList<String>();

		// Add values to the list
		list.add("alpha"); // at index 0
		list.add("beta"); // at index 1
	}
	
	@Test
	public void insertTest()
	{
		assertEquals(2,list.size(),"Wrong Size");
		
		list.add("gamma");
	
		assertEquals(3,list.size(),"Wrong Size");
		
        assertEquals("alpha", list.get(0), "Wrong element");
    	
        assertEquals("beta", list.get(1), "Wrong element");
	
        assertEquals("gamma", list.get(2), "Wrong element");
	}
	
	@Test
	public void replaceTest()
	{
		assertEquals(2,list.size(),"Wrong Size");
		
		list.add("test");
		
		assertEquals(3,list.size(),"Wrong Size");
		
		list.set(1,"change");
		
		assertEquals(3,list.size(),"Wrong Size");
		
		assertEquals("alpha", list.get(0), "Wrong element");
    	
        assertEquals("change", list.get(1), "Wrong element");
	
        assertEquals("gamma", list.get(2), "Wrong element");
		
	}
	
	
}
