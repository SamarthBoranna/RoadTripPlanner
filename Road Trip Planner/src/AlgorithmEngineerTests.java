import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlgorithmEngineerTests {

    /**
     * Test 1: test D to I from directed graph shown in 4/6 lecture
     */
    @Test public void test1() {
        RTGraphAE<String, Integer> graph = new RTGraphAE<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "M", 5);
        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);
        graph.insertEdge("F", "G", 9);

        //test 1.1
        {
            List<String> expected = new ArrayList<>();
            expected.add("D");
            expected.add("A");
            expected.add("H");
            expected.add("I");
            List<String> actual = graph.shortestPathData("D", "I");

            assertEquals(expected, actual, "test 1.1: shortestPathData does not match expected");
        }

        //test 1.2
        {
            double expected = 17;
            double actual = graph.shortestPathCost("D", "I");

            assertEquals(expected, actual, "test 1.2: shortestPathCost does not match expected");
        }
    }

    /**
     * Test 2: test A to L from directed graph shown in 4/6 lecture
     */
    @Test public void test2() {
        RTGraphAE<String, Integer> graph = new RTGraphAE<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "M", 5);
        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);
        graph.insertEdge("F", "G", 9);

        //test 2.1
        {
            List<String> expected = new ArrayList<>();
            expected.add("A");
            expected.add("H");
            expected.add("I");
            expected.add("L");
            List<String> actual = graph.shortestPathData("A", "L");

            assertEquals(expected, actual, "test 2.1: shortestPathData does not match expected");
        }

        //test 2.2
        {
            double expected = 15;
            double actual = graph.shortestPathCost("A", "L");

            assertEquals(expected, actual, "test 2.2: shortestPathCost does not match expected");
        }
    }

    /**
     * Test 3: test M to A (no possible path) from directed graph shown in 4/6 lecture
     */
    @Test public void test3() {
        RTGraphAE<String, Integer> graph = new RTGraphAE<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "M", 5);
        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);
        graph.insertEdge("F", "G", 9);

        //test 3.1
        {
            Exception exception =
                assertThrows(Exception.class, () -> graph.shortestPathData("M", "A"),
                    "test 3.1: Expected shortestPathData() to throw, but it didn't");
            String expected = "Path is not valid!";
            String actual = exception.getMessage();

            assertEquals(expected, actual, "test 3.1: exception message does not match expected");
        }

        //test 3.2
        {
            Exception exception =
                assertThrows(Exception.class, () -> graph.shortestPathCost("M", "A"),
                    "test 3.2: Expected shortestPathCost() to throw, but it didn't");
            String expected = "Path is not valid!";
            String actual = exception.getMessage();

            assertEquals(expected, actual, "test 3.2: exception message does not match expected");
        }
    }

    /**
     * Test 4: basic drawn out graph, testing findMinimumSpanningTree() method
     */
    @Test public void test4() {
        RTGraphAE<String, Integer> graph = new RTGraphAE<>();
        graph.insertNode("a");
        graph.insertNode("b");
        graph.insertNode("c");
        graph.insertNode("d");

        graph.insertEdge("a", "b", 1);
        graph.insertEdge("a", "c", 2);
        graph.insertEdge("a", "d", 6);
        graph.insertEdge("c", "d", 3);
        graph.insertEdge("b", "a", 1);
        graph.insertEdge("c", "a", 2);
        graph.insertEdge("d", "a", 6);
        graph.insertEdge("d", "c", 3);

        // test 4.1 -- dbca -> dcab
        {
            List<String> nodesVisited = new ArrayList<>();
            nodesVisited.add("d");
            nodesVisited.add("c");
            nodesVisited.add("a");
            nodesVisited.add("b");
            List<String> actual = graph.findMinimumSpanningTree(nodesVisited);

            List<String> expected = new ArrayList<>();
            expected.add("d");
            expected.add("c");
            expected.add("a");
            expected.add("b");

            assertEquals(expected, actual, "test 4.1: findMinimumSpanningTree does not match "
                + "expected");
        }

        // test 4.2 -- bad -> bacd
        {
            List<String> nodesVisited = new ArrayList<>();
            nodesVisited.add("b");
            nodesVisited.add("a");
            nodesVisited.add("d");
            List<String> actual = graph.findMinimumSpanningTree(nodesVisited);

            List<String> expected = new ArrayList<>();
            expected.add("b");
            expected.add("a");
            expected.add("c");
            expected.add("d");

            assertEquals(expected, actual, "test 4.2: findMinimumSpanningTree does not match "
                + "expected");
        }

    }

    /**
     * Test 5: basic map of Wisconsin, testing findMinimumSpanningTree() method
     */
    @Test public void test5() {
        RTGraphAE<String, Integer> graph = new RTGraphAE<>();
        graph.insertNode("Madison");
        graph.insertNode("Milwaukee");
        graph.insertNode("Green Bay");
        graph.insertNode("Eau Claire");
        graph.insertNode("La Crosse");

        graph.insertEdge("Madison", "Milwaukee", 60);
        graph.insertEdge("Madison", "Green Bay", 100);
        graph.insertEdge("Madison", "Eau Claire", 150);
        graph.insertEdge("Madison", "La Crosse", 120);

        graph.insertEdge("La Crosse", "Milwaukee", 260);
        graph.insertEdge("La Crosse", "Green Bay", 250);
        graph.insertEdge("La Crosse", "Eau Claire", 50);
        graph.insertEdge("La Crosse", "Madison", 120);

        graph.insertEdge("Eau Claire", "Milwaukee", 250);
        graph.insertEdge("Eau Claire", "Green Bay", 180);
        graph.insertEdge("Eau Claire", "La Crosse", 50);
        graph.insertEdge("Eau Claire", "Madison", 150);

        graph.insertEdge("Green Bay", "Milwaukee", 80);
        graph.insertEdge("Green Bay", "Eau Claire", 180);
        graph.insertEdge("Green Bay", "La Crosse", 250);
        graph.insertEdge("Green Bay", "Madison", 100);

        graph.insertEdge("Milwaukee", "Green Bay", 80);
        graph.insertEdge("Milwaukee", "Eau Claire", 250);
        graph.insertEdge("Milwaukee", "La Crosse", 260);
        graph.insertEdge("Milwaukee", "Madison", 60);

        // test 5.1 -- La Crosse, Green Bay, Madison -> La Crosse, Madison, Green Bay
        {
            List<String> nodesVisited = new ArrayList<>();
            nodesVisited.add("La Crosse");
            nodesVisited.add("Green Bay");
            nodesVisited.add("Madison");
            List<String> actual = graph.findMinimumSpanningTree(nodesVisited);

            List<String> expected = new ArrayList<>();
            expected.add("La Crosse");
            expected.add("Madison");
            expected.add("Green Bay");

            assertEquals(expected, actual, "test 5.1: findMinimumSpanningTree does not match "
                + "expected");
        }

        // test 4.2 -- MilMadG -> Milwaukee, Madison, Green Bay
        {
            List<String> nodesVisited = new ArrayList<>();
            nodesVisited.add("Milwaukee");
            nodesVisited.add("Madison");
            nodesVisited.add("Green Bay");
            List<String> actual = graph.findMinimumSpanningTree(nodesVisited);

            List<String> expected = new ArrayList<>();
            expected.add("Milwaukee");
            expected.add("Madison");
            expected.add("Green Bay");

            assertEquals(expected, actual, "test 5.2: findMinimumSpanningTree does not match "
                + "expected");
        }

        // test 5.3 -- ELMadMilG -> Eau Claire, La Crosse, Madison, Milwaukee, Green Bay
        {
            List<String> nodesVisited = new ArrayList<>();
            nodesVisited.add("Eau Claire");
            nodesVisited.add("Madison");
            nodesVisited.add("Milwaukee");
            nodesVisited.add("Green Bay");
            nodesVisited.add("La Crosse");
            List<String> actual = graph.findMinimumSpanningTree(nodesVisited);

            List<String> expected = new ArrayList<>();
            expected.add("Eau Claire");
            expected.add("La Crosse");
            expected.add("Madison");
            expected.add("Milwaukee");
            expected.add("Green Bay");

            assertEquals(expected, actual, "test 5.3: findMinimumSpanningTree does not match "
                + "expected");
        }
    }
}
