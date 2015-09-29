package com.fillingapps.everpobre;

import android.test.AndroidTestCase;

import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.dao.NotebookDAO;
import com.fillingapps.everpobre.model.db.DBHelper;

public class NotebookDAOTests extends AndroidTestCase{

    public void testInsertNullNotebookReturnsInvalidId() {
        Notebook notebook = null;

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertNotebookWithNullNameReturnsInvalidId() {
        Notebook notebook = new Notebook("");
        notebook.setName(null);

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertNotebookReturnsValidId(){
        Notebook notebook = new Notebook("Test title");

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        long id = notebookDAO.insert(notebook);

        assertTrue(id > 0);
    }
}
