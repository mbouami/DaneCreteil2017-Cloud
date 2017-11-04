package com.bouami.danecreteil2017_cloud.Parametres;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract.*;

import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.CONTENT_AUTHORITY;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_ANIMATEURS;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_CIVILITES;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_DEPARTEMENTS;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_DISCIPLINES;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_ETABLISSEMENTS;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_PERSONNEL;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_REFERENTS;
import static com.bouami.danecreteil2017_cloud.Parametres.DaneContract.PATH_VILLES;

/**
 * Created by Mohammed on 05/10/2017.
 */


public class DaneProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DaneDbHelper mDaneHelper;

    static final int VILLES = 100*DaneContract.NUM_VERSION_SQLITE;
    static final int VILLES_PAR_DEPARTEMENT = VILLES+1;

    static final int ANIMATEURS = 200*DaneContract.NUM_VERSION_SQLITE;
    static final int ANIMATEURS_ID = ANIMATEURS+1;
    static final int ANIMATEURS_CONTENANT_NOM = ANIMATEURS+2;
    static final int ANIMATEURS_PAR_DEPARTEMENT_CONTENANT_NOM = ANIMATEURS+3;
    static final int ANIMATEURS_PAR_DEPARTEMENT = ANIMATEURS+4;

    static final int ETABLISSEMENTS = 300*DaneContract.NUM_VERSION_SQLITE;
    static final int ETABLISSEMENTS_PAR_VILLE = ETABLISSEMENTS+1;
    static final int ETABLISSEMENTS_ID = ETABLISSEMENTS+2;
    static final int ETABLISSEMENTS_CONTENANT_NOM = ETABLISSEMENTS+3;
    static final int ETABLISSEMENTS_PAR_ANIMATEUR = ETABLISSEMENTS+4;
    static final int ETABLISSEMENTS_PAR_DEPARTEMENT = ETABLISSEMENTS+5;
    static final int ETABLISSEMENTS_PAR_DEPARTEMENT_CONTENANT_NOM  = ETABLISSEMENTS+6;

    static final int PERSONNEL = 400*DaneContract.NUM_VERSION_SQLITE;
    static final int PERSONNEL_PAR_ETAB = PERSONNEL+1;
    static final int PERSONNEL_PAR_ID = PERSONNEL+2;
    static final int PERSONNEL_CONTENANT_NOM = PERSONNEL+3;
    static final int PERSONNEL_PAR_DEPARTEMENT = PERSONNEL+4;
    static final int PERSONNEL_PAR_DEPARTEMENT_CONTENANT_NOM  = PERSONNEL+5;

    static final int DEPARTEMENTS = 500*DaneContract.NUM_VERSION_SQLITE;
    static final int DEPARTEMENT_ID = DEPARTEMENTS+1;
    static final int DEPARTEMENT_PAR_NOM = DEPARTEMENTS+2;

    static final int REFERENT = 600*DaneContract.NUM_VERSION_SQLITE;
    static final int REFERENT_PAR_ETAB = REFERENT+1;
    static final int REFERENT_PAR_ID = REFERENT+2;
    static final int REFERENT_CONTENANT_NOM = REFERENT+3;
    static final int REFERENTS_PAR_DEPARTEMENT = REFERENT+4;
    static final int REFERENTS_PAR_DEPARTEMENT_CONTENANT_NOM = REFERENT+5;

    static final int DISCIPLINES = 700*DaneContract.NUM_VERSION_SQLITE;
    static final int DISCIPLINE_ID = DISCIPLINES+1;
    static final int DISCIPLINE_PAR_NOM = DISCIPLINES+2;
    static final int DISCIPLINES_CONTENANT_NOM = DISCIPLINES+3;

    static final int CIVILITES = 800*DaneContract.NUM_VERSION_SQLITE;
    static final int CIVILITE_ID = CIVILITES+1;
    static final int CIVILITE_PAR_NOM = CIVILITES+2;

    private static final SQLiteQueryBuilder sAnimateurParDepartementQueryBuilder;
    static{
        sAnimateurParDepartementQueryBuilder = new SQLiteQueryBuilder();
        sAnimateurParDepartementQueryBuilder.setTables(
                AnimateurEntry.TABLE_NAME + " LEFT JOIN " +
                        DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry.COLUMN_CIVILITE_ID
        );
    }

    private static final SQLiteQueryBuilder sEtablissementsParVilleQueryBuilder;
    static{
        sEtablissementsParVilleQueryBuilder = new SQLiteQueryBuilder();


        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sEtablissementsParVilleQueryBuilder.setTables(
                EtablissementEntry.TABLE_NAME + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  + " LEFT JOIN " +
                        AnimateurEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_ANIMATEUR_ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry._ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry.COLUMN_CIVILITE_ID
        );
    }

    private static final SQLiteQueryBuilder sEtablissementsParDepartementQueryBuilder;
    static{
        sEtablissementsParDepartementQueryBuilder = new SQLiteQueryBuilder();
        sEtablissementsParDepartementQueryBuilder.setTables(
                EtablissementEntry.TABLE_NAME + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + AnimateurEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_ANIMATEUR_ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry._ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry.COLUMN_CIVILITE_ID

        );
    }

    private static final SQLiteQueryBuilder sPersonnelParEtablissementQueryBuilder;
    static{
        sPersonnelParEtablissementQueryBuilder = new SQLiteQueryBuilder();
        sPersonnelParEtablissementQueryBuilder.setTables(
                PersonnelEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_CIVILITE_ID
        );
    }

    private static final SQLiteQueryBuilder sPersonnelParDepartementQueryBuilder;
    static{
        sPersonnelParDepartementQueryBuilder = new SQLiteQueryBuilder();
        sPersonnelParDepartementQueryBuilder.setTables(
                PersonnelEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_CIVILITE_ID
        );
    }

    private static final SQLiteQueryBuilder sReferentsParEtablissementQueryBuilder;
    static{
        sReferentsParEtablissementQueryBuilder = new SQLiteQueryBuilder();
        sReferentsParEtablissementQueryBuilder.setTables(
                ReferentEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + DisciplineEntry.TABLE_NAME +
                        " ON " + DisciplineEntry.TABLE_NAME +
                        "." + DisciplineEntry._ID +
                        " = " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_DISCIPLINE_ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_CIVILITE_ID
        );
    }

    private static final SQLiteQueryBuilder sVillesParDepartementQueryBuilder;
    static{
        sVillesParDepartementQueryBuilder = new SQLiteQueryBuilder();
        sVillesParDepartementQueryBuilder.setTables(VilleEntry.TABLE_NAME);
    }

    private static final SQLiteQueryBuilder sEtablissementParIdQueryBuilder;
    static{
        sEtablissementParIdQueryBuilder = new SQLiteQueryBuilder();
        sEtablissementParIdQueryBuilder.setTables(EtablissementEntry.TABLE_NAME);
    }

    private static final SQLiteQueryBuilder sAnimateurQueryBuilder;
    static{
        sAnimateurQueryBuilder = new SQLiteQueryBuilder();
        sAnimateurQueryBuilder.setTables(AnimateurEntry.TABLE_NAME);
    }

    private static final SQLiteQueryBuilder sEtablissementQueryBuilder;
    static{
        sEtablissementQueryBuilder = new SQLiteQueryBuilder();
        sEtablissementQueryBuilder.setTables(
                EtablissementEntry.TABLE_NAME + " LEFT JOIN " +
                        AnimateurEntry.TABLE_NAME +
                        " ON " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry._ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_ANIMATEUR_ID +
                        " LEFT JOIN " + VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID
        );
    }

    private static final SQLiteQueryBuilder sPersonnelQueryBuilder;
    static{
        sPersonnelQueryBuilder = new SQLiteQueryBuilder();
        sPersonnelQueryBuilder.setTables(
                PersonnelEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  + " LEFT JOIN " +
                        AnimateurEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_ANIMATEUR_ID +
                        " = " + AnimateurEntry.TABLE_NAME +
                        "." + AnimateurEntry._ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + PersonnelEntry.TABLE_NAME +
                        "." + PersonnelEntry.COLUMN_CIVILITE_ID
        );
    };

    private  static final SQLiteQueryBuilder sDepartementQueryBuilder;
    static {
        sDepartementQueryBuilder = new SQLiteQueryBuilder();
        sDepartementQueryBuilder.setTables(DepartementEntry.TABLE_NAME);
    }

    private  static final SQLiteQueryBuilder sCiviliteQueryBuilder;
    static {
        sCiviliteQueryBuilder = new SQLiteQueryBuilder();
        sCiviliteQueryBuilder.setTables(CiviliteEntry.TABLE_NAME);
    }

    private static final SQLiteQueryBuilder sReferentQueryBuilder;
    static{
        sReferentQueryBuilder = new SQLiteQueryBuilder();
        sReferentQueryBuilder.setTables(
                ReferentEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_CIVILITE_ID
        );
    };

    private static final SQLiteQueryBuilder sDisciplinesQueryBuilder;
    static{
        sDisciplinesQueryBuilder = new SQLiteQueryBuilder();
        sDisciplinesQueryBuilder.setTables(
                DisciplineEntry.TABLE_NAME
        );
    };

    private static final SQLiteQueryBuilder sReferentsParDepartementQueryBuilder;
    static{
        sReferentsParDepartementQueryBuilder = new SQLiteQueryBuilder();
        sReferentsParDepartementQueryBuilder.setTables(
                ReferentEntry.TABLE_NAME + " LEFT JOIN " +
                        EtablissementEntry.TABLE_NAME +
                        " ON " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_ETABLISSEMENT_ID +
                        " = " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry._ID + " LEFT JOIN " +
                        VilleEntry.TABLE_NAME +
                        " ON " + EtablissementEntry.TABLE_NAME +
                        "." + EtablissementEntry.COLUMN_VILLE_ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry._ID  +
                        " LEFT JOIN " + DepartementEntry.TABLE_NAME +
                        " ON " + DepartementEntry.TABLE_NAME +
                        "." + DepartementEntry._ID +
                        " = " + VilleEntry.TABLE_NAME +
                        "." + VilleEntry.COLUMN_DEPARTEMENT_ID +
                        " LEFT JOIN " + DisciplineEntry.TABLE_NAME +
                        " ON " + DisciplineEntry.TABLE_NAME +
                        "." + DisciplineEntry._ID +
                        " = " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_DISCIPLINE_ID +
                        " LEFT JOIN " + CiviliteEntry.TABLE_NAME +
                        " ON " + CiviliteEntry.TABLE_NAME +
                        "." + CiviliteEntry._ID +
                        " = " + ReferentEntry.TABLE_NAME +
                        "." + ReferentEntry.COLUMN_CIVILITE_ID
        );
    }


    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PATH_VILLES, VILLES);
        matcher.addURI(authority, PATH_VILLES+ "/*", VILLES_PAR_DEPARTEMENT);

        matcher.addURI(authority, PATH_DEPARTEMENTS, DEPARTEMENTS);
        matcher.addURI(authority, PATH_DEPARTEMENTS+ "/*", DEPARTEMENT_ID);
        matcher.addURI(authority, PATH_DEPARTEMENTS+ "/*/nom", DEPARTEMENT_PAR_NOM);

        matcher.addURI(authority, PATH_CIVILITES, CIVILITES);
        matcher.addURI(authority, PATH_CIVILITES+ "/*", CIVILITE_ID);
        matcher.addURI(authority, PATH_CIVILITES+ "/*/nom", CIVILITE_PAR_NOM);

        matcher.addURI(authority, PATH_DISCIPLINES, DISCIPLINES);
        matcher.addURI(authority, PATH_DISCIPLINES+ "/*", DISCIPLINE_ID);
        matcher.addURI(authority, PATH_DISCIPLINES+ "/*/nom", DISCIPLINE_PAR_NOM);
        matcher.addURI(authority, PATH_DISCIPLINES+ "/*/rechercher", DISCIPLINES_CONTENANT_NOM);

        matcher.addURI(authority, PATH_ANIMATEURS, ANIMATEURS);
        matcher.addURI(authority, PATH_ANIMATEURS+ "/*", ANIMATEURS_ID);
        matcher.addURI(authority, PATH_ANIMATEURS+ "/*/depart", ANIMATEURS_PAR_DEPARTEMENT);
        matcher.addURI(authority, PATH_ANIMATEURS+ "/*/rechercher", ANIMATEURS_CONTENANT_NOM);
        matcher.addURI(authority, PATH_ANIMATEURS+ "/*/etab", ETABLISSEMENTS_PAR_ANIMATEUR);
        matcher.addURI(authority, PATH_ANIMATEURS+ "/*/*/rechercher", ANIMATEURS_PAR_DEPARTEMENT_CONTENANT_NOM);

        matcher.addURI(authority, PATH_ETABLISSEMENTS, ETABLISSEMENTS);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*", ETABLISSEMENTS_ID);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*/ville", ETABLISSEMENTS_PAR_VILLE);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*/depart", ETABLISSEMENTS_PAR_DEPARTEMENT);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*/etab", ETABLISSEMENTS_ID);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*/rechercher", ETABLISSEMENTS_CONTENANT_NOM);
        matcher.addURI(authority, PATH_ETABLISSEMENTS+ "/*/*/rechercher", ETABLISSEMENTS_PAR_DEPARTEMENT_CONTENANT_NOM);

        matcher.addURI(authority, PATH_PERSONNEL, PERSONNEL);
        matcher.addURI(authority, PATH_PERSONNEL+ "/*", PERSONNEL_PAR_ID);
        matcher.addURI(authority, PATH_PERSONNEL+ "/*/personnel", PERSONNEL_PAR_DEPARTEMENT);
        matcher.addURI(authority, PATH_PERSONNEL+ "/*/etab", PERSONNEL_PAR_ETAB);
        matcher.addURI(authority, PATH_PERSONNEL+ "/*/rechercher", PERSONNEL_CONTENANT_NOM);
        matcher.addURI(authority, PATH_PERSONNEL+ "/*/*/rechercher", PERSONNEL_PAR_DEPARTEMENT_CONTENANT_NOM);

        matcher.addURI(authority, PATH_REFERENTS, REFERENT);
        matcher.addURI(authority, PATH_REFERENTS+ "/*", REFERENT_PAR_ID);
        matcher.addURI(authority, PATH_REFERENTS+ "/*/referent", REFERENTS_PAR_DEPARTEMENT);
        matcher.addURI(authority, PATH_REFERENTS+ "/*/etab", REFERENT_PAR_ETAB);
        matcher.addURI(authority, PATH_REFERENTS+ "/*/rechercher", REFERENT_CONTENANT_NOM);
        matcher.addURI(authority, PATH_REFERENTS+ "/*/*/rechercher", REFERENTS_PAR_DEPARTEMENT_CONTENANT_NOM);

        return matcher;
    }

    private static final String sDisciplinesParNomSelection =
            DisciplineEntry.TABLE_NAME+
                    "." + DisciplineEntry.COLUMN_DISCIPLINE_NOM + " = ? ";

    private static final String sVillesParDepartementSelection =
            VilleEntry.TABLE_NAME+
                    "." + VilleEntry.COLUMN_DEPARTEMENT_ID + " = ? ";

    private static final String sAnimateurParIdSelection =
            AnimateurEntry.TABLE_NAME+
                    "." + AnimateurEntry._ID + " = ? ";

    private static final String sEtablissementParIdSelection =
            EtablissementEntry.TABLE_NAME+
                    "." + EtablissementEntry._ID + " = ? ";

    private static final String sEtablissementParDepartementSelection =
            DepartementEntry.TABLE_NAME+
                    "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " = ? ";

    private static final String sDepartementParIdSelection =
            DepartementEntry.TABLE_NAME+
                    "." + DepartementEntry._ID + " = ? ";

    private static final String sReferentsParIdSelection =
            ReferentEntry.TABLE_NAME+
                    "." + ReferentEntry._ID + " = ? ";

    private static final String sCiviliteParIdSelection =
            CiviliteEntry.TABLE_NAME +
                    "." + CiviliteEntry._ID + " = ? ";

    private static final String sDepartementParNomSelection =
            DepartementEntry.TABLE_NAME+
                    "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " = ? ";

    private static final String sEtablissementParIdAnimateurSelection =
            EtablissementEntry.TABLE_NAME+
                    "." + EtablissementEntry.COLUMN_ANIMATEUR_ID+ " = ? ";

    private static final String sEtablissementParNomSelection =
            EtablissementEntry.TABLE_NAME+
                    "." + EtablissementEntry.COLUMN_NOM + " like ? ";

    private static final String sAnimateursParNomSelection =
            AnimateurEntry.TABLE_NAME+
                    "." + AnimateurEntry.COLUMN_NOM + " like ? ";

    private static final String sPersonnelParNomSelection =
            PersonnelEntry.TABLE_NAME+
                    "." + PersonnelEntry.COLUMN_NOM + " like ? ";

    private static final String sPersonnelParDepartementSelection =
            DepartementEntry.TABLE_NAME+
                    "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " = ? ";

    private static final String sPersonnelParEtabSelection =
            PersonnelEntry.TABLE_NAME+
                    "." + PersonnelEntry.COLUMN_ETABLISSEMENT_ID + " = ? ";

    private static final String sReferentsParNomSelection =
            ReferentEntry.TABLE_NAME+
                    "." + ReferentEntry.COLUMN_NOM + " like ? ";

    private static final String sReferentsParEtabSelection =
            ReferentEntry.TABLE_NAME+
                    "." + ReferentEntry.COLUMN_ETABLISSEMENT_ID + " = ? ";

    private static final String sPersonnelsParIdSelection =
            PersonnelEntry.TABLE_NAME+
                    "." + PersonnelEntry._ID + " = ? ";

    private static final String sReferentsParDepartementSelection =
            DepartementEntry.TABLE_NAME+
                    "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " = ? ";

    private Cursor getVillesParDepartement(Uri uri, String[] projection, String sortOrder) {
        String iddepartement = VilleEntry.getDepartementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{iddepartement};
        selection = sVillesParDepartementSelection;
        return sVillesParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private static final String sEtablissementsParVilleSelection =
            EtablissementEntry.TABLE_NAME+
                    "." + EtablissementEntry.COLUMN_VILLE_ID + " = ? ";

    private Cursor getAnimateursParDepartementContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sAnimateurParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtablissementsParVille(Uri uri, String[] projection, String sortOrder) {
//        String ville = EtablissementEntry.getVilleFromUri(uri);
        String ville = EtablissementEntry.getVilleFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{ville};
        selection = sEtablissementsParVilleSelection;
        return sEtablissementsParVilleQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtablissementsParId(Uri uri, String[] projection, String sortOrder) {
        String idetab = EtablissementEntry.getEtablissementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idetab};
        selection = sEtablissementParIdSelection;
        return sEtablissementsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getDepartenetParId(Uri uri, String[] projection, String sortOrder) {
        String iddepart = DepartementEntry.getDepartementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{iddepart};
        selection = sDepartementParIdSelection;
        return sDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCiviliteParId(Uri uri, String[] projection, String sortOrder) {
        String idcivilite = CiviliteEntry.getCiviliteFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idcivilite};
        selection = sCiviliteParIdSelection;
        return sCiviliteQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getDepartenetParNom(Uri uri, String[] projection, String sortOrder) {
        String nomdepart = DepartementEntry.getDepartementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{nomdepart};
        selection = sDepartementParNomSelection;
        return sDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtablissementsParAnimateur(Uri uri, String[] projection, String sortOrder) {
        String idanim = AnimateurEntry.getIdAnimateurFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idanim};
        selection = sEtablissementParIdAnimateurSelection;
        return sEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtablissementsContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtablissementsParDepartementContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sEtablissementsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getAnimateurParId(Uri uri, String[] projection, String sortOrder) {
        String idetab = AnimateurEntry.getIdAnimateurFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idetab};
        selection = sAnimateurParIdSelection;
        return sAnimateurParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getAnimateursParDepartement(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sAnimateurParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getAnimateursContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sAnimateurQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPersonnelParDepartementContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sPersonnelParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getPersonnelParId(Uri uri, String[] projection, String sortOrder) {
        String idpersonnel = PersonnelEntry.getPersonnelFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idpersonnel};
        selection = sPersonnelsParIdSelection;
        return sPersonnelParEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getPersonnelContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sPersonnelQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPersonnelParEtab(Uri uri, String[] projection, String sortOrder) {
        String idetab = PersonnelEntry.getEtablissementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idetab};
        selection = sPersonnelParEtabSelection;
        return sPersonnelParEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getDisciplinesContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sDisciplinesQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getReferentsContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sReferentQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getReferentParId(Uri uri, String[] projection, String sortOrder) {
        String idreferent = ReferentEntry.getReferentFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idreferent};
        selection = sReferentsParIdSelection;
        return sReferentsParEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getReferentsParEtab(Uri uri, String[] projection, String sortOrder) {
        String idetab = ReferentEntry.getEtablissementFromUri(uri);
        String[] selectionArgs;
        String selection;
        selectionArgs = new String[]{idetab};
        selection = sReferentsParEtabSelection;
        return sReferentsParEtablissementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getReferentsParDepartement(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sReferentsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getReferentsParDepartementContenantleNom(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sReferentsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEtabsParDepartement(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sEtablissementsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPersonnelParDepartement(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        return sPersonnelParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public boolean onCreate() {
        mDaneHelper = new DaneDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case VILLES_PAR_DEPARTEMENT:
                return VilleEntry.CONTENT_ITEM_TYPE;
            case VILLES:
                return VilleEntry.CONTENT_TYPE;
            case ANIMATEURS:
                return AnimateurEntry.CONTENT_TYPE;
            case ANIMATEURS_ID:
                return AnimateurEntry.CONTENT_ITEM_TYPE;
            case DEPARTEMENTS:
                return DepartementEntry.CONTENT_TYPE;
            case CIVILITES:
                return CiviliteEntry.CONTENT_TYPE;
            case DEPARTEMENT_ID:
                return DepartementEntry.CONTENT_ITEM_TYPE;
            case DEPARTEMENT_PAR_NOM:
                return DepartementEntry.CONTENT_ITEM_TYPE;
            case DISCIPLINES:
                return DisciplineEntry.CONTENT_TYPE;
            case DISCIPLINE_ID:
                return DisciplineEntry.CONTENT_ITEM_TYPE;
            case DISCIPLINE_PAR_NOM:
                return DisciplineEntry.CONTENT_ITEM_TYPE;
            case DISCIPLINES_CONTENANT_NOM:
                return AnimateurEntry.CONTENT_ITEM_TYPE;
            case ANIMATEURS_CONTENANT_NOM:
                return AnimateurEntry.CONTENT_ITEM_TYPE;
            case ETABLISSEMENTS_PAR_ANIMATEUR:
                return AnimateurEntry.CONTENT_ITEM_TYPE;
            case ETABLISSEMENTS:
                return EtablissementEntry.CONTENT_TYPE;
            case ETABLISSEMENTS_PAR_VILLE:
                return EtablissementEntry.CONTENT_ITEM_TYPE;
            case ETABLISSEMENTS_PAR_DEPARTEMENT:
                return EtablissementEntry.CONTENT_ITEM_TYPE;
            case ETABLISSEMENTS_ID:
                return EtablissementEntry.CONTENT_ITEM_TYPE;
            case ETABLISSEMENTS_CONTENANT_NOM:
                return EtablissementEntry.CONTENT_ITEM_TYPE;
            case PERSONNEL:
                return PersonnelEntry.CONTENT_TYPE;
            case PERSONNEL_PAR_ID:
                return PersonnelEntry.CONTENT_ITEM_TYPE;
            case PERSONNEL_PAR_ETAB:
                return PersonnelEntry.CONTENT_ITEM_TYPE;
            case PERSONNEL_CONTENANT_NOM:
                return PersonnelEntry.CONTENT_ITEM_TYPE;
            case REFERENT:
                return ReferentEntry.CONTENT_TYPE;
            case REFERENT_PAR_ID:
                return ReferentEntry.CONTENT_ITEM_TYPE;
            case REFERENT_PAR_ETAB:
                return ReferentEntry.CONTENT_ITEM_TYPE;
            case REFERENT_CONTENANT_NOM:
                return ReferentEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("uri inconnue: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ANIMATEURS_PAR_DEPARTEMENT_CONTENANT_NOM: {
                retCursor = getAnimateursParDepartementContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case ANIMATEURS_PAR_DEPARTEMENT: {
                retCursor = getAnimateursParDepartement(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case VILLES_PAR_DEPARTEMENT:
            {
                retCursor = getVillesParDepartement(uri, projection, sortOrder);
                break;
            }
            case ETABLISSEMENTS_PAR_VILLE: {
                retCursor = getEtablissementsParVille(uri, projection, sortOrder);
                break;
            }
            case ETABLISSEMENTS_PAR_DEPARTEMENT: {
                retCursor = getEtabsParDepartement(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case ETABLISSEMENTS_ID: {
                retCursor = getEtablissementsParId(uri, projection, sortOrder);
                break;
            }

            case ANIMATEURS_ID: {
                retCursor = getAnimateurParId(uri, projection, sortOrder);
                break;
            }

            case DEPARTEMENT_ID: {
                retCursor = getDepartenetParId(uri, projection, sortOrder);
                break;
            }

            case CIVILITE_ID: {
                retCursor = getCiviliteParId(uri, projection, sortOrder);
                break;
            }
            case DEPARTEMENT_PAR_NOM: {
                retCursor = getDepartenetParNom(uri, projection, sortOrder);
                break;
            }

            case ETABLISSEMENTS_CONTENANT_NOM: {
                retCursor = getEtablissementsContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }

            case ETABLISSEMENTS_PAR_DEPARTEMENT_CONTENANT_NOM: {
                retCursor = getEtablissementsParDepartementContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }

            case PERSONNEL_PAR_ETAB: {
                retCursor = getPersonnelParEtab(uri, projection, sortOrder);
                break;
            }

            case PERSONNEL_PAR_DEPARTEMENT: {
                retCursor = getPersonnelParDepartement(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case PERSONNEL_CONTENANT_NOM: {
                retCursor = getPersonnelContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }

            case PERSONNEL_PAR_DEPARTEMENT_CONTENANT_NOM: {
                retCursor = getPersonnelParDepartementContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case PERSONNEL_PAR_ID: {
                retCursor = getPersonnelParId(uri, projection, sortOrder);
                break;
            }
            case REFERENT_PAR_ID: {
                retCursor = getReferentParId(uri, projection, sortOrder);
                break;
            }

            case REFERENT_PAR_ETAB: {
                retCursor = getReferentsParEtab(uri, projection, sortOrder);
                break;
            }
            case REFERENT_CONTENANT_NOM: {
                retCursor = getReferentsContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case REFERENTS_PAR_DEPARTEMENT_CONTENANT_NOM: {
                retCursor = getReferentsParDepartementContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case REFERENTS_PAR_DEPARTEMENT: {
                retCursor = getReferentsParDepartement(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }

            case ANIMATEURS_CONTENANT_NOM: {
                retCursor = getAnimateursContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }
            case ETABLISSEMENTS_PAR_ANIMATEUR: {
                retCursor = getEtablissementsParAnimateur(uri, projection, sortOrder);
                break;
            }

            case DISCIPLINES_CONTENANT_NOM: {
                retCursor = getDisciplinesContenantleNom(uri, projection,selection,selectionArgs, sortOrder);
                break;
            }

            case DEPARTEMENTS: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        DepartementEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CIVILITES: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        CiviliteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case DISCIPLINES: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        DisciplineEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case VILLES: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        VilleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PERSONNEL: {
                retCursor = sPersonnelParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case REFERENT: {
//                retCursor = mDaneHelper.getReadableDatabase().query(
//                        ReferentEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
                retCursor = sReferentsParDepartementQueryBuilder.query(mDaneHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case ANIMATEURS: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        AnimateurEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            }
            case ETABLISSEMENTS: {
                retCursor = mDaneHelper.getReadableDatabase().query(
                        EtablissementEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            }

            default:
                throw new UnsupportedOperationException("uri inconnue: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDaneHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case VILLES: {
                long _id = db.insert(VilleEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = VilleEntry.buildVilleUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout de la ville " + uri);
                break;
            }
            case ETABLISSEMENTS: {
                long _id = db.insert(EtablissementEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = EtablissementEntry.buildEtablissementUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout de l'établissement " + uri);
                break;
            }
            case PERSONNEL: {
                long _id = db.insert(PersonnelEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = PersonnelEntry.buildPersonnelUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout de l'établissement " + uri);
                break;
            }
            case REFERENT: {
                long _id = db.insert(ReferentEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = ReferentEntry.buildReferentUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout du référent " + uri);
                break;
            }
            case DEPARTEMENTS: {
                long _id = db.insert(DepartementEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = DepartementEntry.buildDepartementUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout du département " + uri);
                break;
            }
            case CIVILITES: {
                long _id = db.insert(CiviliteEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = CiviliteEntry.buildCiviliteUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout du département " + uri);
                break;
            }
            case DISCIPLINES: {
                long _id = db.insert(DisciplineEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = DisciplineEntry.buildDisciplineUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout de la discipline " + uri);
                break;
            }
            case ANIMATEURS: {
                long _id = db.insert(AnimateurEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = AnimateurEntry.buildAnimateurUri(_id);
                else
                    throw new android.database.SQLException("Erreur lors de l'ajout de l'établissement " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("uri inconnue: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDaneHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case VILLES:
                rowsDeleted = db.delete(
                        VilleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ETABLISSEMENTS:
                rowsDeleted = db.delete(
                        EtablissementEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PERSONNEL:
                rowsDeleted = db.delete(
                        PersonnelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REFERENT:
                rowsDeleted = db.delete(
                        ReferentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REFERENT_PAR_ID:
                String idreferent = ReferentEntry.getReferentFromUri(uri);
                rowsDeleted = db.delete(
                        ReferentEntry.TABLE_NAME, ReferentEntry._ID + " = ?", new String[]{idreferent});
                break;
            case ANIMATEURS:
                rowsDeleted = db.delete(
                        AnimateurEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DEPARTEMENTS:
                rowsDeleted = db.delete(
                        DepartementEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CIVILITES:
                rowsDeleted = db.delete(
                        CiviliteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DISCIPLINES:
                rowsDeleted = db.delete(
                        DisciplineEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("uri inconnue: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDaneHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case VILLES:
                rowsUpdated = db.update(VilleEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case ETABLISSEMENTS:
                rowsUpdated = db.update(EtablissementEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case PERSONNEL:
                rowsUpdated = db.update(PersonnelEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case ANIMATEURS:
                rowsUpdated = db.update(AnimateurEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case REFERENT:
                rowsUpdated = db.update(ReferentEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case REFERENT_PAR_ID:
                rowsUpdated = db.update(ReferentEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case DISCIPLINES:
                rowsUpdated = db.update(DisciplineEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("uri inconnue: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] contentValues) {
        final SQLiteDatabase db = mDaneHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case VILLES:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(VilleEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case ETABLISSEMENTS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(EtablissementEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case PERSONNEL:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(PersonnelEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            default:
                return super.bulkInsert(uri, contentValues);
        }
    }

}