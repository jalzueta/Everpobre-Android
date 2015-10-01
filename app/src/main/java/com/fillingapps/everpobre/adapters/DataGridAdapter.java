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

    private LayoutInflater layoutInflater;
    private Cursor mCursor;

    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);
        this.mCursor = c;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        }
        // si llega a "null" es porque no es una reutilizacion
        else {
            view = layoutInflater.inflate(R.layout.item_notebook, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Notebook notebook = NotebookDAO.notebookFromCursor(mCursor);
        holder.itemName.setText(notebook.getName());

        return view;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    // El patron View-ViewHolder sirve para no tener que hacer el hinflado de las celdas para todas las celdas,
    // sino que se reaprovechan, como el iOS (a partir de Lollipop ya lo hace solito)
    // El poner "static", la aislamos completamente de "DataGridAdapter"
    static class ViewHolder {
        @Bind(R.id.txt_notebook_name) TextView itemName;
        @Bind(R.id.icon_notebook) ImageView itemIcon;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
