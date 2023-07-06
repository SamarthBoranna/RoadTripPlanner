
public interface RTFrontendInterface {
  // public RTFrontend(Scanner userInput, RTBackendInterface backend);
  public void runCommandLoop();

  public char mainMenuPrompt();

  public void loadDataCommand();

  public void chooseGasMileagePrompt();

  public void findShortestBetweenAllCities();

  public void findShortestBetweenMultipleCities();

  public void findShortestBetweenTwoCities();

}
