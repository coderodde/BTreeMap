package net.coderodde.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class BTreeMapTest {
    
    private final BTreeMap<Integer, Integer> map = new BTreeMap<>(2);
    
    @Test
    public void testSplitChild() {
        BTreeMap<Character, Character> map = new BTreeMap<>(2);
        map.put('J', 'J');
        map.put('G', 'G');
        map.put('I', 'I');
        map.put('H', 'H');
        System.out.println("");
    }
    
    @Test
    public void testSize() {
        map.clear();
        
        for (int i = 0; i < 10; ++i) {
            assertEquals(i, map.size());
            map.put(i, i);
            assertEquals(i + 1, map.size());
        }
    }

    @Test
    public void testIsEmpty() {
        map.clear();
        assertTrue(map.isEmpty());
        
        for (int i = 0; i < 1000; ++i) {
            map.put(i, i);
            assertFalse(map.isEmpty());
        }
    }

    @Test
    public void testContainsKey() {
    }

    @Test
    public void testContainsValue() {
    }

    @Test
    public void testGet() {
        map.clear();
        assertTrue(map.isEmpty());
        
        for (int i = 0; i < 1999; ++i) {
            map.put(i, 2 * i);
        }
        
        for (int i = 0; i < 1999; ++i) {
            assertEquals((Integer)(2 * i), map.get(i));
        }
    }

    @Test
    public void testPut() {
    }

    @Test
    public void testRemove() {
    }

    @Test
    public void testPutAll() {
    }

    @Test
    public void testClear() {
    }

    @Test
    public void testKeySet() {
    }

    @Test
    public void testValues() {
    }

    @Test
    public void testEntrySet() {
    }
    
}
