package com.fillingapps.everpobre.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.adapters.DataGridAdapter;
import com.fillingapps.everpobre.model.dao.NotebookDAO;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = (GridView) getActivity().findViewById(R.id.grid_view);
        cursor = new NotebookDAO(getActivity()).queryCursor();
        adapter = new DataGridAdapter(getActivity(), cursor);
        gridView.setAdapter(adapter);
    }
}
