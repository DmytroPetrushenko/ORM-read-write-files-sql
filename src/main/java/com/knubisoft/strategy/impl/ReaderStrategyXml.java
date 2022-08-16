package com.knubisoft.strategy.impl;

import com.knubisoft.dto.DataReadWriteSource;
import com.knubisoft.dto.Table;
import com.knubisoft.strategy.ReaderStrategy;

public class ReaderStrategyXml<T extends DataReadWriteSource> implements ReaderStrategy<T> {
    private final static String PATTERN = "\\<\\?xml.+\\>\\<\\w+s\\>.*\\<\\/\\w+s\\>";

    @Override
    public boolean isApplied(DataReadWriteSource content) {
        return false;
    }

    @Override
    public Table parseToTable(DataReadWriteSource content) {
        return null;
    }
}
