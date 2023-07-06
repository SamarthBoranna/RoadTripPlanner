import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;

public class RTFrontendFD implements RTFrontendInterface {

  private Scanner userInput;
  private RTBackendInterface<String> backend;
  private double price;
  private boolean showCost = false;

  public RTFrontendFD(Scanner userInput, RTBackendInterface<String> backend) {
    this.userInput = userInput;
    this.backend = backend;
  }

  private void line() { 
    System.out
        .println("-------------------------------------------------------------------------------");
  }

  @Override
  public void runCommandLoop() {
    // Display opening message
    line();
    System.out.println("Road Trip Planner for Cities in Wisconsin");
    line();

    char command = '\0'; // Set command to a null char
    while (command != 'Q') { // Continue looping through program until user wants to quit
      command = this.mainMenuPrompt();

      switch (command) {
        case 'D':
          loadDataCommand();
          continue;
        case 'P':
          findShortestBetweenTwoCities();
          continue;
        case 'A':
          findShortestBetweenAllCities();
          continue;
        case 'G':
          chooseGasMileagePrompt();
          continue;
        case 'M':
          findShortestBetweenMultipleCities();
          continue;
        case 'Q':
          break;
        default:
          System.out.println(
              "Invalid command. Please type one of the letters presented within []s to identify the command you would like to choose.");
          break;
      }

    }
    // Display closing message
    System.out.println("");
    line();
    System.out.println("Thank you for accessing Road Trip Planner.");
    line();
  }

  @Override
  public char mainMenuPrompt() {
    // Display prompts for the user
    System.out.println("Choose a command from the list below:");
    System.out.println("    Load [D]ata From File");
    System.out.println(
        "    Find Shortest [P]ath between two Locations <insert location> <insert location>");
    System.out
        .println("    Find the Shortest Path that goes through [A]ll cities <insert location>");
    System.out.println("    Input Price per [G]allon <insert price>");
    System.out.println(
        "    Find Shortest Path between [M]ultiple locations <insert number of locations>");
    System.out.println("    [Q]uit");

    // Read and trim user input
    System.out.println("Choose command: ");
    String input = userInput.nextLine().trim();
    if (input.length() == 0) { // If blank, return a null char value
      return '\0';
    }

    // Else, return the command letter capitalized
    return Character.toUpperCase(input.charAt(0));
  }

  @Override
  public void loadDataCommand() {
    System.out.print("Input path to the desired file: ");
    String filename = userInput.nextLine().trim(); // Load file
    try {
      backend.loadData(filename);
    } catch (FileNotFoundException e) { // File is invalid
      System.out.println("Error: Could not find or load file: " + filename);
    }
  }

  @Override
  public void chooseGasMileagePrompt() {
    double returnValue;
    boolean isFirst = true;
    outer: while (true) {// loops until a valid double is inputed by the user
      if (isFirst)
        System.out.println("Input gas mileage (dollars/mile): ");
      String input = userInput.nextLine();
      try {
        returnValue = Double.parseDouble(input);// attempts to convert the string the user inputed
                                                // to a double
        showCost = true;
        break outer;// no exception thrown, end loop and store value in gasMileage field
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid gas mileage (dollars/mile): "); // exception
                                                                                 // thrown, reprompt
                                                                                 // the user
      }
    }
    price = returnValue;
  }

  @Override
  public void findShortestBetweenAllCities() {
    System.out.print("Input starting city: ");
    String startingCity = userInput.nextLine();
    while (!backend.isCityValid(startingCity)) {
      System.out.println("Please enter a valid city.");
      System.out.print("Input starting city: ");
      startingCity = userInput.nextLine();
    }

    List<String> path = backend.shortestPathAllCities(startingCity);

    String display = "";
    int i;
    for (i = 0; i < path.size() - 1; i++) {
      display += path.get(i) + " -> ";
    }
    display += path.get(i);

    System.out.println("The shortest path between all major cities in Wisconsin starting in "
        + startingCity + " is " + display);
    System.out
        .println("The distance of this trip is " + backend.distanceFromLinkedList(path) + " miles");

    if (showCost) {
      String formattedNum = String.format("%.2f", (price * backend.distanceFromLinkedList(path)));
      System.out.println("The cost for this trip, given that the price per gallon is $"
          + String.format("%.2f", price) + ", is $" + formattedNum);
    }
  }

  @Override
  public void findShortestBetweenMultipleCities() {
    int numLocations;

    do {
      System.out.println("Input number of locations: ");
      while (!userInput.hasNextInt()) {
        System.out.print("Please enter a valid number.");
        System.out.println("Input number of locations: ");
        userInput.nextLine();
      }
      numLocations = userInput.nextInt();
      userInput.nextLine(); // consume the leftover newline character
    } while (numLocations <= 0);

    List<String> cities = new LinkedList<String>();

    for (int i = 1; i <= numLocations; i++) {
      System.out.println("Input location " + (i) + ":  ");
      String location = userInput.nextLine();
      while (!backend.isCityValid(location)) {
        System.out.println("Please enter a valid city.");
        System.out.print("Input location " + (i) + ":  ");
        location = userInput.nextLine();
      }
      // add each city to the linked list that holds all the cities
      cities.add(location);
    }

    List<String> path = backend.roadTripPlan(cities);
    String display = "";

    int j;
    for (j = 0; j < path.size() - 1; j++) {
      display += path.get(j) + " -> ";
    }
    display += path.get(j);

    System.out.println("The shortest path between these cities is " + display);
    System.out
        .println("The distance of this trip is " + backend.distanceFromLinkedList(path) + " miles");

    if (showCost) {
      String formattedNum = String.format("%.2f", (price * backend.distanceFromLinkedList(path)));
      System.out.println("The cost for this trip, given that the price per gallon is $"
          + String.format("%.2f", price) + ", is $" + formattedNum);
    }
  }

  @Override
  public void findShortestBetweenTwoCities() {
    System.out.println("Input starting city: ");
    String startingCity = userInput.nextLine();
    while (!backend.isCityValid(startingCity)) {
      System.out.println("Please enter a valid city.");
      System.out.print("Input starting city: ");
      startingCity = userInput.nextLine();
    }

    System.out.println("Input ending city: ");
    String endingCity = userInput.nextLine();
    while (!backend.isCityValid(endingCity)) {
      System.out.println("Please enter a valid city.");
      System.out.print("Input ending city: ");
      endingCity = userInput.nextLine();
    }

    List<String> path = backend.shortestPath(startingCity, endingCity);

    String display = "";
    int i;
    for (i = 0; i < path.size() - 1; i++) {
      display += path.get(i) + " -> ";
    }
    display += path.get(i);

    System.out.println(
        "The shortest road trip from " + startingCity + " to " + endingCity + " is " + display);
    System.out
        .println("The distance of this trip is " + backend.distanceFromLinkedList(path) + " miles");

    if (showCost) {
      String formattedNum = String.format("%.2f", (price * backend.distanceFromLinkedList(path)));
      System.out.println("The cost for this trip, given that the price per gallon is $"
          + String.format("%.2f", price) + ", is $" + formattedNum);
    }
  }

}
