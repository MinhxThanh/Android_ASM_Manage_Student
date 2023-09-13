package pro.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pro.edu.R;
import pro.edu.model.Classes;

public class ClassesAdapter extends BaseAdapter {
    private Context context;
    private List<Classes> list;

    public ClassesAdapter(Context context, List<Classes> list) {
        this.context = context;
        this.list = list;
    }

    public ClassesAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_classes_item_list_view, null);
        }
        TextView tvCode = (TextView) view.findViewById(R.id.tvClassCode);
        TextView tvName = (TextView) view.findViewById(R.id.tvClassName);

        Classes classes = list.get(i);
        tvCode.setText("Code: " + classes.getCode());
        tvName.setText("Name: " + classes.getName());

        return view;
    }
}
