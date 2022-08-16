package com.knubisoft.dao.impl;

import com.knubisoft.dao.PersonDao;
import com.knubisoft.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDaoImpl implements PersonDao {
    private final ConnectionUtil util = new ConnectionUtil();

    @Override
    public ResultSetMetaData findAll() {
        String query = "SELECT * FROM orm_db.persons";
        try (Connection connection = util.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getMetaData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
