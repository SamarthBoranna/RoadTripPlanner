import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Scanner;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class FrontendDeveloperTests {

  /**
   * Tests if the program can accurately handle incorrect and correct inputs
   */
  @Test
  @Order(1)
  public void testValidInput() { // tests a valid input
    TextUITester test1 = new TextUITester("Q/n");

    try (Scanner scnr = new Scanner(System.in)) {

      RTFrontendInterface test1FD = new RTFrontendFD(scnr, new RTBackendFD(null, null));
      test1FD.runCommandLoop();

      String output = test1.checkOutput();

      assertTrue(output.contains("Thank you for accessing Road Trip Planner."));

    } catch (Exception e) {
    }

    // tests an invalid input
    TextUITester test2 = new TextUITester("Z\nQ/n");

    try (Scanner scnr = new Scanner(System.in)) {

      RTFrontendInterface test2FD = new RTFrontendFD(scnr, new RTBackendFD(null, null));
      test2FD.runCommandLoop();

      String output = test2.checkOutput();

      assertTrue(output.contains(
          "Invalid command. Please type one of the letters presented within []s to identify the "
              + "command you would like to choose."));

    } catch (Exception e) {
    }
  }

  
  /**
   * checks if the frontend class can throw an error when an invalid file is loaded in
   */
  @Test
  @Order(2)
  public void testLoadData() {
    // Tests an invalid case of Load function, checks thatan error is printed
    TextUITester testFalse = new TextUITester("D\nerror.txt\nQ\n");

    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendFD(new RTGraphFD(), new DotDataReaderFD()));
    frontend.runCommandLoop();

    String output = testFalse.checkOutput();

    assertTrue(output.contains("Error: Could not find or load file: error.txt"));
  }


  /**
   * checks if the frontend class can correctly ask the user how many cities they would like to 
   * input, ask what all cities they are looking for, and then correctly display the road trip, and 
   * the distance they would travel
   */
  @Test
  @Order(3)
  public void testShortestPathMultipleCities() {
    //System.out.println("testing multiple cities");
    // tests inputting 3 cities
    TextUITester multipleCities = new TextUITester("M\n3\nSuperior\nGreen Bay\nMadison\nQ\n");

    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendFD(new RTGraphFD(), new DotDataReaderFD()));
    frontend.runCommandLoop();

    // checks the output
    String output = multipleCities.checkOutput();
    //System.out.println(output);

    // checks if the output contains the expected strings
    assertTrue(output
        .contains("The shortest path between these cities is Superior -> Green Bay -> Madison"));
    assertTrue(output.contains("The distance of this trip is 307.9 miles"));
  }

  /**
   * checks if the frontend class can correctly ask the user what city they are starting from and 
   * which city they will end at, and then correctly display the road trip, and 
   * the distance they would travel
   */
  @Test
  @Order(4)
  public void testShortestPathTwoCities() {
    //System.out.println("testing two cities");
    // checks if we can input 2 cities and find the shortest path between them
    TextUITester testMileage1 = new TextUITester("P\nMilwaukee\nMadison\nQ\n");

    // creates frontend object
    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendFD(new RTGraphFD(), new DotDataReaderFD()));
    frontend.runCommandLoop();

    // creates output object
    String output = testMileage1.checkOutput();
    // System.out.println(output);

    // checks if output contains what is expected
    assertTrue(output.contains(
        "The shortest road trip from Milwaukee to Madison is Milwaukee -> Green Bay -> Madison"));
    assertTrue(output.contains("The distance of this trip is 492.5 miles"));
  }


  /**
   * checks if the frontend class can accurately display the shortest path of all cities over 
   * Wisconsin starting at a city the user will input
   */
  @Test
  @Order(5)
  public void testShortestPathAllCities() {
    // System.out.println("testing all cities");
    // checks if the method can create a path between all main cities in wisconsin
    TextUITester allCities = new TextUITester("A\nMilwaukee\nQ\n");

    // creates a frontend object
    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendFD(new RTGraphFD(), new DotDataReaderFD()));
    frontend.runCommandLoop();

    String output = allCities.checkOutput();
    // System.out.println(output);

    // checks if output contains expected strings
    assertTrue(output.contains(
        "The shortest path between all major cities in Wisconsin starting in Milwaukee is Milwaukee "
            + "-> Madison -> Green Bay -> Kenosha -> Appleton"));
    assertTrue(output.contains("The distance of this trip is 492.5 miles"));
  }

  /**
   * checks if the frontend can accurately display the price of the trip given the price per mile 
   * which the user will input.
   */
  @Test
  @Order(6)
  public void testGasMileage() {
    // checks if we are able to print out the price of the trip by asking user for Price per mile
    TextUITester testMileage1 = new TextUITester("G\n5.0\nA\nMilwaukee\nQ\n");

    RTFrontendInterface frontend = new RTFrontendFD(new Scanner(System.in),
        new RTBackendFD(new RTGraphFD(), new DotDataReaderFD()));
    frontend.runCommandLoop();

    String output = testMileage1.checkOutput();
    //System.out.println(output);

    assertTrue(output.contains(
        "The shortest path between all major cities in Wisconsin starting in Milwaukee is Milwaukee "
            + "-> Madison -> Green Bay -> Kenosha -> Appleton"));
    assertTrue(output.contains("The distance of this trip is 492.5 miles"));
    assertTrue(output
        .contains("The cost for this trip, given that the price per gallon is $5.0, is $2462.5"));
  }

}
