package com.bouami.danecreteil2017_cloud.Parametres;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bouami.danecreteil2017_cloud.Fragments.ConfirmationReferentDialogFragment;
import com.bouami.danecreteil2017_cloud.Fragments.PersonnelDialogFragment;
import com.bouami.danecreteil2017_cloud.Fragments.ReferentDialogFragment;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Discipline;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Models.Referent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohammed on 05/10/2017.
 */
public class DaneContract {

    private static final String TAG = "DaneContract";
    public static final String BASE_URL ="http://www.bouami.fr/danecreteil/web/";
//        public static final String BASE_URL ="http://192.168.1.17/danecreteil/web/";
    public static final String BASE_URL_EXPORT = BASE_URL + "exportdonnees/";
    public final String BASE_URL_DEPART = BASE_URL + "listedetailvillespardepart/";
    public static final String BASE_URL_NEW_REFERENT = BASE_URL + "newreferent/";
    public static final String BASE_URL_DELETE_REFERENT = BASE_URL + "deletereferent/";
    public static final int NUM_VERSION_SQLITE = 1;
//        public static final int DATABASE_VERSION = 6;
    public static final int DATABASE_VERSION = 32;
    List<Animateur> listedesanimateurs = new ArrayList<Animateur>();
    List<Etablissement> listedesetablissements = new ArrayList<Etablissement>();
    List<Personnel> listedespersonnels = new ArrayList<Personnel>();
    List<Referent> listedesreferents = new ArrayList<Referent>();

    public static final String CONTENT_AUTHORITY = "com.bouami.danecreteil2017_cloud";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ANIMATEURS = "animateurs";
    public static final String PATH_DEPARTEMENTS = "departements";
    public static final String PATH_DISCIPLINES = "disciplines";
    public static final String PATH_CIVILITES = "civilites";
    public static final String PATH_VILLES = "villes";
    public static final String PATH_ETABLISSEMENTS = "etablissements";
    public static final String PATH_PERSONNEL = "personnel";
    public static final String PATH_REFERENTS = "referents";

    private static final String[] ETABLISSEMENT_COLUMNS = {
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry._ID,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_TYPE,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_NOM,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_TEL,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_FAX,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_EMAIL,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_ADRESSE,
            EtablissementEntry.TABLE_NAME + "." + EtablissementEntry.COLUMN_ANIMATEUR_ID,
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_NOM + " AS NOMANIMATEUR",
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_PRENOM + " AS PRENOMANIMATEUR" ,
            CiviliteEntry.TABLE_NAME + "." + CiviliteEntry.COLUMN_CIVILITE_NOM + " AS CIVILITE",
            VilleEntry.TABLE_NAME + "." + VilleEntry.COLUMN_VILLE_CP + " AS CPVILLE",
            VilleEntry.TABLE_NAME + "." + VilleEntry.COLUMN_VILLE_NOM + " AS NOMVILLE",
            DepartementEntry.TABLE_NAME + "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " AS NOMDEPARTEMENT"
    };

    public static final String[] ANIM_COLUMNS = {
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry._ID,
            CiviliteEntry.TABLE_NAME + "." + CiviliteEntry.COLUMN_CIVILITE_NOM + " AS CIVILITE",
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_NOM,
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_PRENOM,
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_TEL,
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_EMAIL,
            AnimateurEntry.TABLE_NAME + "." + AnimateurEntry.COLUMN_PHOTO,
            DepartementEntry.TABLE_NAME + "." + DepartementEntry.COLUMN_DEPARTEMENT_NOM + " AS NOMDEPARTEMENT"
    };

    public static final String[] DEPARTEMENT_COLUMNS = {
            DaneContract.DepartementEntry.TABLE_NAME + "." + DaneContract.DepartementEntry._ID,
            DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM,
            DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_INTITULE
    };


    public static final String[] DISCIPLINES_COLUMNS = new String[] {
            DaneContract.DisciplineEntry.TABLE_NAME +"."+DaneContract.DisciplineEntry._ID,
            DaneContract.DisciplineEntry.TABLE_NAME +"."+DaneContract.DisciplineEntry.COLUMN_DISCIPLINE_NOM
    };

    public static final String[] CIVILITES_COLUMNS = new String[] {
            CiviliteEntry.TABLE_NAME +"."+CiviliteEntry._ID,
            CiviliteEntry.TABLE_NAME +"."+CiviliteEntry.COLUMN_CIVILITE_NOM,
            CiviliteEntry.TABLE_NAME +"."+CiviliteEntry.COLUMN_CIVILITE_INTITULE
    };

    public static final String[] REFERENTS_COLUMNS = {
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry._ID,
            DaneContract.CiviliteEntry.TABLE_NAME + "." + DaneContract.CiviliteEntry.COLUMN_CIVILITE_NOM + " AS CIVILITE",
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_NOM,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_PRENOM,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_TEL,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_EMAIL,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_STATUT,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_REFERENT_ID,
            DaneContract.ReferentEntry.TABLE_NAME + "." + DaneContract.ReferentEntry.COLUMN_SYNCHRONISER,
            DaneContract.DisciplineEntry.TABLE_NAME + "." + DaneContract.DisciplineEntry.COLUMN_DISCIPLINE_NOM + " AS NOMDISCIPLINE",
            DaneContract.EtablissementEntry.TABLE_NAME + "." + DaneContract.EtablissementEntry.COLUMN_NOM + " AS NOMETABLISSEMENT",
            DaneContract.EtablissementEntry.TABLE_NAME + "." + DaneContract.EtablissementEntry.COLUMN_RNE + " AS RNEETABLISSEMENT",
            DaneContract.DepartementEntry.TABLE_NAME + "." + DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM + " AS NOMDEPARTEMENT",
            DaneContract.VilleEntry.TABLE_NAME + "." + DaneContract.VilleEntry.COLUMN_VILLE_NOM + " AS NOMVILLE",
    };

    public static final String[] PERSONNEL_COLUMNS = {
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry._ID,
            DaneContract.CiviliteEntry.TABLE_NAME + "." + DaneContract.CiviliteEntry.COLUMN_CIVILITE_NOM + " AS CIVILITE",
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_NOM,
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_PRENOM,
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_TEL,
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_EMAIL,
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_STATUT,
            DaneContract.PersonnelEntry.TABLE_NAME + "." + DaneContract.PersonnelEntry.COLUMN_SYNCHRONISER,
            DaneContract.EtablissementEntry.TABLE_NAME + "." + DaneContract.EtablissementEntry.COLUMN_NOM + " AS NOMETABLISSEMENT",
            DaneContract.EtablissementEntry.TABLE_NAME + "." + DaneContract.EtablissementEntry.COLUMN_RNE + " AS RNEETABLISSEMENT",
            DaneContract.DepartementEntry.TABLE_NAME + "." + DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM + " AS NOMDEPARTEMENT",
            DaneContract.VilleEntry.TABLE_NAME + "." + DaneContract.VilleEntry.COLUMN_VILLE_NOM + " AS NOMVILLE",
    };

