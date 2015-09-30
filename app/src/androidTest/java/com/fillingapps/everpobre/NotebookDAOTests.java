package com.fillingapps.everpobre;

import android.database.Cursor;
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

    public void testQueryAllNotebooks() {
        insertNotebookStubs(10);

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        final Cursor cursor = notebookDAO.queryCursor();
        final int notebookCount = cursor.getCount();

        assertTrue(notebookCount > 9);
    }

    private void insertNotebookStubs(final int notebooksToInsert) {

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        for (int i = 0; i < notebooksToInsert; i++){
            final Notebook notebook = new Notebook(String.format("Test title %d", i));
            final long id = notebookDAO.insert(notebook);
        }
    }

    public void testDeleteAllNotebooks() {
        insertNotebookStubs(10);

        final NotebookDAO notebookDAO = new NotebookDAO(getContext());
        notebookDAO.deleteAll();

        final Cursor cursor = notebookDAO.queryCursor();
        final int notebookCount = cursor.getCount();

        assertEquals(0, notebookCount);
    }

    public void testUpdateOneNotebook() {

        final NotebookDAO notebookDAO = new NotebookDAO(getContext());
        final Notebook notebook = new Notebook("Change me if you dare");

        final long id = notebookDAO.insert(notebook);

        final Notebook originalNotebookCopy = notebookDAO.query(id);
        assertEquals("Change me if you dare", originalNotebookCopy.getName());

        originalNotebookCopy.setName("Challenge accepted");
        notebookDAO.update(id, originalNotebookCopy);

        final Notebook afterNotebookCopy = notebookDAO.query(id);
        assertEquals("Challenge accepted", afterNotebookCopy.getName());
    }
}
