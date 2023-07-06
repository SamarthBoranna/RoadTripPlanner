// --== CS400 File Header Information ==--
// Name: Tarun Chichili
// Email: chichili@wisc.edu
// Team: AD
// TA: Rachit Tibrewal
// Lecturer: Gary Dahl
// Notes to Grader:

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests {

    protected RTGraphBD<String, Double> map = null;
    protected DotDataReaderBD reader = null;

    @BeforeEach
    public void createInstance() {
    }

    /**
     * Junit test to check if loading will work once load method is correct
     */
    @Test
    public void test1() {
        map = new RTGraphBD<>();
        reader = new DotDataReaderBD();
        RTBackendBD backend = new RTBackendBD(map,reader);
        try{
            backend.loadData("test");
        }catch (FileNotFoundException e){
            assertTrue(false);
        }
        assertTrue(true);
    }

    /**
     * Junit test to check if shortestPath between two cities will work through backend
     */
    @Test
    public void test2() {
        map = new RTGraphBD<>();
        reader = new DotDataReaderBD();
        RTBackendBD backend = new RTBackendBD(map,reader);

        List path = backend.shortestPath("city1", "city2");

        assertEquals(path.get(0), "Madison");

    }

    /**
     * Junit test to check if shortestPathAllcities  will work through backend
     */
    @Test
    public void test3() {
        map = new RTGraphBD<>();
        reader = new DotDataReaderBD();
        RTBackendBD backend = new RTBackendBD(map,reader);

        List path = backend.shortestPathAllCities("test");
        assertEquals(path.get(0), "Madison");
    }

    /**
     * Junit test to check if distanceFromLinkedList  will work through backend
     */
    @Test
    public void test4() {
        map = new RTGraphBD<>();
        reader = new DotDataReaderBD();
        RTBackendBD backend = new RTBackendBD(map,reader);

        List cities = new ArrayList();

        cities.add("city1");
        cities.add("city2");
        cities.add("city3");

        double distance = backend.distanceFromLinkedList(cities);
        assertEquals(distance, 10.0);
    }

    /**
     * Junit test to check if isCityValid will work through backend
     */
    @Test
    public void test5() {
        map = new RTGraphBD<>();
        reader = new DotDataReaderBD();
        RTBackendBD backend = new RTBackendBD(map,reader);

        boolean check = backend.isCityValid("invalid city");
        assertFalse(check);
    }


}
