package com.example.redischallenge;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapComparator {
    //Method for sorting the TreeMap based on values
    public static <K, V extends Comparable<V>> Map<K, V>
    sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                //int compare = map.get(k1).compareTo(map.get(k2));
                //if (compare == 0) return 1;
                //else return compare;

                if(((Integer) map.get(k1)).intValue() > ((Integer) map.get(k2)).intValue()){
                    return 1;
                }
                else if (((Integer) map.get(k1)).intValue() == ((Integer) map.get(k2)).intValue()){
                    return ((String)k1).compareTo(((String)k2));
                }
                else{
                    return -1;
                }
            }
        };

        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
