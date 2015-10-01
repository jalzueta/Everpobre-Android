package com.fillingapps.everpobre.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.activities.EditNotebookActivity;
import com.fillingapps.everpobre.adapters.DataGridAdapter;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;
import com.fillingapps.everpobre.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataGridFragment extends Fragment {

    GridView gridView;
    DataGridAdapter adapter;
    private Cursor cursor;

    // Constructor que se usa para cuando se instancia un fragment directamente en un XML de layout
    public DataGridFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = (GridView) getActivity().findViewById(R.id.grid_view);

        refreshData();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click", 2000).show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "long click", 2000).show();

                Intent i = new Intent(getActivity(), EditNotebookActivity.class);

                NotebookDAO notebookDAO = new NotebookDAO(getActivity());
                // id, para un CursorAdapter es la id de la BD.
                Notebook notebook = notebookDAO.query(id);
                i.putExtra(Constants.intent_key_notebook, notebook);

                startActivity(i);

                // Este return a "false" le da paso al evento "onClick"
                return true;
            }
        });
    }

    public void refreshData() {
        cursor = new NotebookDAO(getActivity()).queryCursor();
        cursor.moveToFirst();
        adapter = new DataGridAdapter(getActivity(), cursor);
        gridView.setAdapter(adapter);
    }
}
