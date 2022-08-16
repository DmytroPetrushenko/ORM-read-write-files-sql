package com.knubisoft.util;

import com.knubisoft.strategy.ReaderStrategy;
import com.knubisoft.strategy.WriterStrategy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

@Getter
public class StrategyUtil {
    private static final String PACKAGE = "com.knubisoft";
    private static final Class<?> READER_STRATEGY = ReaderStrategy.class;
    private static final Class<?> WRITER_STRATEGY = WriterStrategy.class;
    private final List<? extends ReaderStrategy<?>> readerStrategyList;
    private final List<? extends WriterStrategy<?>> writerStrategyList;

    public StrategyUtil() {
        this.readerStrategyList =
                (List<? extends ReaderStrategy<?>>) getStrategyList(READER_STRATEGY);
        this.writerStrategyList =
                (List<? extends WriterStrategy<?>>) getStrategyList(WRITER_STRATEGY);
    }

    private <T> List<? extends T> getStrategyList(Class<T> strategy) {
        return new Reflections(PACKAGE).getSubTypesOf(strategy).stream()
                .map(this::creatEntity)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private <T> T creatEntity(Class<T> clazz) {
        return clazz.getConstructor().newInstance();
    }
}
