package com.knubisoft.strategy.impl;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.dto.Table;
import com.knubisoft.strategy.ReaderStrategy;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReaderStrategyJson implements ReaderStrategy<DataReadWriteSource> {
    private static final String PATTERN = "\\[.*\\{.+\\}+.*\\]";

    @Override
    public boolean isApplied(DataReadWriteSource content) {
        return content instanceof StringReadWriteSource
                && ((StringReadWriteSource) content).getContent().replaceAll("\\r\\n", "")
                .matches(PATTERN);
    }

    @Override
    public Table parseToTable(DataReadWriteSource data) {
        String content = ((StringReadWriteSource) data).getContent().replaceAll("\\r\\n", "");
        return createTable(content);
    }

    private Table createTable(String content) {
        String[] split = content.replaceAll("((\\[)|(\\])|(\\s))", "")
                .replaceAll("\\},\\{", ";")
                .replaceAll("[\\{\\}\\\"]", "")
                .split(";");
        Map<Integer, Map<String, String>> mapForTable = IntStream.range(0, split.length)
                .boxed()
                .collect(Collectors.toMap(num -> num, num -> createRow(split[num])));
        return new Table(mapForTable);
    }

    private Map<String, String> createRow(String string) {
        String[] cellString = string.split(",");
        return Arrays.stream(cellString)
                .collect(Collectors.toMap(cell -> cell.split(":")[0],cell -> cell.split(":")[1]));
    }
}
