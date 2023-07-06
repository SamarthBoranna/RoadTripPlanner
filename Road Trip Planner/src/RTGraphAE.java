// --== CS400 File Header Information ==--
// Name: Matthew Brandt
// Email: mmbrandt2@wisc.edu
// Group and Team: AD, red
// Group TA: Rachit Tibdewal
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes.  This class makes use of Dijkstra's shortest path algorithm.
 */
public class RTGraphAE<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements RTGraphInterface<NodeType, EdgeType> {

    /**
     * This method determines the shortest path that visits each node (city) that is passed through
     * the input. It will make use of a modified version of Prim's method to determine the minimum
     * spanning tree of all the visited nodes.
     *
     * @param allNodes nodes that need to be visited
     * @return ShortestPath a list of the nodes visited in order to find the shortest path
     */
    @Override public List<NodeType> findMinimumSpanningTree(List<NodeType> allNodes) {

        // allNodes empty, return null
        if (allNodes.size() == 0)
            return null;

        // if a node isn't present in graph, throw exception
        for (NodeType node : allNodes) {
            if (!containsNode(node)) {
                throw new NoSuchElementException("Node not in graph");
            }
        }

        List<NodeType> toReturn = new ArrayList<>();

        // start from first node listed
        NodeType currentNode = allNodes.get(0);
        toReturn.add(currentNode);
        allNodes.remove(0); // remove start node

        while (allNodes.size() != 0) {
            double minCost = Double.MAX_VALUE; // set to infinity

            // define to use later
            List<NodeType> cheapestPath = new ArrayList<>();
            NodeType toRemove = null;

            // loop through allNodes list from first node to find the shortest path
            for (NodeType node : allNodes) {
                double compareCost;
                try {
                    compareCost = shortestPathCost(currentNode, node);
                } catch (NoSuchElementException e) {
                    continue; // skip paths that are not possible
                }
                if (compareCost < minCost) {
                    minCost = compareCost;
                    cheapestPath = shortestPathData(currentNode, node);
                    toRemove = node; // marking node to remove if cheapest path
                }
            }

            // no match found within loop
            if (cheapestPath.size() == 0)
                throw new NoSuchElementException("No possible path!");

            // switch current node to last node in list
            currentNode = cheapestPath.get(cheapestPath.size() - 1);

            // add cheapestPath to toReturn list, with first node removed
            cheapestPath.remove(0);
            toReturn.addAll(cheapestPath);

            allNodes.remove(toRemove); // remove visited node from list
        }

        return toReturn;
    }

    /**
     * While searching for the shortest path between two nodes, a SearchNode contains data about one
     * specific path between the start node and another node in the graph.  The final node in this
     * path is stored in it's node field.  The total cost of this path is stored in its cost field.
     * And the predecessor SearchNode within this path is referened by the predecessor field (this
     * field is null within the SearchNode containing the starting node in it's node field).
     * <p>
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
     * highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the shortest path between
     * the provided start and end locations.  The SearchNode that is returned by this method is
     * represents the end of the shortest path that is found: it's cost is the cost of that shortest
     * path, and the nodes linked together through predecessor references represent all of the nodes
     * along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found or when either start
     *                                or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {

        // if a node isn't present in graph, throw exception
        if (!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("Start OR end node not in graph");
        }

        // dealing with edge case if start = end
        if (start.equals(end)) {
            return new SearchNode(nodes.get(start), 0.0, null);
        }

        // make a hashtable to keep track of visited nodes and shortest costs to each node
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();
        Hashtable<NodeType, Double> costs = new Hashtable<>();
        Hashtable<NodeType, Boolean> visited = new Hashtable<>();

        SearchNode current = null;
        boolean endNodeFound = false; // to keep track if end node was ever found

        // make a search node for the start node
        SearchNode startNode = new SearchNode(nodes.get(start), 0.0, null);

        // loop through edges that extend from start node and add them to queue
        for (Edge edge : nodes.get(start).edgesLeaving) {
            priorityQueue.add(new SearchNode(edge.successor, edge.data.doubleValue(), startNode));
        }

        // put start node in visited hashtable
        visited.put(start, true);

        while (!priorityQueue.isEmpty()) {
            current = priorityQueue.poll();
            NodeType currentNode = current.node.data;
            double cost = current.cost;

            // skip nodes already visited,
            if (visited.get(currentNode) != null && visited.get(currentNode)) {
                continue;
            }

            // set visited to true in hashtable
            // put cost in costs hashtable
            visited.put(currentNode, true);
            costs.put(currentNode, cost);

            // found end node, report to boolean
            if (currentNode.equals(end)) {
                endNodeFound = true;
                break;
            }

            for (Edge edge : nodes.get(currentNode).edgesLeaving) {
                // finding each new node to travel to
                NodeType newNode = edge.successor.data;
                double altCost = edge.data.doubleValue() + cost;
                Double originalCost = costs.get(newNode);

                // if the altCost is less than the stored altCost, replace and add new path to
                // priority queue
                if (originalCost == null || altCost < originalCost) {
                    costs.put(newNode, altCost);
                    priorityQueue.add(new SearchNode(edge.successor, altCost, current));
                }
            }
        }

        // if end node was not found, throw exception
        if (!endNodeFound)
            throw new NoSuchElementException("Path is not valid!");

        return current;
    }

    /**
     * Returns the list of data values from nodes along the shortest path from the node with the
     * provided start value through the node with the provided end value.  This list of data values
     * starts with the start value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This method uses Dijkstra's
     * shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {

        List<NodeType> backwardsPath = new ArrayList<>();
        List<NodeType> toReturn = new ArrayList<>();
        SearchNode currentNode = computeShortestPath(start, end);

        // loop through to get backwards path
        while (currentNode != null) {
            backwardsPath.add(currentNode.node.data);
            currentNode = currentNode.predecessor;
        }

        // make path forwards to return
        for (int i = backwardsPath.size() - 1; i >= 0; i--) {
            toReturn.add(backwardsPath.get(i));
        }

        return toReturn;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest path from the node
     * containing the start data to the node containing the end data.  This method uses Dijkstra's
     * shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        return computeShortestPath(start, end).cost;
    }

}

