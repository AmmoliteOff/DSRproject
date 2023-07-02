package org.dsr.practice.utils;

import java.util.HashMap;
import java.util.Map;

public class PriceConverter {
    public static Map<Long, Long> convert(Map<Long, Double> toConvert){
        Map<Long, Long> result = new HashMap<Long, Long>();
        for (Long id:toConvert.keySet()) {
            result.put(id, Double.valueOf(toConvert.get(id)*100).longValue());
        }
        return result;
    }
}
