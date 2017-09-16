package com.bouami.danecreteil2017_cloud.Adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
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

        private List<T> mSnapshots;
        private Class<T> mModelClass;
        protected Class<VH> mViewHolderClass;
        protected int mModelLayout;

    public MyRecycleAdapter(Class<T> mModelClass,@LayoutRes int mModelLayout, Class<VH> mViewHolderClass,List<T> mSnapshots) {
        this.mSnapshots = mSnapshots;
        this.mModelClass = mModelClass;
        this.mViewHolderClass = mViewHolderClass;
        this.mModelLayout = mModelLayout;
    }

    public void cleanup() {
        mSnapshots.clear();
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

    public T getItem(int position) {
        return mSnapshots.get(position);
    }

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
        T model = getItem(position);
        populateViewHolder(viewHolder, model, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mModelLayout;
    }

    protected abstract void populateViewHolder(VH viewHolder, T model, int position);
}
