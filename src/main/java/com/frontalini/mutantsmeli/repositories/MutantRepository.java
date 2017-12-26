package com.frontalini.mutantsmeli.repositories;

import com.frontalini.mutantsmeli.model.Dna;
import com.frontalini.mutantsmeli.model.Stats;
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

    @Override
    public void saveDna(Dna dna) {
        if(!existsDna(dna.toString())){
            try{
                Connection connection = getConnection();

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + TableDna.TABLE_NAME +
                                " (" + TableDna.COLUMN_NAME_DNA + ", " + TableDna.COLUMN_NAME_ISMUTANT + ") " +
                                " VALUES (?, ?)");
                statement.setString(1, dna.toString());
                statement.setInt(2, dna.getMutant());
                statement.executeUpdate();
                statement.close();
                connection.close();
            }catch(URISyntaxException | SQLException e){
                //TODO: Log error
            }
        }
    }

    @Override
    public Stats getStats() throws URISyntaxException, SQLException {
        Connection connection = createTableIfNotExists();
        Stats stats = new Stats();
        stats.setCountMutant(getCount(connection, 1));
        stats.setCountHuman(getCount(connection, 0));

        connection.close();
        return stats;
    }

    @Override
    public Boolean isMutant(String sequenceDna) {
        try{
            Connection connection = createTableIfNotExists();

            Boolean isMutant = null;
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + TableDna.COLUMN_NAME_ISMUTANT +
                            " FROM " + TableDna.TABLE_NAME +
                            " WHERE " + TableDna.COLUMN_NAME_DNA + " = ?");
            statement.setString(1, sequenceDna);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                isMutant = resultSet.getInt(TableDna.COLUMN_NAME_ISMUTANT) == 1;

            resultSet.close();
            statement.close();
            connection.close();
            return isMutant;
        }catch(URISyntaxException | SQLException e){
            return false;
        }
    }

    private Connection createTableIfNotExists() throws URISyntaxException, SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = TableDna.CREATE_TABLE;
        statement.executeUpdate(sql);
        statement.close();
        return connection;
    }

    private boolean existsDna(String dna) {
        return isMutant(dna) != null;
    }

    private int getCount(Connection connection, int isMutant) throws SQLException {
        int count = 0;
        PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) AS " + TableDna.ALIAS_COUNT +
                        " FROM " + TableDna.TABLE_NAME +
                        " WHERE " + TableDna.COLUMN_NAME_ISMUTANT + " = ?");
        statement.setInt(1, isMutant);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            count = resultSet.getInt(TableDna.ALIAS_COUNT);

        resultSet.close();
        statement.close();
        return count;
    }

    private class TableDna {

        static final String TABLE_NAME = "dnas";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_DNA = "dna";
        static final String COLUMN_NAME_ISMUTANT = "is_mutant";
        static final String ALIAS_COUNT = "count";
        static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " (" + COLUMN_NAME_ID + " SERIAL PRIMARY KEY, " +
                        " " + COLUMN_NAME_DNA + " TEXT, " +
                        " " + COLUMN_NAME_ISMUTANT + " INTEGER)";

    }

}
