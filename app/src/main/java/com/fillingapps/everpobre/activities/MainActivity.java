package com.fillingapps.everpobre.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.fragments.DataGridFragment;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;
import com.fillingapps.everpobre.providers.EverpobreProvider;
import com.fillingapps.everpobre.utils.Constants;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    DataGridFragment mDataGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataGridFragment = (DataGridFragment) getFragmentManager().findFragmentById(R.id.grid_fragment);

        LoaderManager loader = getLoaderManager();
        loader.initLoader(0, null, this);

        // Esta búsqueda directa se hace en el hilo principal y puede llegar a bloquear la pnatalla
        // Cursor cursor = new NotebookDAO(this).queryCursor();


//        insertNotebookStubs(10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
        // A segundo plano con un Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = new NotebookDAO(this).queryCursor();
                mDataGridFragment.setCursor(cursor);

                // Cambio al hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDataGridFragment.refreshData();
                    }
                });
            }
        });
        thread.start()
        ;*/

        /*
        Cursor cursor = new NotebookDAO(this).queryCursor();
        mDataGridFragment.setCursor(cursor);
        mDataGridFragment.refreshData();
        */
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
        else if (id == R.id.action_open_map) {

            Intent i = new Intent(this, MapActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Creamos el cursor loader
        CursorLoader loader = new CursorLoader(this, EverpobreProvider.NOTEBOOKS_URI, NotebookDAO.allColumns, null, null, null);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Entra aquí cuando se ha terminado de cargar el cursor con datos en segundo plano
        mDataGridFragment.setCursor(cursor);
        mDataGridFragment.setIdLayout(R.layout.fragment_data_grid);
        mDataGridFragment.setIdGridView(R.id.grid_view);
        mDataGridFragment.refreshData();

        mDataGridFragment.setListener(new DataGridFragment.OnDataGridFragmentClickListener() {
            @Override
            public void dataGridElementClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ShowNotebookActivity.class);
                startActivity(i);
            }

            @Override
            public void dataGridElementLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotebookDAO notebookDAO = new NotebookDAO(MainActivity.this);
                // id, para un CursorAdapter es la id de la BD.
                Notebook notebook = notebookDAO.query(id);

                Intent i = new Intent(MainActivity.this, EditNotebookActivity.class);
                i.putExtra(Constants.intent_key_notebook, notebook);

                startActivity(i);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se suele avisar al adapter para que se recargue
    }
}
