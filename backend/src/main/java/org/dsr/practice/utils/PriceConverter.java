package org.dsr.practice.utils;

import org.dsr.practice.models.Pairs;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceConverter {
    public static Map<Long, Long> convert(List<Pairs> toConvert){
        Map<Long, Long> result = new HashMap<Long, Long>();
        for (int i = 0; i<toConvert.size(); i++) {
            result.put(toConvert.get(i).getUserId(), toConvert.get(i).getDebt().multiply(new BigDecimal("100")).longValue());
        }
        return result;
    }

    public static Long convert(BigDecimal value){
        return value.multiply(new BigDecimal("100")).longValue();
    }
}
