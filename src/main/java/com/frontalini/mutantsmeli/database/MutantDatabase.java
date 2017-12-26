package com.frontalini.mutantsmeli.database;

import com.frontalini.mutantsmeli.model.Dna;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class MutantDatabase {

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static Connection createTableIfNotExists() throws URISyntaxException, SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = TableAdn.CREATE_TABLE;
        statement.executeUpdate(sql);
        statement.close();
        return connection;
    }

    private static boolean existsDna(String dna) throws URISyntaxException, SQLException {
        Connection connection = createTableIfNotExists();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 " +
                        " FROM " + TableAdn.TABLE_NAME +
                        " WHERE " + TableAdn.COLUMN_NAME_ADN + " = ?");
        statement.setString(1, dna);
        ResultSet resultSet = statement.executeQuery();
        boolean existsDna = resultSet.next();
        statement.close();
        connection.close();
        return existsDna;
    }

    public static void saveDna(Dna dna) throws URISyntaxException, SQLException {
        if(!existsDna(dna.toString())){
            Connection connection = getConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + TableAdn.TABLE_NAME +
                    " (" + TableAdn.COLUMN_NAME_ADN + ", " + TableAdn.COLUMN_NAME_ISMUTANT + ") " +
                    " VALUES (?, ?)");
            statement.setString(1, dna.toString());
            statement.setInt(2, dna.getMutant());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }

}
