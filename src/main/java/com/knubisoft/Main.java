package com.knubisoft;

import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.model.Person;
import com.knubisoft.service.Orm;
import com.knubisoft.service.OrmInterface;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        OrmInterface orm = new Orm();

        String pathCsv = Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("test.csv")).getPath();
        String fileContentCsv = FileUtils
                .readFileToString(new File(pathCsv), StandardCharsets.UTF_8);
        StringReadWriteSource sourceCsv = new StringReadWriteSource();
        sourceCsv.setContent(fileContentCsv);
        List<Object> objectsCsv = orm.readAll(sourceCsv, Person.class);
        objectsCsv.forEach(System.out::println);

        orm.writeAll(new StringReadWriteSource(), objectsCsv);

        String pathJson = Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("file.json")).getPath();
        String fileContentJson = FileUtils
                .readFileToString(new File(pathJson), StandardCharsets.UTF_8);
        StringReadWriteSource sourceJson = new StringReadWriteSource();
        sourceJson.setContent(fileContentJson);
        List<Object> objectsJson = orm.readAll(sourceJson, Person.class);
        objectsJson.forEach(System.out::println);

        String pathXml = Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("fileXML.xml")).getPath();
        String fileContentXml = FileUtils
                .readFileToString(new File(pathXml), StandardCharsets.UTF_8);
        StringReadWriteSource sourceXml = new StringReadWriteSource();
        sourceXml.setContent(fileContentXml);
        List<Object> objectsXml = orm.readAll(sourceXml, Person.class);
        objectsXml.forEach(System.out::println);
    }
}
