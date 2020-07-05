package com.example.admin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.admin.model.Adminmd;

import java.util.ArrayList;

public class AdminAdapter extends BaseAdapter {
    Context context;
    ArrayList<Adminmd> arrayListadmin;

    public AdminAdapter(Context context, ArrayList<Adminmd> arrayListadmin) {
        this.context = context;
        this.arrayListadmin = arrayListadmin;
    }

    @Override
    public int getCount() {
        return arrayListadmin.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListadmin.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
