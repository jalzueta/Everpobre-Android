package com.fillingapps.everpobre.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fillingapps.everpobre.R;
import com.fillingapps.everpobre.model.Note;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;
import com.fillingapps.everpobre.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditNotebookActivity extends AppCompatActivity {

    private Notebook mNotebook;

    enum EditMode {
        ADDING,
        EDITING,
        DELETING
    }
    EditMode editMode;

    @Bind(R.id.txt_notebook_name) EditText txtNotebookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notebook);

        ButterKnife.bind(this);

        Intent i = getIntent();
        mNotebook = i.getParcelableExtra(Constants.intent_key_notebook);
        if (mNotebook == null) {
            editMode = EditMode.ADDING;
        } else {
            editMode = EditMode.EDITING;
            txtNotebookName.setText(mNotebook.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_notebook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        switch (id) { //switch exhaustivos --> Swift (se comprueba el caso por defecto)
            case R.id.action_save_notebook:
                saveNotebook();
                break;

            case R.id.action_delete_notebook:
                deleteNotebook();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNotebook() {

    }

    private void saveNotebook() {
        final String notebookName = txtNotebookName.getText().toString();
        if (notebookName.isEmpty()) {
            return;
        }
        final NotebookDAO notebookDAO = new NotebookDAO(this);

        if (editMode == EditMode.ADDING) {
            final Notebook notebookToAdd = new Notebook(notebookName);
            notebookDAO.insert(notebookToAdd);
        } else if (editMode == EditMode.EDITING) {
            mNotebook.setName(notebookName);
            notebookDAO.update(mNotebook.getId(), mNotebook);
        }

        finish();
    }
}
