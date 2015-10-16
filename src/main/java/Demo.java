
import java.util.Map;
import java.util.TreeMap;
import net.coderodde.util.BTreeMap;

public class Demo {

    public static void main(String[] args) {
       Map<Integer, Integer> treeMap = new TreeMap<>();
       Map<Integer, Integer> btreeMap = new BTreeMap<>(16);
       
       int SIZE = 1000000;
       
       long startTime = System.currentTimeMillis();
       
       for (int i = 0; i < SIZE; ++i) {
           treeMap.put(i, 3 * i);
       }
       
       long endTime = System.currentTimeMillis();
       
       System.out.println(" TreeMap.put in " + (endTime - startTime) +
                          " milliseconds.");
       
       startTime = System.currentTimeMillis();
       
       for (int i = 0; i < SIZE; ++i) {
           btreeMap.put(i, 3 * i);
       }
       
       endTime = System.currentTimeMillis();
       
       System.out.println("BTreeMap.put in " + (endTime - startTime) +
                          " milliseconds.");
       
       startTime = System.currentTimeMillis();
       
       for (int i = 0; i < SIZE; ++i) {
           int number = treeMap.get(i);
           
           if (number != 3 * i) {
               throw new IllegalStateException("TreeMap broken.");
           }
       }
       
       endTime = System.currentTimeMillis();
       
       System.out.println(" TreeMap.get in " + (endTime - startTime) + 
                          " milliseconds.");
       
       startTime = System.currentTimeMillis();
       
       for (int i = 0; i < SIZE; ++i) {
           int number = btreeMap.get(i);
           
           if (number != 3 * i) {
               throw new IllegalStateException("BTreeMap broken.");
           }
       }
       
       endTime = System.currentTimeMillis();
       
       System.out.println("BTreeMap.get in " + (endTime - startTime) + 
                          " milliseconds.");
    }
}
