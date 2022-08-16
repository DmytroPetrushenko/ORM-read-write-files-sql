package com.knubisoft.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Table {
    private Map<Integer, Map<String, String>> table;

    public int size() {
        return table.size();
    }

    public Map<String, String> getRowByIndex(Integer rowNumber) {
        Map<String, String> row = table.get(rowNumber);
        return row == null ? null : new LinkedHashMap<>(row);
    }
}
