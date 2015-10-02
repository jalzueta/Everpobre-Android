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
import com.fillingapps.everpobre.activities.ShowNotebookActivity;
import com.fillingapps.everpobre.adapters.DataGridAdapter;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;
import com.fillingapps.everpobre.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataGridFragment extends Fragment {

    public interface OnDataGridFragmentClickListener {
        public void dataGridElementClick(AdapterView<?> parent, View view, int position, long id);
        public void dataGridElementLongClick(AdapterView<?> parent, View view, int position, long id);
    }

    GridView gridView;
    DataGridAdapter adapter;
    private Cursor cursor;
    private int idLayout = R.layout.fragment_data_grid;
    private int idGridView;

    // TODO: hacer WeakReference el listener
    private OnDataGridFragmentClickListener listener;

    public static DataGridFragment createDataGridFragment(Cursor cursor, int idLayout, int idGridView) {
        DataGridFragment fragment = new DataGridFragment();
        fragment.cursor = cursor;
        fragment.idLayout = idLayout;
        fragment.idGridView = idGridView;

        return fragment;
    }

    // Constructor que se usa para cuando se instancia un fragment directamente en un XML de layout
    public DataGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(idLayout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = (GridView) getActivity().findViewById(idGridView);

        if (gridView != null){
            refreshData();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (listener != null){
                        listener.dataGridElementClick(parent, view, position, id);
                    }
                }
            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listener != null) {
                        listener.dataGridElementLongClick(parent, view, position, id);
                    }

                    // Este return a "false" le da paso al evento "onClick"
                    return true;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData() {
        if (cursor == null) {
            return;
        }
        adapter = new DataGridAdapter(getActivity(), cursor);

        gridView = (GridView) getActivity().findViewById(idGridView);
        gridView.setAdapter(adapter);
    }

    public OnDataGridFragmentClickListener getListener() {
        return listener;
    }

    public void setListener(OnDataGridFragmentClickListener listener) {
        this.listener = listener;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public int getIdLayout() {
        return idLayout;
    }

    public void setIdLayout(int idLayout) {
        this.idLayout = idLayout;
    }

    public int getIdGridView() {
        return idGridView;
    }

    public void setIdGridView(int idGridView) {
        this.idGridView = idGridView;
    }
}
