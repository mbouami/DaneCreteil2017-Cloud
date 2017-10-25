package com.bouami.danecreteil2017_cloud;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bouami.danecreteil2017_cloud.Adapter.DisciplinesAdapter;
import com.bouami.danecreteil2017_cloud.Adapter.ReferentsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Referent;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.ViewHolder.ReferentViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

public class NewReferentActivity extends AppCompatActivity {

    private static final String TAG = "NewReferentActivity";
    private static final String REQUIRED = "Required";
    public static final String EXTRA_ETABLISSEMENT_ID = "etablissement_id";
    private Long mEtablissementId;
    String mDisciplineId;
    private RadioGroup mGenreReferent;
    private EditText mNomReferent;
    private EditText mPrenomReferent;
    private EditText mTelReferent;
    private EditText mMailReferent;
    private AutoCompleteTextView mDiscipline;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_referent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEtablissementId = getIntent().getLongExtra(EXTRA_ETABLISSEMENT_ID,0);
        if (mEtablissementId == 0) {
            throw new IllegalArgumentException("Must pass EXTRA_ETABLISSEMENT_KEY");
        }
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_add_new_referent);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReferent();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mGenreReferent = (RadioGroup) findViewById(R.id.genre);
        mNomReferent = (EditText) findViewById(R.id.nom);
        mPrenomReferent = (EditText) findViewById(R.id.prenom);
        mTelReferent = (EditText) findViewById(R.id.tel);
        mMailReferent = (EditText) findViewById(R.id.email);
        DisciplinesAdapter mDisciplineAdapter = new DisciplinesAdapter(this,DaneContract.getListeDisciplines(this),0);
        mDiscipline = (AutoCompleteTextView) findViewById(R.id.liste_disciplines);
        mDiscipline.setAdapter(mDisciplineAdapter);
//        mDiscipline.setThreshold(3);
        mDiscipline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                mDisciplineId = DaneContract.getValueFromCursor(cursor,DaneContract.DisciplineEntry._ID);
            }
        });
    }

    private void submitReferent() {
        final String genre = ((RadioButton) findViewById(mGenreReferent.getCheckedRadioButtonId())).getText().toString();
        final String nom = mNomReferent.getText().toString();
        final String prenom = mPrenomReferent.getText().toString();
        final String tel = mTelReferent.getText().toString();
        final String email = mMailReferent.getText().toString();
//        final String discipline = mDiscipline.getAdapter().getItem(1).toString();

        if (TextUtils.isEmpty(nom)) {
            mNomReferent.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(prenom)) {
            mPrenomReferent.setError(REQUIRED);
            return;
        }
        setEditingEnabled(false);
        Toast.makeText(this, "Enregistrement en cours...", Toast.LENGTH_SHORT).show();
        JSONObject jsonreferent = new JSONObject();
        try {
            jsonreferent.put("nom",nom);
            jsonreferent.put("prenom",prenom);
            jsonreferent.put("genre",genre);
            jsonreferent.put("tel",tel);
            jsonreferent.put("email",email);
            jsonreferent.put("statut","1");
            jsonreferent.put("discipline",mDisciplineId);
            jsonreferent.put("etablissement",mEtablissementId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        writeNewReferent(jsonreferent);
        DaneContract.writeNewReferent(this,jsonreferent);
    }

//    private void writeNewReferent(JSONObject referent) {
////        Log.d(TAG, "writeNewReferent : "+referent.toString());
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.POST, DaneContract.BASE_URL_NEW_REFERENT, referent, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.d(TAG,"référent : "+response);
//                            Referent referentajoute = new Referent(response);
//                            Log.d(TAG,"référent : "+referentajoute);
//                            if (!response.getString("id").isEmpty()) {
//                                referentajoute.setRefer(true);
//                            }else {
//                                referentajoute.setRefer(false);
//                            }
//                            DaneContract.addReferent(getBaseContext(),referentajoute.getRefer());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////                        Log.d(TAG, "onResponse : "+response);
////                        try {
////                            MainActivity.mesdonneesjson = response;
////                            MainActivity.mesdonneesjson.getJSONObject("referentsnumeriques").getJSONObject(MainActivity.departementencours).put(response.getString("id"),response);
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        };
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d(TAG, "That didn't work!");
//                    }
//                });
//        //        stringRequest.setTag(TAG);
//        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
//                0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
//        mRequestQueue.add(jsObjRequest);
//
//    }

    private void setEditingEnabled(boolean enabled) {
        mNomReferent.setEnabled(enabled);
        mPrenomReferent.setEnabled(enabled);
        mTelReferent.setEnabled(enabled);
        mMailReferent.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }
}
