package com.knubisoft.dto;

import lombok.Data;

@Data
public class StringReadWriteSource implements DataReadWriteSource {
    private String content;
}
