import java.io.FileNotFoundException;
import java.util.List;
import java.util.LinkedList;

public class RTBackendFD implements RTBackendInterface<String>{

  public RTGraphInterface map;
  public DotDataReaderInterface cityReader;
  
  public RTBackendFD(RTGraphInterface map, DotDataReaderInterface cityReader)
  {
    this.map = map;
    this.cityReader = cityReader;
  }
  
  @Override
  public void loadData(String filename) throws FileNotFoundException {
    if (!filename.equals("filename.txt"))
      throw new FileNotFoundException("The file was not found");
  }

  @Override
  public List<String> shortestPath(String city1, String city2) {
    List<String> path = new LinkedList<String>();
    path.add(city1);
    path.add("Green Bay");
    path.add(city2);
    return path;
  }

  @Override
  public List<String> shortestPathAllCities(String startCity) {
    List<String> path = new LinkedList<String>();
    path.add(startCity);
    path.add("Madison");
    path.add("Green Bay");
    path.add("Kenosha");
    path.add("Appleton");
    return path;
  }

  @Override
  public boolean isCityValid(String cityName) {
    return true;
  }

  @Override
  public List<String> roadTripPlan(List<String> cities) {
    List<String> path = new LinkedList<String>();
    path = cities;
    return path;
  }

  @Override
  public double distanceFromLinkedList(List<String> head) {
    if (head.equals("Madison"))
      return 241.5;
    if (head.equals("Milwaukee"))
      return 492.5;
    if (head.equals("Superior"))
      return 307.9;
    else
      return 0.0;
  }

}
