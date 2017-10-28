package com.bouami.danecreteil2017_cloud.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.bouami.danecreteil2017_cloud.Adapter.DisciplinesAdapter;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by Mohammed on 27/10/2017.
 */

public class ReferentDialogFragment extends DialogFragment {
    private String TAG = "ReferentDialogFragment";
    private Long mEtablissementId;
    private String mDisciplineId;
    private Context mContext;

    private AutoCompleteTextView mDiscipline;

    public static ReferentDialogFragment newInstance(Long idetab) {

        ReferentDialogFragment f = new ReferentDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("idetab", idetab);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEtablissementId = getArguments().getLong("idetab",0);
        mContext = ReferentDialogFragment.this.getContext();
        mDisciplineId="";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.referent_dialog, null, false);
        mDiscipline = (AutoCompleteTextView) v.findViewById(R.id.liste_disciplines);
        Cursor listedisciplines = DaneContract.getListeDisciplines(mContext);
        Log.d(TAG, "listedisciplines le référent : "+listedisciplines.getCount());
        DisciplinesAdapter mDisciplineAdapter = new DisciplinesAdapter(mContext,listedisciplines ,0);
        mDiscipline.setAdapter(mDisciplineAdapter);
        mDiscipline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                mDisciplineId = DaneContract.getValueFromCursor(cursor,DaneContract.DisciplineEntry._ID);
            }
        });
        // Pass null as the parent view because its going in the dialog layout
        builder.setIcon(R.drawable.ic_settings_applications_black_24dp)
                .setTitle("Ajout d'un nouveau référent")
                .setView(inflater.inflate(R.layout.referent_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "enregistrer le référent : "+mDisciplineId);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
                        Log.d(TAG, "quitter : ");
                    }
                });
//        builder.setIcon(R.drawable.ic_settings_applications_black_24dp)
//                .setTitle("titre")
//                .setPositiveButton("enregistrer",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
////                                ((FragmentAlertDialog)getActivity()).doPositiveClick();
//                                Log.d(TAG, "enregistrer le référent : ");
//                            }
//                        }
//                )
//                .setNegativeButton("quitter",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
////                                ((FragmentAlertDialog)getActivity()).doNegativeClick();
//                                Log.d(TAG, "quitter : ");
//                            }
//                        }
//                );
        return builder.create();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.referent_dialog, container, false);
//        // Watch for button clicks.
//        Button buttonenregistrer = (Button)v.findViewById(R.id.enregistrer);
//        Button buttonquitter = (Button)v.findViewById(R.id.quitter);
//        buttonenregistrer.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // When button is clicked, call up to owning activity.
////                ((FragmentDialog)getActivity()).showDialog();
//                Log.d(TAG, "Ajout du référent : ");
//            }
//        });
//        return v;
//    }
}
