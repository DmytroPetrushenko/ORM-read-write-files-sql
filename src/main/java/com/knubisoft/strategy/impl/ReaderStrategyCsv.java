package com.knubisoft.strategy.impl;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.dto.Table;
import com.knubisoft.strategy.ReaderStrategy;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReaderStrategyCsv implements ReaderStrategy<DataReadWriteSource> {
    private static final String PATTERN = "(([\\w\\-]+,)+[\\w\\-]+\\b)";

    @Override
    public boolean isApplied(DataReadWriteSource data) {
        if (data instanceof StringReadWriteSource) {
            return ((StringReadWriteSource) data).getContent()
                    .replaceAll("[\\n\\r]", "").matches(PATTERN);
        }
        return false;
    }

    @Override
    public Table parseToTable(DataReadWriteSource data) {
        String[] rows = ((StringReadWriteSource) data).getContent().replaceAll("\\r\\n", " ")
                .split(" ");
        Map<Integer, String> mapping = createMapping(rows[0]);
        Map<Integer, Map<String, String>> table = createTable(rows, mapping);
        return new Table(table);
    }

    private Map<Integer, Map<String, String>> createTable(String[] rows,
                                                          Map<Integer, String> mapping) {
        return IntStream.range(1, rows.length)
                .boxed()
                .collect(Collectors.toMap(num -> num - 1, num -> createRow(num, rows, mapping)));
    }

    private Map<String, String> createRow(Integer num,
                                          String[] rows, Map<Integer, String> mapping) {
        String[] cells = rows[num].split(",");
        return IntStream.range(0, cells.length)
                .boxed()
                .collect(Collectors.toMap(mapping::get, number -> cells[number]));
    }

    private Map<Integer, String> createMapping(String row) {
        String[] nameCells = row.split(",");
        return IntStream.range(0, nameCells.length)
                .boxed()
                .collect(Collectors.toMap(num -> num, num -> nameCells[num]));
    }
}
