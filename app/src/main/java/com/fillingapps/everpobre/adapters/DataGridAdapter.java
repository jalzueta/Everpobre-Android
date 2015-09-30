package com.fillingapps.everpobre.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;

public class DataGridAdapter extends CursorAdapter{

    private LayoutInflater layoutInflater;

    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    // Como el cellForRowAtIndexPath
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.item_notebook, parent);
        return v;
    }

    @Override
    // Rellena de datos la celda: el cursor está posicionado en el registro que le toca pintar
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView itemIcon = (ImageView) view.findViewById(R.id.icon_notebook);
        TextView itemName = (TextView) view.findViewById(R.id.txt_notebook_name);

        Notebook notebook = NotebookDAO.notebookFromCursor(cursor);

        itemName.setText(notebook.getName());
    }
}
