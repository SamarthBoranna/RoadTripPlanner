// --== CS400 Spring 2023 File Header Information ==--
// Name: Samarth Boranna
// Email: boranna
// Team: AD
// TA: Rachit Tibdewal
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tester class to check the functionality of the DotDataReaderDW method to parse data from valid
 * dot data files
 * 
 * @author samarth
 */
public class DataWranglerTests {

  /**   
   * Tests the readDataFromFile(filename) method with an invalid parameter (null). Checks that
   * method throws a FileNotFoundException.
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testInvalidParameter() throws FileNotFoundException {
    DotDataReaderDW dataReader = new DotDataReaderDW();

    // Checks that a null parameter throws a FileNotFoundExcepiton
    try {
      dataReader.readDataFromFile(null);
      assertTrue(false);
    } catch (FileNotFoundException e) {
      assertTrue(true);
    }
  }

  /**
   * Tests the readDataFromFile(filename) method with an valid, but non-existant filename. Checks
   * that method throws a FileNotFoundException.
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testImproperFileName() throws FileNotFoundException {
    DotDataReaderDW dataReader = new DotDataReaderDW();

    // Checks that invalid filenames throws FileNotFoundException
    try {
      dataReader.readDataFromFile("");
      assertTrue(false);
      dataReader.readDataFromFile("filename.dot");
      assertTrue(false);
    } catch (FileNotFoundException e) {
      assertTrue(true);
    }
  }

  /**
   * Tests the readDataFromFile(filename) method with an valid filename. Checks that method iterates
   * through all lines of the file and that the returned list contains data.
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testValidFile() throws FileNotFoundException {
    DotDataReaderDW dataReader = new DotDataReaderDW();

    // Checks that valid filename with data parses the data into the returned List
    List<String[]> results = null;
    try {
      results = dataReader.readDataFromFile("WisconsinCities.dot");
    } catch (FileNotFoundException e) {
      assertTrue(false);
    }
    assertTrue(results.size() != 0);
    assertTrue(results.size() == 15); // Checks that all 15 lines are properly iterated through
  }

  /**
   * Tests the readDataFromFile(filename) with a valid filename parses the data into Strings, and
   * that the first parsed data matches the expected
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testContentFormat() throws FileNotFoundException {
    DotDataReaderDW dataReader = new DotDataReaderDW();
    List<String[]> results = dataReader.readDataFromFile("WisconsinCities.dot");

    // Checks that all values are Strings
    assertTrue(results.get(0)[0] instanceof String);
    assertTrue(results.get(0)[1] instanceof String);
    assertTrue(results.get(0)[2] instanceof String);

    // Checks that the first values stored in the returned first array is correct
    assertTrue(results.get(0)[0].equals("Madison"));
    assertTrue(results.get(0)[1].equals("Eau Claire"));
    assertTrue(results.get(0)[2].equals("178.1"));
  }

  /**
   * Tests the readDataFromFile(filename) method with an available dataset. Checks that the method
   * properly stores all data into the list and that all the data matches the expected values.
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testAllContents() throws FileNotFoundException {
    DotDataReaderDW dataReader = new DotDataReaderDW();

    List<String[]> results = dataReader.readDataFromFile("WisconsinCities.dot");
    List<String[]> expected = new ArrayList<String[]>();
    expected.add(new String[] {"Madison", "Eau Claire", "178.1"});
    expected.add(new String[] {"Madison", "La Crosse", "142.7"});
    expected.add(new String[] {"Madison", "Milwaukee", "79.6"});
    expected.add(new String[] {"Madison", "Appleton", "112.9"});
    expected.add(new String[] {"Madison", "Green Bay", "144.7"});
    expected.add(new String[] {"Eau Claire", "La Crosse", "85.5"});
    expected.add(new String[] {"Eau Claire", "Milwaukee", "245.9"});
    expected.add(new String[] {"Eau Claire", "Appleton", "182.6"});
    expected.add(new String[] {"Eau Claire", "Green Bay", "191.9"});
    expected.add(new String[] {"La Crosse", "Milwaukee", "209.8"});
    expected.add(new String[] {"La Crosse", "Appleton", "169.6"});
    expected.add(new String[] {"La Crosse", "Green Bay", "200.7"});
    expected.add(new String[] {"Milwaukee", "Appleton", "106.9"});
    expected.add(new String[] {"Milwaukee", "Green Bay", "116.7"});
    expected.add(new String[] {"Appleton", "Green Bay", "31.7"});

    // Checks that contents of the resulting List of Arrays are as expected
    for (int i = 0; i < expected.size(); i++) {
      assertTrue(expected.get(i)[0].equals(results.get(i)[0]));
      assertTrue(expected.get(i)[1].equals(results.get(i)[1]));
      assertTrue(expected.get(i)[2].equals(results.get(i)[2]));
    }
  }

  /**
   * Tests DataWrangler integration with BD and AE through functions that involve storing and
   * retreiving nodes and edges from the graph
   */
  @Test
  public void testIntegrationBD() {
    RTBackendInterface backend = new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW());

