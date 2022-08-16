package com.knubisoft.service;

import com.knubisoft.dto.DataReadWriteSource;
import java.util.List;

public interface OrmInterface {
    <T> List<T> readAll(DataReadWriteSource content, Class<?> clazz);

    <T> void writeAll(DataReadWriteSource content, List<T> objects);
}
