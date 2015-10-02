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

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataGridAdapter extends CursorAdapter{

    @Bind(R.id.txt_notebook_name) TextView itemName;
    @Bind(R.id.icon_notebook) ImageView itemIcon;

    private LayoutInflater layoutInflater;
    private Cursor mCursor;

    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);
        this.mCursor = c;
    }

    @Override
    // Creo la vista: esto se llama cuando de verdad hace falta una nueva vista.
    // Al ser un CursorAdapter, el se encarga de la reutilizacion de las vistas,
    // No es necesario un patron view-holder
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_notebook, parent, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    // Pinto la vista
    public void bindView(View view, Context context, Cursor cursor) {
        Notebook notebook = NotebookDAO.notebookFromCursor(mCursor);
        itemName.setText(notebook.getName());
    }
}
