// --== CS400 File Header Information ==--
// Name: Tarun Chichili
// Email: chichili@wisc.edu
// Team: AD
// TA: Rachit Tibrewal
// Lecturer: Gary Dahl
// Notes to Grader:

import java.io.FileNotFoundException;
import java.util.List;

public interface RTBackendInterface<NodeType> {
    //public RTBackendInterface(RTGraphInterface map, DotDataReaderInterface cityReader);

    public void loadData(String filename) throws FileNotFoundException;

    public List<NodeType> roadTripPlan(List<String> cities);
    public List<NodeType> shortestPath(String city1, String city2);
    public List<NodeType> shortestPathAllCities(String startCity);
    public boolean isCityValid(String cityName);
    //given linked list of valid cities output total distance between them
    public double distanceFromLinkedList(List<String> cities);

}
