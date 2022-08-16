package com.knubisoft.strategy;

import com.knubisoft.dto.DataReadWriteSource;
import java.util.List;

public interface WriterStrategy<T extends DataReadWriteSource> {

    boolean isApplied(DataReadWriteSource content);

    <V> void writeToSource(List<V> list);

}
