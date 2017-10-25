package com.bouami.danecreteil2017_cloud.Adapter;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by mbouami on 16/09/2017.
 */

public abstract class MyRecycleAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

        private static final String TAG = "MyRecycleAdapter";
        private boolean mDataValid;
        private int mRowIDColumn;

        private Class<T> mModelClass;
        protected Class<VH> mViewHolderClass;
        protected int mModelLayout;
        private Cursor mCursor;

        protected SparseIntArray mItemPositions;

    public MyRecycleAdapter(Class<T> mModelClass,@LayoutRes int mModelLayout, Class<VH> mViewHolderClass,Cursor cursor) {
        this.mCursor = cursor;
        this.mModelClass = mModelClass;
        this.mViewHolderClass = mViewHolderClass;
        this.mModelLayout = mModelLayout;
        setHasStableIds(true);
//        swapCursor(cursor);

    }
    protected Cursor getCursor()
    {
        return mCursor;
    }

    public void changeCursor(Cursor cursor)
    {
        Cursor old = swapCursor(cursor);
        if(old != null){
            old.close();
        }
    }


    public Cursor swapCursor(Cursor newCursor)
    {
/*        if(newCursor == mCursor){
            return null;
        }
        Cursor oldCursor = mCursor;
        if(oldCursor != null){
            if(mDataSetObserver != null){
                oldCursor.unregisterDataSetObserver(mDataSetObserver);
            }
        }
        mCursor = newCursor;
        if(newCursor != null){
            if(mDataSetObserver != null){
                newCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        }
        else{
            mRowIDColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;*/
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;

        } else {
            mRowIDColumn = -1;
            mDataValid = false;
        }

        setItemPositions();
        if (mDataValid){
            notifyDataSetChanged();
        }


        return oldCursor;
    }

    public void setItemPositions() {
        mItemPositions = null;

        if (mDataValid) {
            int count = mCursor.getCount();
            mItemPositions = new SparseIntArray(count);
            mCursor.moveToPosition(-1);
            while (mCursor.moveToNext()) {
                int rowId = mCursor.getInt(mRowIDColumn);
                int cursorPos = mCursor.getPosition();
                mItemPositions.append(rowId, cursorPos);
            }
        }
    }

    private DataSetObserver mDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated()
        {
            mDataValid = false;
            notifyDataSetChanged();
        }
    };

    public void cleanup() {

        if (mCursor!=null) mCursor.close();
    }

    @Override
    public int getItemCount() {
        if(mDataValid && mCursor != null){
            return mCursor.getCount();
        }
        else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position)
    {
/*        if(mDataValid && mCursor != null && mCursor.moveToPosition(position)){
            return mCursor.getLong(mRowIDColumn);
        }
        return RecyclerView.NO_ID;*/
        if (!mDataValid || !mCursor.moveToPosition(position)) {
            return super.getItemId(position);
        }
        int rowId = mCursor.getInt(mRowIDColumn);
        return rowId;
    }

//    public T getItem(int position) {
//        return mCursor.moveToPosition(position);
//    }

//    @Override
//    public long getItemId(int position) {
//        // http://stackoverflow.com/questions/5100071/whats-the-purpose-of-item-ids-in-android-listview-adapter
//        return mSnapshots.get(position).getId();
//    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
//        T model = getItem(position);
//        Log.d(TAG,"onBindViewHolder : "+position);
        if(!mDataValid){
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if(!mCursor.moveToPosition(position)){
            throw new IllegalStateException("couldn't move cursor to position " + position);
        };
        populateViewHolder(viewHolder, mCursor,position);
    }

    @Override
    public int getItemViewType(int position) {
        return mModelLayout;
    }

    protected abstract void populateViewHolder(VH viewHolder, Cursor mCursor,int position);
}