    public static final class DepartementEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPARTEMENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPARTEMENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPARTEMENTS;

        // Table name
        public static final String TABLE_NAME = "departements";

        public static final String COLUMN_DEPARTEMENT_ID = "departement_id";
        public static final String COLUMN_DEPARTEMENT_NOM = "departement";
        public static final String COLUMN_DEPARTEMENT_INTITULE = "nom";

        public static Uri buildDepartement() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildDepartementParNom(String nomfepart) {
            return CONTENT_URI.buildUpon().appendPath(nomfepart).appendPath("nom").build();
        }
        public static Uri buildDepartementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getDepartementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class DisciplineEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DISCIPLINES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISCIPLINES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISCIPLINES;

        // Table name
        public static final String TABLE_NAME = "disciplines";

        public static final String COLUMN_DISCIPLINE_ID = "discipline_id";
        public static final String COLUMN_DISCIPLINE_NOM = "nom";

        public static Uri buildDiscipline() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildDisciplineParNom(String nomdiscipline) {
            return CONTENT_URI.buildUpon().appendPath(nomdiscipline).appendPath("nom").build();
        }
        public static Uri buildDisciplineUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildDisciplineContenatleNom(String nomdiscipline,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(nomdiscipline).appendPath(rubrique).build();
        }
        public static String getDisciplineFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CiviliteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CIVILITES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CIVILITES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CIVILITES;

        // Table name
        public static final String TABLE_NAME = "civilites";

        public static final String COLUMN_CIVILITE_NOM = "civilite";
        public static final String COLUMN_CIVILITE_INTITULE = "intitule";

        public static Uri buildCivilite() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildCiviliteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getCiviliteFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class AnimateurEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ANIMATEURS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ANIMATEURS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ANIMATEURS;

        // Table name
        public static final String TABLE_NAME = "animateurs";

        public static final String COLUMN_CIVILITE_ID = "civilite_id";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_PRENOM = "prenom";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_ANIMATEUR_ID = "animateur_id";
        public static final String COLUMN_DEPARTEMENT_ID = "departement_id";


        public static Uri buildAnimateurUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAnimateurs() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildEtabParIdAnimateur(String idanim,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(idanim).appendPath(rubrique).build();
        }

        public static Uri buildAnimateurContenatleNom(String idanim,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(idanim).appendPath(rubrique).build();
        }

        public static Uri buildAnimateursParDepartement(String departement,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(rubrique).build();
        }

        public static Uri buildAnimateurParDepartementContenatleNom(String departement,String nomanim,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(nomanim).appendPath(rubrique).build();
        }

        public static String getNomAnimateurFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getIdAnimateurFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class VilleEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VILLES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VILLES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VILLES;

        // Table name
        public static final String TABLE_NAME = "villes";
        public static final String COLUMN_VILLE_NOM = "nom";
        public static final String COLUMN_VILLE_CP = "cp";
        public static final String COLUMN_DEPARTEMENT_ID = "departement_id";

        public static Uri buildVille() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildVilleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static Uri buildVilleParDepartement(String departement) {
            return CONTENT_URI.buildUpon().appendPath(departement).build();
        }


        public static String getDepartementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class EtablissementEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ETABLISSEMENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ETABLISSEMENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ETABLISSEMENTS;

        public static final String TABLE_NAME = "etablissements";

        // Column with the foreign key into the location table.
        public static final String COLUMN_VILLE_ID = "ville_id";
        public static final String COLUMN_ANIMATEUR_ID = "animateur_id";
        public static final String COLUMN_ETABLISSEMENT_ID = "etablissement_id";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_RNE = "rne";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_FAX = "fax";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADRESSE = "adresse";
        public static final String COLUMN_TYPE = "type";

        public static Uri buildEtablissementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildEtablissementParVille(String idville) {
            return CONTENT_URI.buildUpon().appendPath(idville).build();
        }

        public static Uri buildEtablissements() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildEtablissementParId(String idetab,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(idetab).appendPath(rubrique).build();
        }

        public static Uri buildEtablissementParDepartement(String departement,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(rubrique).build();
        }

        public static Uri buildEtablissementContenatLeNom(String nometab,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(nometab).appendPath(rubrique).build();
        }

        public static Uri buildEtablissementParDepartementContenatLeNom(String departement,String nometab,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(nometab).appendPath(rubrique).build();
        }

        public static String getVilleFromUri(Uri uri) {

            return uri.getPathSegments().get(1);
        }

        public static String getEtablissementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
        public static String getNomEtablissementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class PersonnelEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSONNEL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSONNEL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSONNEL;

        public static final String TABLE_NAME = "personnel";

        // Column with the foreign key into the location table.
        public static final String COLUMN_PERSONNEL_ID = "personnel_id";
        public static final String COLUMN_ETABLISSEMENT_ID = "etablissement_id";
        public static final String COLUMN_CIVILITE_ID = "civilite_id";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_PRENOM = "prenom";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_STATUT = "statut";
        public static final String COLUMN_SYNCHRONISER = "synchroniser";

        public static Uri buildPersonnelUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildPersonnel() {
            return CONTENT_URI.buildUpon().build();
        }
        //        public static String getVilleFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
        public static String getEtablissementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
        public static Uri buildPersonnelParIdEtab(String idetab,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(idetab).appendPath(rubrique).build();
        }

        public static Uri buildPersonnelParDepartement(String departement,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(rubrique).build();
        }

        public static Uri buildPersonnelParDepartementContenatLeNom(String departement,String nompersonnel,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(nompersonnel).appendPath(rubrique).build();
        }

        public static String getPersonnelFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class ReferentEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REFERENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REFERENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REFERENTS;

        public static final String TABLE_NAME = "referents";

        // Column with the foreign key into the location table.
        public static final String COLUMN_REFERENT_ID = "referent_id";
        public static final String COLUMN_ETABLISSEMENT_ID = "etablissement_id";
        public static final String COLUMN_CIVILITE_ID = "civilite_id";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_PRENOM = "prenom";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_STATUT = "statut";
        public static final String COLUMN_DISCIPLINE_ID = "discipline_id";
        public static final String COLUMN_SYNCHRONISER = "synchroniser";

        public static Uri buildReferentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildReferent() {
            return CONTENT_URI.buildUpon().build();
        }
        //        public static String getVilleFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
        public static String getEtablissementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
        public static Uri buildReferentsParIdEtab(String idetab,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(idetab).appendPath(rubrique).build();
        }

