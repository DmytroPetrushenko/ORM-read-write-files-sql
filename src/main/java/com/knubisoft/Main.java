package com.knubisoft;

import com.knubisoft.dto.StringReadWriteSource;
import com.knubisoft.model.Person;
import com.knubisoft.service.ORM;
import com.knubisoft.service.ORMInterface;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        /*
        String path = Objects.requireNonNull(Main.class.getClassLoader().getResource("test.csv")).getPath();
        String fileContent = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        StringReadWriteSource source = new StringReadWriteSource();
        source.setContent(fileContent);
        ORMInterface orm = new ORM();
        List<Object> objects = orm.readAll(source, Person.class);
        objects.forEach(System.out::println);

        orm.writeAll(new StringReadWriteSource(), objects);
         */

        String path = Objects.requireNonNull(Main.class.getClassLoader().getResource("file.json")).getPath();
        String fileContent = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        StringReadWriteSource source = new StringReadWriteSource();
        source.setContent(fileContent);
        ORMInterface orm = new ORM();
        List<Object> objects = orm.readAll(source, Person.class);
        objects.forEach(System.out::println);
    }
}