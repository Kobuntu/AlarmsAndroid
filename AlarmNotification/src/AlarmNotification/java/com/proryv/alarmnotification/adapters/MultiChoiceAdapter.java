package com.proryv.alarmnotification.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;

import com.proryv.alarmnotification.R;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Ig on 07.07.13.
 */
public class MultiChoiceAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener, SearchView.OnQueryTextListener, Filterable {

    private ArrayList<MultiChoiceElement> _originalItems;
    private ArrayList<MultiChoiceElement> _items;
    private Context _context;
    public EnumMultiChoiceType multiChoiceType;

    public MultiChoiceAdapter(ArrayList<MultiChoiceElement> items, Context context, EnumMultiChoiceType multiChoiceType)
    {
        _originalItems = items;
        _items = new ArrayList<MultiChoiceElement>(_originalItems);
        _context = context;
        this.multiChoiceType = multiChoiceType;
    }


    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        MultiChoiceElement item = _items.get(position);
        if (item != null)
        {
            return item.Id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(_context);
            view = inflater.inflate(R.layout.item_multichoice, null);
        }

        MultiChoiceElement currItem = _items.get(position);
        if (currItem != null)
        {
            CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
            cb.setText(currItem.Text);
            cb.setTag(currItem);
            cb.setChecked(currItem.IsChecked);
            cb.setOnCheckedChangeListener(this);
            ImageView image = (ImageView)view.findViewById(R.id.imageView);
            if (currItem.MultiChoiceType == EnumMultiChoiceType.Alarm)
            {
                image.setVisibility(View.VISIBLE);
                AlarmHelper.setAlarmImage(image, currItem.Id);
            }
            else
            {
                image.setVisibility(View.GONE);
            }
        }

        return view;
    }

    public void selectDeselectAll(Boolean isCheck)
    {
        if (_items == null || _items.size() == 0) return;

        for(MultiChoiceElement me : _items)
        {
            me.IsChecked = isCheck;
        }

        notifyDataSetChanged();
    }

    public Vector<Integer> getSelectedItems()
    {
        if (_originalItems == null) return null;

        Vector<Integer> result = new Vector<Integer>();

        for (MultiChoiceElement me : _originalItems)
        {
            if (me.IsChecked)
            {
                result.add(me.Id);
            }
        }

        return result;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        MultiChoiceElement me = (MultiChoiceElement)compoundButton.getTag();
        if (me!=null)
        {
            me.IsChecked = b;
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults results) {

                _items.clear();
                ArrayList<MultiChoiceElement> listSource = (ArrayList<MultiChoiceElement>) results.values;
                _items.addAll(listSource);
                notifyDataSetChanged();
            }

            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
                android.widget.Filter.FilterResults results = new android.widget.Filter.FilterResults();

                if (_originalItems != null && _originalItems.size() > 0)
                {
                    String filter;
                    ArrayList<MultiChoiceElement> filteredItems = new ArrayList<MultiChoiceElement>();
                    if (constraint != null && !(filter = constraint.toString().toLowerCase()).isEmpty())
                    {
                        for(MultiChoiceElement item : _originalItems)
                        {
                            if (item.Text!=null && !item.Text.isEmpty())
                            {
                                if (item.Text.toLowerCase().indexOf(filter) > -1)
                                {
                                    filteredItems.add(item);
                                }
                            }
                        }
                    }
                    else
                    {
                        filteredItems.addAll(_originalItems);
                    }

                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }

                return results;
            }
        };
        return filter;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setF(s);
        if (!getIsUpdated())
        {
            upDateFilter();
        }
        return true;
    }

    String _f;
    synchronized String getF()
    {
        return _f;
    }

    synchronized void setF(String f)
    {
        _f = f;
    }

    boolean _isUpdated;
    synchronized boolean getIsUpdated()
    {
        return _isUpdated;
    }
    synchronized void  setIsUpdated(boolean isUpdated)
    {
        _isUpdated = isUpdated;
    }

    private void upDateFilter()
    {
        setIsUpdated(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }
                if (_context!=null)
                {
                    ((Activity)_context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFilter().filter(getF());
                            setIsUpdated(false);
                        }
                    });
                }
            }
        }).start();


    }
}