        public static Uri buildReferentsParDepartement(String departement,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(rubrique).build();
        }

        public static Uri buildReferentsParDepartementContenatLeNom(String departement,String nomreferent,String rubrique) {
            return CONTENT_URI.buildUpon().appendPath(departement).appendPath(nomreferent).appendPath(rubrique).build();
        }

        public static String getReferentFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }


    public static void initialiserBase(Context mContext){
//            mContext.deleteDatabase(DaneContract.CONTENT_AUTHORITY);
//            mContext.openOrCreateDatabase("test",Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
        mContext.getContentResolver().delete(DaneContract.PersonnelEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.ReferentEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.EtablissementEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.VilleEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.AnimateurEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.DepartementEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.DisciplineEntry.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(DaneContract.CiviliteEntry.CONTENT_URI,null,null);
    }

    static long addDepartement(Context mContext, String nom, String intitule) {

        long departementId;
        Cursor departementCursor = mContext.getContentResolver().query(
                DaneContract.DepartementEntry.CONTENT_URI,
                new String[]{DaneContract.DepartementEntry._ID},
                DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM + " = ?",
                new String[]{nom},
                null);
        if (departementCursor.moveToFirst()) {
            int departementIdIndex = departementCursor.getColumnIndex(DaneContract.DepartementEntry._ID);
            departementId = departementCursor.getLong(departementIdIndex);
        } else {
            ContentValues departementValues = new ContentValues();
            departementValues.put(DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM, nom);
            departementValues.put(DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_INTITULE, intitule);
            Uri insertedUri = mContext.getContentResolver().insert(
                    DaneContract.DepartementEntry.CONTENT_URI,
                    departementValues
            );
            departementId = ContentUris.parseId(insertedUri);
        }
        departementCursor.close();
        return departementId;
    }

    static long addDiscipline(Context mContext, ContentValues discipline) {
        final String OWM_DISCIPLINE_NOM = "nom";
        long disciplineId;
        Cursor disciplineCursor = mContext.getContentResolver().query(
                DaneContract.DisciplineEntry.CONTENT_URI,
                new String[]{DaneContract.DisciplineEntry._ID},
                DisciplineEntry.COLUMN_DISCIPLINE_NOM + " = ?",
                new String[]{discipline.getAsString(OWM_DISCIPLINE_NOM)},
                null);
        if (disciplineCursor.moveToFirst()) {
            int disciplineIdIndex = disciplineCursor.getColumnIndex(DaneContract.DisciplineEntry._ID);
            disciplineId = disciplineCursor.getLong(disciplineIdIndex);
        } else {
            ContentValues disciplineValues = new ContentValues();
            disciplineValues.put(DisciplineEntry.COLUMN_DISCIPLINE_NOM, discipline.getAsString(OWM_DISCIPLINE_NOM));
            Uri insertedUri = mContext.getContentResolver().insert(
                    DaneContract.DisciplineEntry.CONTENT_URI,
                    disciplineValues
            );
            disciplineId = ContentUris.parseId(insertedUri);
        }
        disciplineCursor.close();
        return disciplineId;
    }

    static long addCivilite(Context mContext, String nom, String intitule) {

        long civiliteId;
        Cursor civiliteCursor = mContext.getContentResolver().query(
                DaneContract.CiviliteEntry.CONTENT_URI,
                new String[]{DaneContract.CiviliteEntry._ID},
                CiviliteEntry.COLUMN_CIVILITE_NOM + " = ?",
                new String[]{nom},
                null);
        if (civiliteCursor.moveToFirst()) {
            int civiliteIdIndex = civiliteCursor.getColumnIndex(DaneContract.CiviliteEntry._ID);
            civiliteId = civiliteCursor.getLong(civiliteIdIndex);
        } else {
            ContentValues civiliteValues = new ContentValues();
            civiliteValues.put(CiviliteEntry.COLUMN_CIVILITE_NOM, nom);
            civiliteValues.put(CiviliteEntry.COLUMN_CIVILITE_INTITULE, intitule);
            Uri insertedUri = mContext.getContentResolver().insert(
                    DaneContract.CiviliteEntry.CONTENT_URI,
                    civiliteValues
            );
            civiliteId = ContentUris.parseId(insertedUri);
        }
        civiliteCursor.close();
        return civiliteId;
    }

    static long addVille(Context mContext, ContentValues laville) {
        long villeId = 0;
        final String OWM_VILLE_CP = "cp";
        Cursor villeCursor = null;
        villeCursor = mContext.getContentResolver().query(
                VilleEntry.CONTENT_URI,
                new String[]{VilleEntry._ID},
                VilleEntry.COLUMN_VILLE_CP + " = ?",
                new String[]{laville.getAsString(OWM_VILLE_CP)},
                null);
        if (villeCursor.moveToFirst()) {
            int villeIdIndex = villeCursor.getColumnIndex(VilleEntry._ID);
            villeId = villeCursor.getLong(villeIdIndex);
        } else {
            Uri insertedUri = mContext.getContentResolver().insert(
                    VilleEntry.CONTENT_URI,
                    laville
            );
            villeId = ContentUris.parseId(insertedUri);
        }
        villeCursor.close();
//        Log.d(TAG, "Ville " + laville + " : "+villeId);
        return villeId;
    }

    static long addAnimateur(Context mContext, ContentValues animateur) {
        long animateurId = 0;
        final String OWM_ANIMATEUR_ID = "animateur_id";
        Cursor animateurCursor = null;
        animateurCursor = mContext.getContentResolver().query(
                AnimateurEntry.CONTENT_URI,
                new String[]{AnimateurEntry._ID},
                AnimateurEntry.COLUMN_ANIMATEUR_ID + " = ?",
                new String[]{animateur.getAsString(OWM_ANIMATEUR_ID)},
                null);
        if (animateurCursor.moveToFirst()) {
            int animateurIdIndex = animateurCursor.getColumnIndex(AnimateurEntry._ID);
            animateurId = animateurCursor.getLong(animateurIdIndex);
        } else {
            Uri insertedUri = mContext.getContentResolver().insert(
                    AnimateurEntry.CONTENT_URI,
                    animateur
            );
            animateurId = ContentUris.parseId(insertedUri);
        }
        animateurCursor.close();
//        Log.d(TAG, "Animateur " + animateur + " : "+animateurId);
        return animateurId;
    }

    static long addEtablissement(Context mContext, ContentValues etablissement) {
        long etablissementId = 0;
        final String OWM_ETABLISSEMENT_ID = "etablissement_id";
        // First, check if the location with this city name exists in the db
        Cursor etablissementCursor = null;
        etablissementCursor = mContext.getContentResolver().query(
                EtablissementEntry.CONTENT_URI,
                new String[]{EtablissementEntry._ID},
                EtablissementEntry.COLUMN_ETABLISSEMENT_ID + " = ?",
                new String[]{etablissement.getAsString(OWM_ETABLISSEMENT_ID)},
                null);
        if (etablissementCursor.moveToFirst()) {
            int etablissementIdIndex = etablissementCursor.getColumnIndex(EtablissementEntry._ID);
            etablissementId = etablissementCursor.getLong(etablissementIdIndex);
        } else {
            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    EtablissementEntry.CONTENT_URI,
                    etablissement
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            etablissementId = ContentUris.parseId(insertedUri);
        }
        etablissementCursor.close();
//        Log.d(TAG, "Animateur " + etablissement + " : "+etablissementId);
        return etablissementId;
    }

    public static long addPersonnel(Context mContext, ContentValues personnel) {
        long personnelId = 0;
        final String OWM_PERSONNEL_ID = "personnel_id";
        // First, check if the location with this city name exists in the db
        Cursor personnelCursor = null;
        personnelCursor = mContext.getContentResolver().query(
                PersonnelEntry.CONTENT_URI,
                new String[]{PersonnelEntry._ID},
                PersonnelEntry.COLUMN_PERSONNEL_ID + " = ?",
                new String[]{personnel.getAsString(OWM_PERSONNEL_ID)},
                null);
        if (personnelCursor.moveToFirst()) {
            int personnelIdIndex = personnelCursor.getColumnIndex(PersonnelEntry._ID);
            personnelId = personnelCursor.getLong(personnelIdIndex);
        } else {
            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    PersonnelEntry.CONTENT_URI,
                    personnel
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            personnelId = ContentUris.parseId(insertedUri);
        }
        personnelCursor.close();
//        Log.d(TAG, "personnel " + personnel + " : "+personnelId);
        return personnelId;
    }

    public static long addReferent(Context mContext, ContentValues referent) {
        long referentId = 0;
//        final String OWM_REFERENT_ID = "referent_id";
//        // First, check if the location with this city name exists in the db
//        Cursor referentCursor = null;
//        referentCursor = mContext.getContentResolver().query(
//                ReferentEntry.CONTENT_URI,
//                new String[]{ReferentEntry._ID},
//                ReferentEntry.COLUMN_REFERENT_ID + " = ?",
//                new String[]{referent.getAsString(OWM_REFERENT_ID)},
//                null);
//        if (referentCursor.moveToFirst()) {
//            int referentIdIndex = referentCursor.getColumnIndex(ReferentEntry._ID);
//            referentId = referentCursor.getLong(referentIdIndex);
//        } else {
            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(ReferentEntry.CONTENT_URI,referent);
            referentId = ContentUris.parseId(insertedUri);
//        }
//        referentCursor.close();
        return referentId;
    }

    public static long updateReferent(Context mContext, ContentValues referent) {
//        Uri muri = DaneContract.ReferentEntry.buildReferentUri(referent.getAsLong("_id"));
//        referent.remove("referent_id");
//        long updatereferent = mContext.getContentResolver().update(muri,referent,null,null);
//        return updatereferent;
        Uri muri = DaneContract.ReferentEntry.buildReferent();
        String idreferent = referent.getAsString("_id");
        referent.remove("_id");
        referent.remove("etablissement_id");
        referent.remove("referent_id");
        Long iddiscipline = referent.getAsLong("discipline_id");
        if (iddiscipline==null) referent.remove("discipline_id");
        String[] selectionArgs = new String[]{idreferent};
        String selection = ReferentEntry.TABLE_NAME+"." + ReferentEntry._ID + " = ? ";
        long updatereferent = mContext.getContentResolver().update(muri,referent,selection,selectionArgs);
        return updatereferent;
    }

    public static long updatePersonnel(Context mContext, ContentValues personnel) {
        Uri muri = DaneContract.PersonnelEntry.buildPersonnel();
        String idpersonnel = personnel.getAsString("_id");
        personnel.remove("_id");
//        personnel.remove("etablissement_id");
        personnel.remove("personnel_id");
        String[] selectionArgs = new String[]{idpersonnel};
        String selection = PersonnelEntry.TABLE_NAME+"." + PersonnelEntry._ID + " = ? ";
        long updatepersonnel = mContext.getContentResolver().update(muri,personnel,selection,selectionArgs);
        return updatepersonnel;
    }
    public static String getValueFromCursor(Cursor cursor,String champ) {
        return cursor.getString(cursor.getColumnIndexOrThrow(champ));
    }

    public static  Cursor getListeDisciplines(Context mContext) {
        Uri uri = DaneContract.DisciplineEntry.buildDiscipline();
        Cursor disciplinescursor = mContext.getContentResolver().query(uri,
                DISCIPLINES_COLUMNS,
                null,
                null,
                null);
        if (disciplinescursor.moveToFirst()){
            return disciplinescursor;
        }
        return null;
    };

    public static  Cursor getEtablissementFromId(Context mContext, Long idetab) {
        Uri uri = DaneContract.EtablissementEntry.buildEtablissementUri(idetab);
        Cursor etabcursor = mContext.getContentResolver().query(uri,
                ETABLISSEMENT_COLUMNS,
                null,
                null,
                null);
        if (etabcursor.moveToFirst()){
            return etabcursor;
        }
        return null;
    };

    public static  Cursor getReferentFromId(Context mContext, Long idreferent) {
        Uri uri = ReferentEntry.buildReferentUri(idreferent);
        Cursor referentcursor = mContext.getContentResolver().query(uri,
                REFERENTS_COLUMNS,
                null,
                null,
                null);
        if (referentcursor.moveToFirst()){
            return referentcursor;
        }
        return null;
    };

    public static  Cursor getPersonnelFromId(Context mContext, Long idpersonnel) {
        Uri uri = PersonnelEntry.buildPersonnelUri(idpersonnel);
        Cursor personnelcursor = mContext.getContentResolver().query(uri,
                PERSONNEL_COLUMNS,
                null,
                null,
                null);
        if (personnelcursor.moveToFirst()){
            return personnelcursor;
        }
        return null;
    };

    public static Cursor getAnimateurFromId(Context mContext, Long idanim) {
        Uri uri = DaneContract.AnimateurEntry.buildAnimateurUri(idanim);
        Cursor animcursor = mContext.getContentResolver().query(uri,
                ANIM_COLUMNS,
                null,
                null,
                null);
        if (animcursor.moveToFirst()){
            return animcursor;
        }
        return null;
    };

    public  static Cursor rechercherAnimateurs(Context mContext, String constraint, String mDepartement) {
        if (!constraint.equals("")) {
            Uri uri = DaneContract.AnimateurEntry.buildAnimateurParDepartementContenatleNom(mDepartement,constraint,"rechercher");
            String[] selectionArgs = new String[]{"%" + constraint +"%",mDepartement};
            String sortOrder = DaneContract.AnimateurEntry.COLUMN_NOM;
            String selection = DaneContract.AnimateurEntry.TABLE_NAME+"." + DaneContract.AnimateurEntry.COLUMN_NOM +
                    " like ? AND "+DaneContract.DepartementEntry.TABLE_NAME+"."+DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM+" = ?";
            return mContext.getContentResolver().query(uri,
                    ANIM_COLUMNS,
                    selection,
                    selectionArgs,
                    null,
                    null
            );
        } else {
            return null;
        }
    }

    public  static Cursor rechercherDiscipline(Context mContext, String constraint) {
        if (!constraint.equals("")) {
            Uri uri = DaneContract.DisciplineEntry.buildDisciplineContenatleNom(constraint,"rechercher");
            String[] selectionArgs = new String[]{"%" + constraint +"%"};
            String sortOrder = DisciplineEntry.COLUMN_DISCIPLINE_NOM;
            String selection = DaneContract.DisciplineEntry.TABLE_NAME+"." + DisciplineEntry.COLUMN_DISCIPLINE_NOM + " like ? ";
            return mContext.getContentResolver().query(uri,
                    DISCIPLINES_COLUMNS,
                    selection,
                    selectionArgs,
                    null,
                    null
            );
        } else {
            return null;
        }
    }

    private String getDepartementId(Context mContext, String mDepartement) {
        Uri DepartementUri = DaneContract.DepartementEntry.buildDepartementParNom(mDepartement);
        Cursor departcursor = mContext.getContentResolver().query(DepartementUri,
                DEPARTEMENT_COLUMNS,
                null,
                null,
                null);
        if (departcursor.moveToFirst()){
            return departcursor.getString(departcursor.getColumnIndex(DaneContract.DepartementEntry._ID));
        }
        return null;
    };


    public JSONObject getDonneesFromUrl(String url, Context ctx) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(ctx);
        final JSONObject[] donneesjson = new JSONObject[1];
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        donneesjson[0] = response;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        donneesjson[0] = null;
                    }
                });
