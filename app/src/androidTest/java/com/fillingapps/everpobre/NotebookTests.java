package com.fillingapps.everpobre;

import android.test.AndroidTestCase;

import com.fillingapps.everpobre.model.Note;
import com.fillingapps.everpobre.model.Notebook;

public class NotebookTests extends AndroidTestCase {

    private final String name = "Test notebook title";
    private final String noteText = "Note text";

    public void testCanCreateANotebook() {
        // alt + cmd + f: para extraer algo a una variable (name)
        Notebook notebook = new Notebook(name);

        assertNotNull(notebook);
        assertEquals(name, notebook.getName());
    }

    public void testCanAddNotesToANotebook() {
        Notebook notebook = new Notebook(name);

        assertEquals(0, notebook.allNotes().size());

        Note note = new Note(notebook, noteText);
        notebook.addNote(note);

        assertEquals(1, notebook.allNotes().size());
    }

    public void testCanAddNoteToANotebookWithNoteText() {
        Notebook notebook = new Notebook(name);

        assertEquals(0, notebook.allNotes().size());

        notebook.addNote("my note text");

        assertEquals(1, notebook.allNotes().size());
    }

}
