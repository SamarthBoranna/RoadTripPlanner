// --== CS400 File Header Information ==--
// Name: Tarun Chichili
// Email: chichili@wisc.edu
// Team: AD
// TA: Rachit Tibrewal
// Lecturer: Gary Dahl
// Notes to Grader:

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RTBackendBD implements RTBackendInterface{

    RTGraphInterface map;
    DotDataReaderInterface mapReader;

    //keep track of nodes for MST

    List nodes = new ArrayList();

    public RTBackendBD(RTGraphInterface map, DotDataReaderInterface mapReader){
        this.map = map;
        this.mapReader = mapReader;
    }

    @Override
    public void loadData(String filename) throws FileNotFoundException {
        List<String[]> dataFromFile = mapReader.readDataFromFile(filename);
        if(dataFromFile == null){
            return;
        }

        for(String[] data: dataFromFile){
            String previous = data[0];
            String next = data[1];
            double weight = Double.parseDouble(data[2]);
            //first insert the previous and next nodes, then insert the edge
            if(map.insertNode(previous)){
                nodes.add(previous);
            }
            if(map.insertNode(next)){
                nodes.add(next);
            }
            map.insertEdge(previous,next,weight);
            map.insertEdge(next, previous, weight);
        }
    }

    @Override
    public List shortestPath(String city1, String city2) {
        return map.shortestPathData(city1, city2);
    }

    @Override
    public List shortestPathAllCities(String startCity) {
        List tempNodes = new ArrayList();
        tempNodes.add(startCity);
        for(Object i : nodes){
            if(i == startCity){
                continue;
            }
            tempNodes.add(i);
        }
        List path = map.findMinimumSpanningTree(tempNodes);
        return path;
    }

    @Override
    public boolean isCityValid(String cityName) {
        return map.containsNode(cityName);
    }

    @Override
    public double distanceFromLinkedList(List cities) {
        double totalCost = 0;
        for(int i = 0; i < cities.size()-1; i++ ){
            double sectionCost = map.shortestPathCost(cities.get(i), cities.get(i+1));
            totalCost += sectionCost;
        }

        return totalCost;
    }

    @Override
    public List roadTripPlan(List cities) {
        List finalPath = new ArrayList();
        for(int i = 0; i < cities.size()-1; i++ ){
            List section = map.shortestPathData(cities.get(i), cities.get(i+1));
            if(finalPath.size() == 0){
                finalPath.addAll(section);
            } else{
                finalPath.remove(finalPath.size()-1);
                finalPath.addAll(section);
            }
        }
        return finalPath;
    }
}
