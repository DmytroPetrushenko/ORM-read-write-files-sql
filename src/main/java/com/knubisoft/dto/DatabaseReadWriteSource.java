package com.knubisoft.dto;

import java.sql.ResultSetMetaData;
import lombok.Data;

@Data
public class DatabaseReadWriteSource implements DataReadWriteSource {
    private ResultSetMetaData content;
}
