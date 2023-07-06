import java.util.List;

public interface RTGraphInterface<NodeType,EdgeType extends Number> extends GraphADT<NodeType,EdgeType> {
    public List<NodeType> findMinimumSpanningTree(List<NodeType> allNodes);
}
