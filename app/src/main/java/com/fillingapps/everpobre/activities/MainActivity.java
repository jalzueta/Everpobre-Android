package com.fillingapps.everpobre.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.fragments.DataGridFragment;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;

public class MainActivity extends AppCompatActivity {

    DataGridFragment mDataGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataGridFragment = (DataGridFragment) getFragmentManager().findFragmentById(R.id.grid_fragment);

//        insertNotebookStubs(10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDataGridFragment.refreshData();
    }

    private void insertNotebookStubs(final int notebooksToInsert) {

        NotebookDAO notebookDAO = new NotebookDAO(this);

        for (int i = 0; i < notebooksToInsert; i++){
            final Notebook notebook = new Notebook(String.format("Test title %d", i));
            final long id = notebookDAO.insert(notebook);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; //true: se muestra el menu; false: no se muestra el menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_notebook) {

            Intent i = new Intent(this, EditNotebookActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
