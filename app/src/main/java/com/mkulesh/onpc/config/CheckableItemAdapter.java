/*
 * Copyright (C) 2019. Mikhail Kulesh
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. You should have received a copy of the GNU General
 * Public License along with this program.
 */

package com.mkulesh.onpc.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mkulesh.onpc.R;

import java.util.ArrayList;
import java.util.List;

import androidx.preference.PreferenceManager;

class CheckableItemAdapter extends BaseAdapter
{
    private final LayoutInflater inflater;
    private final List<CheckableItem> items = new ArrayList<>();
    private final SharedPreferences preferences;
    private final String parameter;

    CheckableItemAdapter(Context context, String parameter)
    {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.parameter = parameter;
    }

    String getParameter()
    {
        return parameter;
    }

    void setItems(final List<CheckableItem> newItems)
    {
        for (CheckableItem d : newItems)
        {
            items.add(new CheckableItem(d));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return items.get(position).id;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent)
    {
        CheckableItemView view;
        if (convert == null)
        {
            view = (CheckableItemView) inflater.inflate(R.layout.checkable_item_view, parent, false);
        }
        else
        {
            view = (CheckableItemView) convert;
        }
        final CheckableItem d = items.get(position);
        view.setTag(d.code);
        view.setText(d.text);
        if (d.imageId > 0)
        {
            view.setImage(d.imageId);
        }
        return view;
    }

    void drop(int from, int to)
    {
        if (from != to && from < items.size() && to < items.size())
        {
            CheckableItem p = items.remove(from);
            items.add(to, p);
            CheckableItem.writeToPreference(preferences, parameter, items);
        }
        notifyDataSetChanged();
    }

    void setChecked(int pos, boolean checked)
    {
        if (pos < items.size())
        {
            CheckableItem p = items.get(pos);
            p.checked = checked;
            CheckableItem.writeToPreference(preferences, parameter, items);
        }
        notifyDataSetChanged();
    }
}
