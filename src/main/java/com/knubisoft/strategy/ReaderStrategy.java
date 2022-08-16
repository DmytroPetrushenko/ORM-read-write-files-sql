package com.knubisoft.strategy;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.Table;

public interface ReaderStrategy<T extends DataReadWriteSource> {

    boolean isApplied(DataReadWriteSource data);

    Table parseToTable(DataReadWriteSource data);
}
