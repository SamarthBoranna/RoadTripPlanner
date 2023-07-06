import java.io.FileNotFoundException;
import java.util.List;

public interface DotDataReaderInterface {
   // public DotDataReaderInterface();
   public List<String[]> readDataFromFile(String filename) throws FileNotFoundException; // stores a list of string arrays of length 3, with the first indexes (0 and 1) being the predecessor and successor respectively, and index 2 being the weight as a string
}
