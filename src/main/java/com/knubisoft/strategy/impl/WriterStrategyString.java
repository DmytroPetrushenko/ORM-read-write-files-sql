package com.knubisoft.strategy.impl;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.strategy.WriterStrategy;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;

public class WriterStrategyString<T extends DataReadWriteSource> implements WriterStrategy<T> {

    @Override
    public boolean isApplied(DataReadWriteSource content) {
        return content instanceof StringReadWriteSource;
    }

    @Override
    @SneakyThrows
    public <V> void writeToSource(List<V> list) {
        try (FileWriter writer = new FileWriter("src/main/resources/output.txt")) {
            String names = makeNamesInString(list.get(0));
            writeString(names, writer);
            list.stream()
                    .map(this::makeValuesInString)
                    .forEach(string -> writeString(string, writer));
        }
    }

    @SneakyThrows
    private void writeString(String string, FileWriter writer) {
        writer.write(string);
        writer.append('\n');
    }

    private <V> String makeValuesInString(V entity) {
        StringBuilder builder = new StringBuilder();
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field
                -> builder.append(getValueFromField(field, entity)).append(","));
        String string = builder.toString();
        return string.substring(0, string.length() - 1);
    }

    @SneakyThrows
    private <V> String getValueFromField(Field field, V entity) {
        field.setAccessible(true);
        return String.valueOf(field.get(entity));
    }

    private <V> String makeNamesInString(V v) {
        StringBuilder builder = new StringBuilder();
        Field[] declaredFields = v.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> builder.append(field.getName()).append(","));
        String string = builder.toString();
        return string.substring(0, string.length() - 1);
    }
}
