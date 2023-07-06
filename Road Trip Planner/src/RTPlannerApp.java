// --== CS400 Spring 2023 File Header Information ==--
// Name: Samarth Boranna
// Email: boranna
// Team: AD
// TA: Rachit Tibdewal
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.Scanner;

/**
 * Main Method Class to run all the classes to run the program
 * 
 * @author samarth
 */
public class RTPlannerApp {
  public static void main(String[] args) {
    RTFrontendFD startApp = new RTFrontendFD(new Scanner(System.in),
        new RTBackendBD(new RTGraphAE<>(), new DotDataReaderDW()));
    startApp.runCommandLoop();
  }
}
