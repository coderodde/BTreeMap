package net.coderodde.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This class implements a map using B-trees.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 16, 2015)
 * @param <K>
 * @param <V>
 */
public class BTreeMap<K extends Comparable<? super K>, V> implements Map<K, V> {

    static final class BTreeNode<K extends Comparable<? super K>, V> {
        int      size;
        Object[] keys;
        Object[] values;
        BTreeNode<K, V>[] children;
        
        BTreeNode(int minimumDegree) {
            this.keys = new Object[2 * minimumDegree - 1];
            this.values = new Object[keys.length];
        }
        
        boolean isLeaf() {
            return children == null;
        }
        
        void makeInternal() {
            this.children = (BTreeNode<K, V>[]) new BTreeNode[keys.length + 1];
        }
        
        
    }
    
    /**
     * The minimum amount of children for each non-root node. This implies that
     * any non-root node has at least {@code minimumDegree - 1} keys stored in 
     * it.
     */
    private final int minimumDegree;
    private int size;
    private BTreeNode<K, V> root;
    
    public BTreeMap(int minimumDegree) {
        this.minimumDegree = Math.max(2, minimumDegree);
        this.root = new BTreeNode<>(this.minimumDegree);
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public V get(Object key) {
        return treeSearch(root, (K) key);
    }

    @Override
    public V put(K key, V value) {
        BTreeNode<K, V> r = root;
        
        if (r.size == 2 * minimumDegree - 1) {
            BTreeNode<K, V> s = new BTreeNode<>(minimumDegree);
            root = s;
            s.makeInternal();
            s.children[0] = r;
            treeSplitChild(s, 0);
            return insertNonFull(s, key, value);
        } else {
            return insertNonFull(r, key, value);
        }
    }
    
    private V treeSearch(BTreeNode<K, V> x, K key) {
        int i = 0;
        
        while (i < x.size && key.compareTo((K) x.keys[i]) > 0) {
            ++i;
        }
        
        if (i < x.size && key.equals(x.keys[i])) {
            return (V) x.values[i];
        } else if (x.isLeaf()) {
            return null;
        } else {
            return treeSearch(x.children[i], key);
        }
    }
    
    private V insertNonFull(BTreeNode<K, V> x, K key, V value) {
        int i = x.size - 1;
        
        if (x.isLeaf()) {
            for (int j = 0; j < x.size; ++j) {
                if (x.keys[j].equals(key)) {
                    V old = (V) x.values[j];
                    x.values[i] = value;
                    // Entry exists in this tree, just update its value and 
                    // return the old value.
                    return old;
                }
            }
            
            for(; i >= 0 && key.compareTo((K) x.keys[i]) < 0; --i) {
                x.keys[i + 1]   = x.keys[i];
                x.values[i + 1] = x.values[i];
            }
            
            x.keys[i + 1]   = key;
            x.values[i + 1] = value;
            x.size++;
            size++;
            return null;
        } else {
            while (i >= 0 && key.compareTo((K) x.keys[i]) < 0) {
                --i;
            }
            
            ++i;
            
            if (x.children[i].size == 2 * minimumDegree - 1) {
                treeSplitChild(x, i);
                
                if (key.compareTo((K) x.keys[i]) > 0) {
                    ++i;
                }
            }
            
            return insertNonFull(x.children[i], key, value);
        }
    }
    
    private void treeSplitChild(BTreeNode<K, V> x, int i) {
        BTreeNode<K, V> z = new BTreeNode<>(minimumDegree);
        BTreeNode<K, V> y = x.children[i];
        
        if (!y.isLeaf()) {
            z.makeInternal();
        }
        
        z.size = minimumDegree - 1;
        
        for (int j = 0; j  < minimumDegree - 1; ++j) {
            z.keys[j]   = y.keys[j + minimumDegree];
            z.values[j] = y.values[j + minimumDegree];
        }
            
        if (!y.isLeaf()) {
            for (int j = 0; j < minimumDegree; ++j) {
                z.children[j] = y.children[j + minimumDegree];
            }
        }
        
        y.size = minimumDegree - 1;
        
        for (int j = x.size; j >= i; --j) {
            x.children[j + 1] = x.children[j];
        }
        
        x.children[i + 1] = z;
        
        for (int j = x.size - 1; j >= i; --j) {
            x.keys[j + 1]   = x.keys[j];
            x.values[j + 1] = x.values[j];
        }
        
        x.keys[i]   = y.keys[minimumDegree - 1];
        x.values[i] = y.values[minimumDegree - 1]; 
        x.size++;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        root = new BTreeNode<>(minimumDegree);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
