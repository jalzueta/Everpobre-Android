package com.fillingapps.everpobre;

import android.test.AndroidTestCase;

import com.fillingapps.everpobre.model.Note;
import com.fillingapps.everpobre.model.Notebook;

public class NoteTests extends AndroidTestCase{

    public void testCanCreateANote() {
        Note note = new Note(new Notebook("notebook"), "Note test");

        assertNotNull(note);
    }

}