    // Check that no error is thrown regarding loading the files from a valid filename
    try {
      backend.loadData("WisconsinCities.dot");
      assertTrue(true);
    } catch (FileNotFoundException e) {
      assertTrue(false);
    }

    // Check that cities are properly loaded into the Graph and can be found in the Graph
    assertTrue(backend.isCityValid("Madison"));
    assertTrue(backend.isCityValid("La Crosse"));
    assertTrue(backend.isCityValid("Green Bay"));
    assertTrue(backend.isCityValid("Eau Claire"));
    assertTrue(backend.isCityValid("Appleton"));
    assertTrue(backend.isCityValid("Milwaukee"));

    // Check that BD shortestPath() method properly gets the AE shortestPathData()
    List<String> expected = new ArrayList<String>();
    expected.add("Madison");
    expected.add("Appleton");
    expected.add("Green Bay");

    assertEquals(backend.shortestPath("Madison", "Green Bay"), expected);
  }

  /**
   * Tests DataWrangler integration with FD, BD, and AE through calling FD functions that act on the
   * data stored in the graph
   */
  @Test
  public void testIntegrationFD() {

    // Tests that FD can load data, and find the shortest path between two cities using the BD
    // function called above
    TextUITester testOnePath =
        new TextUITester("D\nWisconsinCities.dot\nP\nMadison\nGreen Bay\nQ\n");
    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();

    // Checks the shortest path between these 2 cities is accurate
    assertTrue(testOnePath.checkOutput().contains("Madison -> Appleton -> Green Bay"));


    // Tests that FD can load data, and find the shortest path between multiple locations through
    // calling BD functions
    TextUITester testMultiplePaths = new TextUITester(
        "D\nWisconsinCities.dot\nM\n4\nMadison\nLa Crosse\nEau Claire\nGreen Bay\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();

    // Checks the shortest path between these 4 cities is accurate through calling the proper BD
    // functions
    assertTrue(testMultiplePaths.checkOutput()
        .contains("Madison -> La Crosse -> Eau Claire -> Green Bay"));
  }

  /**
   * Tests the FD methods to find the shortest path between 2, multiple, and all cities including
   * the edge lenght(mileage)
   */
  @Test
  public void CodeReviewOfFrontendDeveloper1() {
    // Tests that FD can properly find the shortest path between 2 cities and returns their edge
    // length
    TextUITester testOnePath =
        new TextUITester("D\nWisconsinCities.dot\nP\nLa Crosse\nEau Claire\nQ\n");
    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testOnePath.checkOutput()
        .contains("La Crosse -> Eau Claire\nThe distance of this trip is 85.5 miles"));


    // Tests that FD can properly find the shortest path between the same city and returns 0.0 as
    // the edge length
    TextUITester testSameCityPath =
        new TextUITester("D\nWisconsinCities.dot\nP\nGreen Bay\nGreen Bay\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testSameCityPath.checkOutput()
        .contains("Green Bay\nThe distance of this trip is 0.0 miles"));


    // Tests that FD can find the shortest path between multiple cities and correctly adds up their
    // edge length
    TextUITester testMultipleCitiesPath =
        new TextUITester("D\nWisconsinCities.dot\nM\n3\nGreen Bay\nEau Claire\nAppleton\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testMultipleCitiesPath.checkOutput()
        .contains("Green Bay -> Eau Claire -> Appleton\nThe distance of this trip is 374.5 miles"));

    // Tests that FD can find the shortes path between all the cities and correctly adds up their
    // edge length
    TextUITester testAllCitiesPath = new TextUITester("D\nWisconsinCities.dot\nA\nMadison\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testAllCitiesPath.checkOutput().contains(
        "Madison -> Milwaukee -> Appleton -> Green Bay -> Eau Claire -> La Crosse\nThe distance of this trip is 495.6 miles"));
  }

  /**
   * Tests the FD methods regarding gas mileage for paths between cities, as long as proper handling
   * of improper input
   */
  @Test
  public void CodeReviewOfFrontendDeveloper2() {
    // Tests that FD can correctly calculate the cost of a trip between two cities
    TextUITester testCostOnePath =
        new TextUITester("D\nWisconsinCities.dot\nG\n0.58\nP\nLa Crosse\nEau Claire\nQ\n");
    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testCostOnePath.checkOutput().contains("$49.59"));

    // Tests that FD can correctly calculate the cost of a trip between all cities
    TextUITester testCostAllCitiesPath =
        new TextUITester("D\nWisconsinCities.dot\nG\n0.40\nA\nMadison\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testCostAllCitiesPath.checkOutput().contains("$198.24"));

    // Tests that FD displays error message with improper input for car mileage
    TextUITester testImproperInput = new TextUITester("D\nWisconsinCities.dot\nG\nabc\n0.4\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testImproperInput.checkOutput().contains("Please enter a valid gas mileage"));

    // Tests that FD displays error message with improper input for cities
    TextUITester testImproperCityInput =
        new TextUITester("D\nWisconsinCities.dot\nP\nabc\nMadison\nMadison\nQ\n");
    frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    frontend.runCommandLoop();
    assertTrue(testImproperCityInput.checkOutput().contains("Please enter a valid city"));

  }

}