//        stringRequest.setTag(TAG);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(jsObjRequest);
        return donneesjson[0];
    }

    public String getNomEtablissementFromReferentId(JSONObject donnees, String depart, Referent referent ) {
        try {
            String Idetab = referent.getEtablissement();
            Etablissement etablissement = new Etablissement(donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(Idetab));
            return  etablissement.getType()+ " "+etablissement.getNom();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNomEtablissementFromPersonnelId(JSONObject donnees, String depart, Personnel personnel ) {
        try {
            String Idetab = (donnees.getJSONObject("personnel").getJSONObject(depart).getJSONObject(personnel.getId()).getJSONObject("etablissements")).keys().next();
            Etablissement etablissement = new Etablissement(donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(Idetab));
            return  etablissement.getType()+ " "+etablissement.getNom() + " ("+etablissement.getVille()+")";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCiviliteFromId(Context mContext,Long idCivilite) {
        Uri CiviliteUri = DaneContract.CiviliteEntry.buildCiviliteUri(idCivilite);
        Cursor civilitecursor = mContext.getContentResolver().query(CiviliteUri,
                CIVILITES_COLUMNS,
                null,
                null,
                null);
        if (civilitecursor.moveToFirst()){
            return civilitecursor.getString(civilitecursor.getColumnIndexOrThrow(CiviliteEntry.COLUMN_CIVILITE_NOM));
        }
        return null;
    }

    public static Long getIdCiviliteFromNom(Context mContext,String civilite) {
        long civiliteId = 0;
        Cursor civiliteCursor = mContext.getContentResolver().query(
                DaneContract.CiviliteEntry.CONTENT_URI,
                new String[]{DaneContract.CiviliteEntry._ID},
                CiviliteEntry.COLUMN_CIVILITE_NOM + " = ?",
                new String[]{civilite},
                null);
        if (civiliteCursor.moveToFirst()) {
            int civiliteIdIndex = civiliteCursor.getColumnIndex(DaneContract.CiviliteEntry._ID);
            civiliteId = civiliteCursor.getLong(civiliteIdIndex);
        }
        civiliteCursor.close();
        return civiliteId;
    }

    @SuppressLint("LongLogTag")
    public List<Animateur> getListeAnimateursCreteil(JSONObject donnees, String depart) throws JSONException {
        listedesanimateurs = new ArrayList<Animateur>();
        Iterator<String> keysdonnees = donnees.keys();
        Boolean trouve = false;
        while (keysdonnees.hasNext()){
            if (keysdonnees.next().equals("animateurs")){
                trouve = true;
                break;
            }
        }
        if(trouve) {
            if (!depart.equals("")) {
                JSONObject listeanims = donnees.getJSONObject("animateurs").getJSONObject(depart);
                Iterator<String> keys = listeanims.keys();
                try {
                    while(keys.hasNext()){
                        String keyanimateur = keys.next();
                        Animateur anim = new Animateur(listeanims.getJSONObject(keyanimateur));
                        listedesanimateurs.add(anim);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                JSONObject listeanims = donnees.getJSONObject("animateurs");
                Iterator<String> keysdepart = listeanims.keys();
                while (keysdepart.hasNext()){
                    String keydepartencours = keysdepart.next();
                    JSONObject listeanimspardepart = listeanims.getJSONObject(keydepartencours);
                    Iterator<String> keys = listeanimspardepart.keys();
                    try {
                        while(keys.hasNext()){
                            String keyanimateur = keys.next();
                            Animateur anim = new Animateur(listeanimspardepart.getJSONObject(keyanimateur));
                            listedesanimateurs.add(anim);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return listedesanimateurs;
    }

    @SuppressLint("LongLogTag")
    public List<Etablissement> getListeEtablissementsCreteil(JSONObject donnees, String depart) throws JSONException {
        listedesetablissements = new ArrayList<Etablissement>();
        if (!depart.equals("")) {
            JSONObject listeetabs = donnees.getJSONObject("etablissements").getJSONObject(depart);
            Iterator<String> keys = listeetabs.keys();
            try {
                while (keys.hasNext()) {
                    String keyetablissement = keys.next();
                    Etablissement etab = new Etablissement(listeetabs.getJSONObject(keyetablissement));
                    listedesetablissements.add(etab);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject listeetabs = donnees.getJSONObject("etablissements");
            Iterator<String> keysdepart = listeetabs.keys();
            while (keysdepart.hasNext()){
                String keydepartencours = keysdepart.next();
                JSONObject listeanimspardepart = listeetabs.getJSONObject(keydepartencours);
                Iterator<String> keys = listeanimspardepart.keys();
                try {
                    while(keys.hasNext()){
                        String keyetablissement = keys.next();
                        Etablissement etab = new Etablissement(listeanimspardepart.getJSONObject(keyetablissement));
                        listedesetablissements.add(etab);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listedesetablissements;
    }

    public List<Personnel> getListePersonnelCreteil(JSONObject donnees, String depart) throws JSONException {
        listedespersonnels = new ArrayList<Personnel>();
        if (!depart.equals("")) {
            JSONObject listepersonnels = donnees.getJSONObject("personnel").getJSONObject(depart);
            Iterator<String> keys = listepersonnels.keys();
            try {
                while (keys.hasNext()) {
                    String keypersonnel = keys.next();
                    Personnel personnel = new Personnel(listepersonnels.getJSONObject(keypersonnel));
                    listedespersonnels.add(personnel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject listepersonnels = donnees.getJSONObject("personnel");
            Iterator<String> keysdepart = listepersonnels.keys();
            while (keysdepart.hasNext()){
                String keydepartencours = keysdepart.next();
                JSONObject listepersonnelspardepart = listepersonnels.getJSONObject(keydepartencours);
                Iterator<String> keys = listepersonnelspardepart.keys();
                try {
                    while(keys.hasNext()){
                        String keypersonnel = keys.next();
                        Personnel personnel = new Personnel(listepersonnelspardepart.getJSONObject(keypersonnel));
                        listedespersonnels.add(personnel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listedespersonnels;
    }

    public List<Referent> getListeReferentsCreteil(JSONObject donnees, String depart) throws JSONException {
        listedesreferents = new ArrayList<Referent>();
        Iterator<String> keysdonnees = donnees.keys();
        Boolean trouve = false;
        while (keysdonnees.hasNext()){
            if (keysdonnees.next().equals("referentsnumeriques")){
                trouve = true;
                break;
            }
        }
        if (trouve) {
            if (!depart.equals("")) {
                JSONObject listereferents = donnees.getJSONObject("referentsnumeriques").getJSONObject(depart);
                Iterator<String> keys = listereferents.keys();
                try {
                    while (keys.hasNext()) {
                        String keyreferent = keys.next();
                        Referent referent = new Referent(listereferents.getJSONObject(keyreferent));
                        listedesreferents.add(referent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                JSONObject listereferents = donnees.getJSONObject("referentsnumeriques");
                Iterator<String> keysdepart = listereferents.keys();
                while (keysdepart.hasNext()){
                    String keydepartencours = keysdepart.next();
                    JSONObject listereferentspardepart = listereferents.getJSONObject(keydepartencours);
                    Iterator<String> keys = listereferentspardepart.keys();
                    try {
                        while(keys.hasNext()){
                            String keyreferent = keys.next();
                            Referent referent = new Referent(listereferentspardepart.getJSONObject(keyreferent));
                            listedesreferents.add(referent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return listedesreferents;
    }

    @SuppressLint("LongLogTag")
    public List<Etablissement> getListeEtablissementsCreteilParAnimateur(JSONObject donnees, String depart, String idanimateur) throws JSONException {
        List<Etablissement> etabsparanim = new ArrayList<Etablissement>();
        Iterator<String> keysdonnees = donnees.keys();
        Boolean trouve = false;
        while (keysdonnees.hasNext()){
            if (keysdonnees.next().equals("animateurs")){
                trouve = true;
                break;
            }
        }
        if (trouve) {
            JSONObject listeetabs = donnees.getJSONObject("animateurs").getJSONObject(depart).getJSONObject(idanimateur).getJSONObject("etablissements");
            JSONObject etabs = donnees.getJSONObject("etablissements").getJSONObject(depart);
            Iterator<String> keys = listeetabs.keys();
            while (keys.hasNext()) {
                String keyetab = keys.next();
                Etablissement etablissement = new Etablissement(etabs.getJSONObject(keyetab));
                etabsparanim.add(etablissement);
            }
        }
        return etabsparanim;
    }

    @SuppressLint("LongLogTag")
    public List<Personnel> getListePersonnelsCreteilParEtablissement(JSONObject donnees, String depart, String idetablissement) throws JSONException {
        List<Personnel> personnesparetab = new ArrayList<Personnel>();
        Iterator<String> keysdonnees = donnees.keys();
        JSONObject listepersonnels = donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(idetablissement).getJSONObject("personnels");
        JSONObject personnelsjson = donnees.getJSONObject("personnel").getJSONObject(depart);
        Iterator<String> keys = listepersonnels.keys();
        while (keys.hasNext()) {
            String keypersonnel = keys.next();
            Personnel lepersonnel = new Personnel(personnelsjson.getJSONObject(keypersonnel));
            personnesparetab.add(lepersonnel);
        }
        return personnesparetab;
    }

    @SuppressLint("LongLogTag")
    public List<Referent> getListeReferentsCreteilParEtablissement(JSONObject donnees, String depart, String idetablissement) throws JSONException {
        List<Referent> referentsparetab = new ArrayList<Referent>();
        Iterator<String> keysdonnees = donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(idetablissement).keys();
        Boolean trouve = false;
        while (keysdonnees.hasNext()){
            if (keysdonnees.next().equals("referentsnumeriques")){
                trouve = true;
                break;
            }
        }
        if (trouve) {
            JSONObject listereferents = donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(idetablissement).getJSONObject("referentsnumeriques");
            JSONObject referentsjson = donnees.getJSONObject("referentsnumeriques").getJSONObject(depart);
            Iterator<String> keys = listereferents.keys();
            while (keys.hasNext()) {
                String keyreferent = keys.next();
                Referent lereferent = new Referent(referentsjson.getJSONObject(keyreferent));
                referentsparetab.add(lereferent);
            }
        }
        return referentsparetab;
    }

    public List<Animateur> getListeAnimateurs(JSONObject donnees, String depart) {
        listedesanimateurs = new ArrayList<Animateur>();
        try {
            if (depart.length()==0) {
                Iterator<String> keys = donnees.keys();
                while(keys.hasNext()){
                    String keydepart = keys.next();
                    AjouterAnimateurParDepart(donnees, keydepart);
                }
            } else {
                JSONArray datadepart = donnees.getJSONArray(depart);
                AjouterAnimateurParDepart(donnees, depart);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listedesanimateurs;
    }

    public List<Etablissement> getListeEtablissements(JSONObject donnees, String depart) {
        listedesetablissements = new ArrayList<Etablissement>();
        try {
            if (depart.length()==0) {
                Iterator<String> keys = donnees.keys();
                while(keys.hasNext()){
                    String keydepart = keys.next();
                    AjouterEtablissementParDepart(donnees, keydepart);
                }
            } else {
                JSONArray datadepart = donnees.getJSONArray(depart);
                AjouterEtablissementParDepart(donnees, depart);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listedesetablissements;
    }

    private void AjouterAnimateurParDepart(JSONObject contenu, String depart) throws JSONException {
        JSONArray datadepart = contenu.getJSONArray(depart);
        for (int i = 0; i < datadepart.length(); i++) {
            JSONArray etabsObject = datadepart.getJSONObject(i).getJSONArray("etabs");
            for (int j = 0; j < etabsObject.length(); j++) {
                JSONArray animateursObject = etabsObject.getJSONObject(j).getJSONArray("animateur");
                for (int k = 0; k < animateursObject.length(); k++) {
                    Animateur anim = new Animateur(animateursObject.getJSONObject(k));
                    boolean trouve = false;
                    for (Animateur animateur : listedesanimateurs) {
                        if (animateur.lememeAnimateur(anim)) {
                            trouve = true;
                            break;
                        }
                    }
                    if (!trouve) {
                        listedesanimateurs.add(anim);
                    }
//                        if (listedesanimateurs.contains(anim)==false) {
//                            listedesanimateurs.add(anim);
//                        }
                }
            }
        }
    }

    private void AjouterEtablissementParDepart(JSONObject contenu, String depart) throws JSONException {
        JSONArray datadepart = contenu.getJSONArray(depart);
        for (int i = 0; i < datadepart.length(); i++) {
            JSONArray etabsObject = datadepart.getJSONObject(i).getJSONArray("etabs");
            for (int j = 0; j < etabsObject.length(); j++) {
                listedesetablissements.add(new Etablissement(etabsObject.getJSONObject(i)));
            }
        }
    }

    public Intent createShareIntent(String mNomEtabcast, String mEtabSharecast) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Carte visite : "+mNomEtabcast);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mEtabSharecast);
        return shareIntent;
    }

    public static Intent createPhoneIntent(String mTelEtabcast) {
        Intent shareIntent = new Intent(Intent.ACTION_DIAL);
        shareIntent.setData(Uri.parse("tel:"+mTelEtabcast));
        return shareIntent;
    }

    public static Intent createMailIntent(String mMailEtabcast) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mMailEtabcast });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande de rendez-vous");
        return shareIntent;
    }

    public static Intent createMapIntent(String mAdresseEtabcast) {
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", mAdresseEtabcast)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        return intent;
    }

    public static void ImporterDonneesFromUrlToDatabase(final Context ctx, String url) {
        JsonObjectRequest jsObjRequest;
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(ctx);
        DaneContract.initialiserBase(ctx);
        jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(final JSONObject response) {
//                        Log.d(TAG, "onResponse" + response);
                        try {
                            importerDisciplinesDansBase(ctx,response);
                            Iterator<String> keysdepartement = response.getJSONObject("etablissements").keys();
                            String depart = null;
                            while (keysdepartement.hasNext()){
                                String intitule = "";
                                depart = keysdepartement.next();
                                switch (depart){
                                    case "77" : intitule = "Seine et Marne";break;
                                    case "93" : intitule = "Seine Saint Denis";break;
                                    case "94" : intitule = "Val de Marne";break;
                                }
                                importerAnimateursDansBase(ctx,response,depart);
                                importerEtablissementsDansBase(ctx,response,depart);
                                importerReferentsDansBase(ctx,response,depart);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "That didn't work!");
                    }
                });
        //        stringRequest.setTag(TAG);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(jsObjRequest);
    }

    @SuppressLint("LongLogTag")
    public static void importerDisciplinesDansBase(Context ctx, JSONObject donnees) throws JSONException {
        if (!donnees.isNull("disciplines")) {
            Iterator<String> keysdisciplines = donnees.getJSONObject("disciplines").keys();
            String iddiscipline = null;
            while (keysdisciplines.hasNext()){
                iddiscipline = keysdisciplines.next();
                Discipline discp = new Discipline(donnees.getJSONObject("disciplines").getJSONObject(iddiscipline));
                discp.setDisciplines();
                long inserteddiscipline = DaneContract.addDiscipline(ctx,discp.getDisciplines());
                Log.d(TAG, "discipline : " + discp.getDisciplines().getAsString("nom") + "---"+inserteddiscipline);
            }
        }
    }

    @SuppressLint("LongLogTag")
    public static void importerAnimateursDansBase(Context ctx, JSONObject donnees, String departement) throws JSONException {
        Iterator<String> keysanimas = donnees.getJSONObject("animateurs").getJSONObject(departement).keys();
        String intitule = null;
        Animateur anim = null;
        switch (departement){
            case "77" : intitule = "Seine et Marne";break;
            case "93" : intitule = "Seine Saint Denis";break;
            case "94" : intitule = "Val de Marne";break;
        }
        long insertedDepartement = DaneContract.addDepartement(ctx,departement,intitule);
        while (keysanimas.hasNext()){
            anim = new Animateur(donnees.getJSONObject("animateurs").getJSONObject(departement).getJSONObject(keysanimas.next()));
            String animciviliteintitule = "Madame";
            switch (anim.getCivilite()) {
                case "M" : animciviliteintitule = "Monsieur";break;
                case "Mr" : animciviliteintitule = "Monsieur";break;
            }
            long insertedCivilite = DaneContract.addCivilite(ctx,anim.getCivilite(),animciviliteintitule);
            anim.setAnim(insertedDepartement,insertedCivilite);
            long insertedanim = DaneContract.addAnimateur(ctx,anim.getAnim());
            Log.d(TAG, "animateur : "+"---"+insertedanim + anim.getAnim().getAsString("nom")+"---"+anim.getTel()+"---"+anim.getAnim().getAsString("email"));
        }
    }

    @SuppressLint("LongLogTag")
    public static void importerReferentsDansBase(Context ctx, JSONObject donnees, String departement) throws JSONException {
        if (!donnees.getJSONObject("referentsnumeriques").isNull(departement)) {
            Iterator<String> keysreferent = donnees.getJSONObject("referentsnumeriques").getJSONObject(departement).keys();
            String idreferent = null;
            while (keysreferent.hasNext()){
                idreferent = keysreferent.next();
                Referent ref = new Referent(donnees.getJSONObject("referentsnumeriques").getJSONObject(departement).getJSONObject(idreferent));
                String referentciviliteintitule = "Madame";
                switch (ref.getCivilite()) {
                    case "M" : referentciviliteintitule = "Monsieur";break;
                    case "Mr" : referentciviliteintitule = "Monsieur";break;
                }
                long insertedCivilite = DaneContract.addCivilite(ctx,ref.getCivilite(),referentciviliteintitule);
                ref.setRefer(true,insertedCivilite);
                long insertedreferent = DaneContract.addReferent(ctx,ref.getRefer());
                Log.d(TAG, "referent : " + ref.getRefer().getAsString("nom") + "---"+ref.getRefer().getAsString("statut")+"---"+ref.getRefer().getAsString("etablissement_id"));
            }
        }
    }

    @SuppressLint("LongLogTag")
    public static void importerEtablissementsDansBase(Context ctx, JSONObject donnees, String departement) throws JSONException {
        Iterator<String> keysetabs = donnees.getJSONObject("etablissements").getJSONObject(departement).keys();
        String idetab = null;
        String intitule = null;
        while (keysetabs.hasNext()){
            idetab = keysetabs.next();
            switch (departement){
                case "77" : intitule = "Seine et Marne";break;
                case "93" : intitule = "Seine Saint Denis";break;
                case "94" : intitule = "Val de Marne";break;
            }
            long insertedDepartement = DaneContract.addDepartement(ctx,departement,intitule);
            Etablissement etab = new Etablissement(donnees.getJSONObject("etablissements").getJSONObject(departement).getJSONObject(idetab));
            etab.setVil(insertedDepartement);
            long insertedville = DaneContract.addVille(ctx,etab.getVil());
            Log.d(TAG, "cp : " + etab.getVil().getAsString("cp") + "---"+insertedDepartement+"---"+insertedville);
            Map<String, Boolean> lesanimateurs = etab.getAnimateurs();
            long insertedanim = 0;
            if (lesanimateurs!=null) {
                String idanims = donnees.getJSONObject("etablissements").getJSONObject(departement).getJSONObject(idetab).getJSONObject("animateurs").keys().next();
                Animateur anim = new Animateur(donnees.getJSONObject("animateurs").getJSONObject(departement).getJSONObject(idanims));
                String animciviliteintitule = "Madame";
                switch (anim.getCivilite()) {
                    case "M" : animciviliteintitule = "Monsieur";break;
                    case "Mr" : animciviliteintitule = "Monsieur";break;
                    case "M." : animciviliteintitule = "Monsieur";break;
                }
                long insertedCivilite = DaneContract.addCivilite(ctx,anim.getCivilite(),animciviliteintitule);
                anim.setAnim(insertedDepartement,insertedCivilite);
                insertedanim = DaneContract.addAnimateur(ctx,anim.getAnim());
                Log.d(TAG, "animateur : " + anim.getAnim().getAsString("nom")+"---"+anim.getId()+"---"+insertedDepartement);
            }
            etab.setEtab(insertedville,insertedanim);
            long insertedetab = DaneContract.addEtablissement(ctx,etab.getEtab());
            Log.d(TAG, "etablissement : "+insertedetab+"----" + etab.getEtab().getAsString("nom")+"---"+insertedanim+"---"+insertedville);
            Map<String, Boolean> lespersonnel = etab.getPersonnel();
            if (lespersonnel!=null) {
                for ( String idpersonnel : lespersonnel.keySet() ) {
                    Personnel perso = new Personnel(donnees.getJSONObject("personnel").getJSONObject(departement).getJSONObject(idpersonnel));
                    String persociviliteintitule = "Madame";
                    switch (perso.getCivilite()) {
                        case "M" : persociviliteintitule = "Monsieur";break;
                        case "Mr" : persociviliteintitule = "Monsieur";break;
                        case "M." : persociviliteintitule = "Monsieur";break;
                    }
                    long insertedCivilite = DaneContract.addCivilite(ctx,perso.getCivilite(),persociviliteintitule);
                    perso.setPerso(insertedetab,insertedCivilite,true);
                    long insertedperso = DaneContract.addPersonnel(ctx,perso.getPerso());
                    Log.d(TAG, "personnel : "+insertedperso+"----" + perso.getPerso().getAsString("nom")+"---"+perso.getPerso().getAsString("statut"));
                }
            }
        }
    }

    public static void writeNewReferent(final Context mcontext, JSONObject referent) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(mcontext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, DaneContract.BASE_URL_NEW_REFERENT, referent, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Log.d(TAG,"référent : "+response);
                            Referent referentajoute = new Referent(response);
                            String referentciviliteintitule = "Madame";
                            switch (referentajoute.getCivilite()) {
                                case "M" : referentciviliteintitule = "Monsieur";break;
                                case "Mr" : referentciviliteintitule = "Monsieur";break;
                                case "M." : referentciviliteintitule = "Monsieur";break;
                            }
                            long insertedCivilite = DaneContract.addCivilite(mcontext,referentajoute.getCivilite(),referentciviliteintitule);
                            if (!response.getString("id").isEmpty()) {
                                referentajoute.setRefer(true,insertedCivilite);
                            }else {
                                referentajoute.setRefer(false,insertedCivilite);
                            }
                            DaneContract.addReferent(mcontext,referentajoute.getRefer());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "That didn't work!");
                    }
                });
        //        stringRequest.setTag(TAG);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(jsObjRequest);
    }

    public static void deleteReferent(final Context mcontext, JSONObject referent) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(mcontext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, DaneContract.BASE_URL_DELETE_REFERENT, referent, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Log.d(TAG,"retour + "+response);
                            if (response.getBoolean("removed")) {
//                                Log.d(TAG,"référent à supprimer : "+Long.parseLong(response.getString("id")));
                                Uri muri = DaneContract.ReferentEntry.buildReferentUri(Long.parseLong(response.getString("id")));
//                                int deletedUri = mcontext.getContentResolver().delete(muri, ReferentEntry._ID,new String[]{response.getString("id")});
                                int deletedUri = mcontext.getContentResolver().delete(muri, null,null);
//                                Log.d(TAG,"référent supprimé : "+deletedUri);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "That didn't work!");
                    }
                });
        //        stringRequest.setTag(TAG);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(jsObjRequest);
    }

    public static void DeleteReferentDialog(FragmentManager manager, String titre, String message, String idreferent, String idbasereferent, String tag) {
        DialogFragment referentFragment = ConfirmationReferentDialogFragment.newInstance(titre,message,idreferent,idbasereferent);
        referentFragment.show(manager, tag);
    }

    public static void AjoutReferentDialog(FragmentManager manager,Long idetab,String titre, String tag) {
        DialogFragment ajoutreferentFragment = ReferentDialogFragment.newInstance(idetab, Long.valueOf(0),titre);
        ajoutreferentFragment.show(manager, tag);
    }

    public static void EditerReferentDialog(FragmentManager manager,Long idreferent,String titre, String tag) {
        DialogFragment editerreferentFragment = ReferentDialogFragment.newInstance(Long.valueOf(0),idreferent,titre);
        editerreferentFragment.show(manager, tag);
    }

    public static void EditerPersonnelDialog(FragmentManager manager,Long idpersonnel,String titre, String tag) {
        DialogFragment editerpersonnelFragment = PersonnelDialogFragment.newInstance(Long.valueOf(0),idpersonnel,titre);
        editerpersonnelFragment.show(manager, tag);
    }
}
