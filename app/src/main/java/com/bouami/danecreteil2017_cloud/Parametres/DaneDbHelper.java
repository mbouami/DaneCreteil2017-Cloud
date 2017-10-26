package com.bouami.danecreteil2017_cloud.Parametres;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract.*;

/**
 * Created by Mohammed on 05/10/2017.
 */

public class DaneDbHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = DaneDbHelper.class.getSimpleName();
    private static final String SQL_DELETE_DEPARTEMENTS_TABLE = "DROP TABLE IF EXISTS " + DepartementEntry.TABLE_NAME;
    private static final String SQL_DELETE_DISCIPLINES_TABLE = "DROP TABLE IF EXISTS " + DisciplineEntry.TABLE_NAME;
    private static final String SQL_DELETE_ANIMATEUR_TABLE = "DROP TABLE IF EXISTS " + AnimateurEntry.TABLE_NAME;
    private static final String SQL_DELETE_VILLE_TABLE = "DROP TABLE IF EXISTS " + VilleEntry.TABLE_NAME;
    private static final String SQL_DELETE_PERSONNEL_TABLE = "DROP TABLE IF EXISTS " + PersonnelEntry.TABLE_NAME;
    private static final String SQL_DELETE_ETABLISSEMENT_TABLE = "DROP TABLE IF EXISTS " + EtablissementEntry.TABLE_NAME;
    private static final String SQL_DELETE_REFERENTS_TABLE = "DROP TABLE IF EXISTS " + ReferentEntry.TABLE_NAME;

    static final String DATABASE_NAME = "danecreteil.db";

    DaneDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DaneContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_DEPARTEMENTS_TABLE = "CREATE TABLE " + DepartementEntry.TABLE_NAME + " (" +
                DepartementEntry._ID + " INTEGER PRIMARY KEY," +
                DepartementEntry.COLUMN_DEPARTEMENT_NOM + " TEXT NOT NULL, " +
                DepartementEntry.COLUMN_DEPARTEMENT_INTITULE + " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_DISCIPLINES_TABLE = "CREATE TABLE " + DisciplineEntry.TABLE_NAME + " (" +
                DisciplineEntry._ID + " INTEGER PRIMARY KEY," +
                DisciplineEntry.COLUMN_DISCIPLINE_NOM + " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_ANIMATEUR_TABLE = "CREATE TABLE " + AnimateurEntry.TABLE_NAME + " (" +
                AnimateurEntry._ID + " INTEGER PRIMARY KEY," +
                AnimateurEntry.COLUMN_NOM + " TEXT NOT NULL, " +
                AnimateurEntry.COLUMN_TEL + " TEXT NOT NULL, " +
                AnimateurEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                AnimateurEntry.COLUMN_PHOTO + " BLOB, " +
                AnimateurEntry.COLUMN_DEPARTEMENT_ID + " INTEGER NOT NULL, " +
                AnimateurEntry.COLUMN_ANIMATEUR_ID + " INTEGER NOT NULL " +
                " );";

        final String SQL_CREATE_VILLES_TABLE = "CREATE TABLE " + VilleEntry.TABLE_NAME + " (" +
                VilleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VilleEntry.COLUMN_VILLE_NOM + " TEXT NOT NULL, " +
                VilleEntry.COLUMN_DEPARTEMENT_ID + " INTEGER NOT NULL, " +
                VilleEntry.COLUMN_VILLE_CP + " TEXT, " +
                " FOREIGN KEY (" + VilleEntry.COLUMN_DEPARTEMENT_ID + ") REFERENCES " +
                DepartementEntry.TABLE_NAME + " (" + DepartementEntry._ID + ") " +
                " );";

        final String SQL_CREATE_ETABLISSEMENTS_TABLE = "CREATE TABLE " + EtablissementEntry.TABLE_NAME + " (" +
                EtablissementEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EtablissementEntry.COLUMN_NOM + " TEXT NOT NULL, " +
                EtablissementEntry.COLUMN_ETABLISSEMENT_ID + " INTEGER NOT NULL, " +
                EtablissementEntry.COLUMN_VILLE_ID + " INTEGER NOT NULL, " +
                EtablissementEntry.COLUMN_ANIMATEUR_ID + " INTEGER, " +
                EtablissementEntry.COLUMN_TEL + " TEXT, " +
                EtablissementEntry.COLUMN_FAX + " TEXT, " +
                EtablissementEntry.COLUMN_EMAIL + " TEXT, " +
                EtablissementEntry.COLUMN_RNE + " TEXT, " +
                EtablissementEntry.COLUMN_ADRESSE + " TEXT, " +
                EtablissementEntry.COLUMN_TYPE + " TEXT, " +
                " FOREIGN KEY (" + EtablissementEntry.COLUMN_VILLE_ID + ") REFERENCES " +
                VilleEntry.TABLE_NAME + " (" + VilleEntry._ID + ") " +
                " FOREIGN KEY (" + EtablissementEntry.COLUMN_ANIMATEUR_ID + ") REFERENCES " +
                AnimateurEntry.TABLE_NAME + " (" + AnimateurEntry._ID + ") " +
                " );";

        final String SQL_CREATE_PERSONNEL_TABLE = "CREATE TABLE " + PersonnelEntry.TABLE_NAME + " (" +
                PersonnelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PersonnelEntry.COLUMN_NOM + " TEXT NOT NULL, " +
                ReferentEntry.COLUMN_TEL + " TEXT, " +
                ReferentEntry.COLUMN_EMAIL + " TEXT, " +
                PersonnelEntry.COLUMN_STATUT + " TEXT NOT NULL, " +
                PersonnelEntry.COLUMN_ETABLISSEMENT_ID + " INTEGER NOT NULL, " +
                PersonnelEntry.COLUMN_SYNCHRONISER + " BOOLEAN, " +
                PersonnelEntry.COLUMN_PERSONNEL_ID + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + PersonnelEntry.COLUMN_ETABLISSEMENT_ID + ") REFERENCES " +
                EtablissementEntry.TABLE_NAME + " (" + EtablissementEntry._ID + ") " +
                " );";

        final String SQL_CREATE_REFERENTS_TABLE = "CREATE TABLE " + ReferentEntry.TABLE_NAME + " (" +
                ReferentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReferentEntry.COLUMN_NOM + " TEXT NOT NULL, " +
                ReferentEntry.COLUMN_TEL + " TEXT, " +
                ReferentEntry.COLUMN_EMAIL + " TEXT, " +
                ReferentEntry.COLUMN_STATUT + " TEXT NOT NULL, " +
                ReferentEntry.COLUMN_ETABLISSEMENT_ID + " INTEGER NOT NULL, " +
                ReferentEntry.COLUMN_REFERENT_ID + " INTEGER NOT NULL, " +
                ReferentEntry.COLUMN_DISCIPLINE_ID + " INTEGER, " +
                ReferentEntry.COLUMN_SYNCHRONISER + " BOOLEAN, " +
                " FOREIGN KEY (" + ReferentEntry.COLUMN_ETABLISSEMENT_ID + ") REFERENCES " +
                EtablissementEntry.TABLE_NAME + " (" + EtablissementEntry._ID + ") " +
                " FOREIGN KEY (" + ReferentEntry.COLUMN_DISCIPLINE_ID + ") REFERENCES " +
                DisciplineEntry.TABLE_NAME + " (" + DisciplineEntry._ID + ") " +

                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_DEPARTEMENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DISCIPLINES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ANIMATEUR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_VILLES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ETABLISSEMENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PERSONNEL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REFERENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ANIMATEUR_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_VILLE_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_PERSONNEL_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_ETABLISSEMENT_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_REFERENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_DEPARTEMENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_DISCIPLINES_TABLE);
        onCreate(sqLiteDatabase);
    }
}
