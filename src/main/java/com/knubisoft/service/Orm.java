package com.knubisoft.service;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.Table;
import com.knubisoft.util.StrategyUtil;
import com.knubisoft.util.TypeFieldsUtil;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;

public class Orm implements OrmInterface {
    private final StrategyUtil strategyUtil = new StrategyUtil();

    @Override
    public <T> List<T> readAll(DataReadWriteSource data, Class<?> clazz) {
        Table table = convertToTable(data);
        return convertTableToListClasses(table, clazz);
    }

    @Override
    public <T> void writeAll(DataReadWriteSource data, List<T> objects) {
        strategyUtil.getWriterStrategyList().stream()
                .filter(writerStrategy -> writerStrategy.isApplied(data))
                .peek(writerStrategy -> writerStrategy.writeToSource(objects))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("The strategy doesn't exist "
                        + "for this data: " + data));

    }

    private <T> List<T> convertTableToListClasses(Table table, Class<?> clazz) {
        return IntStream.range(0, table.size())
                .boxed()
                .map(num -> (T) getFilledEntityByRow(clazz, table.getRowByIndex(num)))
                .collect(Collectors.toList());
    }

    private Object getFilledEntityByRow(Class<?> clazz, Map<String, String> rowByIndex) {
        Object instance = creatEntity(clazz);
        rowByIndex.entrySet()
                .forEach(entry -> fillEntity(instance, entry));
        return instance;
    }

    @SneakyThrows
    private void fillEntity(Object instance, Map.Entry<String, String> entry) {
        Field field = instance.getClass().getDeclaredField(entry.getKey());
        field.setAccessible(true);
        Object value = TypeFieldsUtil.getObject()
                .transformStringToTypeField(entry.getValue(), field);
        field.set(instance, value);
    }

    private Table convertToTable(DataReadWriteSource data) {
        return strategyUtil.getReaderStrategyList().stream()
                .filter(strategy -> strategy.isApplied(data))
                .map(strategy -> strategy.parseToTable(data))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("The strategy doesn't exist "
                        + "for this data: " + data));
    }

    @SneakyThrows
    private Object creatEntity(Class<?> clazz) {
        return clazz.getConstructor().newInstance();
    }
}
