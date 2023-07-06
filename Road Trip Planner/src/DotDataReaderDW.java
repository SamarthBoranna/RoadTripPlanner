// --== CS400 Spring 2023 File Header Information ==--
// Name: Samarth Boranna
// Email: boranna
// Team: AD
// TA: Rachit Tibdewal
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DotDataReaderDW implements DotDataReaderInterface {

  /**
   * Constructor to create a DotDataReaderDW object and use it to call the method
   */
  public DotDataReaderDW() {
  }

  /**
   * This method takes in a DOT formatted file and parses the different nodes and the edge weight
   * between them into an array of Strings, and adds each array into a list containing all the data
   * from the file.
   * 
   * @return List<String[]> cityData, a list of string arrays of all the data
   */
  @Override
  public List<String[]> readDataFromFile(String filename) throws FileNotFoundException {

    List<String[]> cityData = new ArrayList<>();
    Scanner scanner;

    // If parameter is null, return null
    if (filename == null) {
      throw new FileNotFoundException();
    }

    // Checks that filename exists, if not, returns null
    try {
      scanner = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    }

    // Iterates through the file and adds nodes and edges to an array of strings, then adds each
    // array to a list
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.contains("--")) {
        String[] parts = line.split("\"");
        String[] cities = new String[3];
        cities[0] = parts[1].trim(); // Adds origin city to index 0
        cities[1] = parts[3].trim(); // Adds destination city to index 2
        cities[2] = parts[5].trim(); // Adds distance (miles) to index 3
        cityData.add(cities);
      }
    }
    scanner.close();

    return cityData; // returns list of string arrays of cities and distances
  }

}
