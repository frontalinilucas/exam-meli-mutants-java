package com.frontalini.mutantsmeli.repositories;

import com.frontalini.mutantsmeli.model.Dna;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@Repository
public class MutantRepository implements GenericRepository {

    private Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private Connection createTableIfNotExists() throws URISyntaxException, SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = TableDna.CREATE_TABLE;
        statement.executeUpdate(sql);
        statement.close();
        return connection;
    }

    private boolean existsDna(String dna) throws URISyntaxException, SQLException {
        Connection connection = createTableIfNotExists();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 " +
                        " FROM " + TableDna.TABLE_NAME +
                        " WHERE " + TableDna.COLUMN_NAME_ADN + " = ?");
        statement.setString(1, dna);
        ResultSet resultSet = statement.executeQuery();
        boolean existsDna = resultSet.next();
        statement.close();
        connection.close();
        return existsDna;
    }

    @Override
    public void saveDna(Dna dna) throws URISyntaxException, SQLException {
        if(!existsDna(dna.toString())){
            Connection connection = getConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + TableDna.TABLE_NAME +
                    " (" + TableDna.COLUMN_NAME_ADN + ", " + TableDna.COLUMN_NAME_ISMUTANT + ") " +
                    " VALUES (?, ?)");
            statement.setString(1, dna.toString());
            statement.setInt(2, dna.getMutant());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }

    private class TableDna {

        static final String TABLE_NAME = "adns";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_ADN = "adn";
        static final String COLUMN_NAME_ISMUTANT = "is_mutant";
        static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (" + COLUMN_NAME_ID + " SERIAL PRIMARY KEY, " +
                        " " + COLUMN_NAME_ADN + " TEXT, " +
                        " " + COLUMN_NAME_ISMUTANT + " INTEGER)";

    }

}
