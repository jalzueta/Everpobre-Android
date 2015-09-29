package com.fillingapps.everpobre.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notebook {

    private long id;
    private String name;
    private Date creationDate;
    private Date modificationDate;
    private List<Note> notes;

    public Notebook(String name) {
        this.name = name;
    }

    // lazy getter
    public List<Note> allNotes() {
        if (this.notes == null){
            notes = new ArrayList<Note>();
        }
        return this.notes;
    }

    /**
     * This method adds a non null Note
     * @param note the Note to add
     */
    // final: la nota no queremos que cambie, asi que nos cubrimos
    public void addNote(@NonNull final Note note){
        if (note != null){
            allNotes().add(note);
            note.setNotebook(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void addNote(String text) {
        Note note = new Note(this, text);
        allNotes().add(note);
    }
}
