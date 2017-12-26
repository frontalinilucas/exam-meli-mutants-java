package com.frontalini.mutantsmeli.database;

public class TableAdn {

    public static final String TABLE_NAME = "adns";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_ADN = "adn";
    public static final String COLUMN_NAME_ISMUTANT = "is_mutant";
    public static final String CREATE_TABLE =
                                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                                    " (" + COLUMN_NAME_ID + " SERIAL PRIMARY KEY, " +
                                    " " + COLUMN_NAME_ADN + " TEXT, " +
                                    " " + COLUMN_NAME_ISMUTANT + " INTEGER)";

}
