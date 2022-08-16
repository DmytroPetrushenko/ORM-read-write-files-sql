package com.knubisoft.strategy.impl;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.dto.Table;
import com.knubisoft.strategy.ReaderStrategy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReaderStrategyXml<T extends DataReadWriteSource> implements ReaderStrategy<T> {
    private static final String PATTERN = "\\<\\?xml.+\\>\\<\\w+s\\>.*\\<\\/\\w+s\\>";
    private int count;

    @Override
    public boolean isApplied(DataReadWriteSource content) {
        return content instanceof StringReadWriteSource
                && ((StringReadWriteSource) content).getContent().replaceAll("\\r\\n", "")
                .matches(PATTERN);
    }

    @Override
    public Table parseToTable(DataReadWriteSource content) {
        String[] array = formatToArray(content);
        Map<Integer, Map<String, String>> mapTable = getTableFromArray(array);
        return new Table(mapTable);
    }

    private Map<Integer, Map<String, String>> getTableFromArray(String[] array) {
        Map<Integer, Map<String, String>> mapTable = new LinkedHashMap<>();
        for (int i = 0; i < array.length - count + 1; i += count) {
            Map<String, String> row = new LinkedHashMap<>();
            for (int j = i; j < i + count; j++) {
                String string = array[j];
                String name = string.substring(string.indexOf("<") + 1, string.indexOf(">"));
                String value = string.substring(string.indexOf(">") + 1, string.indexOf("</"));
                row.put(name, value);
            }
            mapTable.put(i == 0 ? 0 : i - count + 1, row);
        }
        return mapTable;
    }

    private String[] formatToArray(DataReadWriteSource content) {
        String[] array = ((StringReadWriteSource) content).getContent().replaceAll("\\r\\n", "")
                .replaceAll("((\\<\\?.*\\?>)|(<\\w+s>)|(<\\/\\w+s>))", "").trim()
                .split("\\s+");
        countFields(array);

        return Arrays.stream(array)
                .filter(string -> !string.matches("((<\\w+>)|(</\\w+>))"))
                .toArray(String[]::new);
    }

    private int countFields(String[] split) {
        count = 0;
        for (String string : split) {
            if (!string.matches("<\\w+>") && !string.matches("</\\w+>")) {
                count++;
            }
            if (string.matches("</\\w+>")) {
                break;
            }
        }
        return count;
    }
}
