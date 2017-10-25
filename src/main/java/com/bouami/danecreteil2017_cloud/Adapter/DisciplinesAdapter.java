package com.bouami.danecreteil2017_cloud.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by Mohammed on 23/10/2017.
 */

public class DisciplinesAdapter extends CursorAdapter implements Filterable {

    private static final String LOG = DisciplinesAdapter.class.getSimpleName();
    private ContentResolver mContent;
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_DISCILPLINE_EN_COURS = 0;
    private static final int VIEW_TYPE_DISCILPLINE_LES_AUTRES = 1;

    public DisciplinesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContent = context.getContentResolver();
    }

    public static class ViewHolder {
        public final TextView nomView;

        public ViewHolder(View view) {
            nomView = (TextView) view.findViewById(R.id.list_disciplines_textview);
        }
    }

    private String convertCursorRowToUXFormat(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(DaneContract.DisciplineEntry.COLUMN_DISCIPLINE_NOM));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        layoutId = R.layout.list_item_disciplines;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        DisciplinesAdapter.ViewHolder viewHolder = new DisciplinesAdapter.ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DisciplinesAdapter.ViewHolder viewHolder = (DisciplinesAdapter.ViewHolder) view.getTag();
        viewHolder.nomView.setText(convertCursorRowToUXFormat(cursor));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_DISCILPLINE_EN_COURS : VIEW_TYPE_DISCILPLINE_LES_AUTRES;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public String convertToString(Cursor cursor) {
        //returns string inserted into textview after item from drop-down list is selected.
        return cursor.getString(cursor.getColumnIndex(DaneContract.DisciplineEntry.COLUMN_DISCIPLINE_NOM));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        FilterQueryProvider filter = getFilterQueryProvider();
        if (filter != null) {
            return filter.runQuery(constraint);
        }
        if (constraint!=null) {
            Cursor searchcursor = DaneContract.rechercherDiscipline(mContext,constraint.toString());
            return searchcursor;
        }
        return null;
    }

}
