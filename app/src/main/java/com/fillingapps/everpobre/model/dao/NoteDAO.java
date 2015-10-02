package com.fillingapps.everpobre.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fillingapps.everpobre.model.Note;
import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.db.DBHelper;

import java.lang.ref.WeakReference;
import java.util.Date;

import static com.fillingapps.everpobre.model.db.DBConstants.*;

public class NoteDAO implements DAOPersistable<Note>{

    private final WeakReference<Context> context;
    public static final String[] allColumns = {
            KEY_NOTE_TEXT,
            KEY_NOTE_CREATION_DATE,
            KEY_NOTE_MODIFICATION_DATE,
            KEY_NOTE_PHOTO_URL,
            KEY_NOTE_NOTEBOOK,
            KEY_NOTE_LATITUDE,
            KEY_NOTE_LONGITUDE,
            KEY_NOTE_HAS_COORDINATES,
            KEY_NOTE_ADDRESS,
    };

    public NoteDAO(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public long insert(@NonNull Note data) {
        if (data == null){
            return DBHelper.INVALID_ID;
        }
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;

        try {
            id = db.insert(TABLE_NOTEBOOK, null, getContentValues(data));
            // data.setId(id);

            // Hacemos el commit: setTransactionSuccessful()
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        dbHelper.close();

        return id;
    }

    public static ContentValues getContentValues(Note data) {

        if (data.getCreationDate() == null) {
            data.setCreationDate(new Date());
        }
        if (data.getModificationDate() == null) {
            data.setModificationDate(new Date());
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOTE_TEXT, data.getText());
        contentValues.put(KEY_NOTE_CREATION_DATE, DBHelper.convertDateToLong(data.getCreationDate()));
        contentValues.put(KEY_NOTE_MODIFICATION_DATE, DBHelper.convertDateToLong(data.getModificationDate()));
        contentValues.put(KEY_NOTE_PHOTO_URL, data.getPhotoUrl());
        contentValues.put(KEY_NOTE_NOTEBOOK, String.format("%d", data.getNotebook()));
        contentValues.put(KEY_NOTE_LATITUDE, String.format("%f", data.getLatitude()));
        contentValues.put(KEY_NOTE_LONGITUDE, String.format("%f", data.getLongitude()));

        Boolean hasCoordinates = data.isHasCoordinates();
        contentValues.put(KEY_NOTE_HAS_COORDINATES, String.format("%d", DBHelper.convertBooleanToInt(hasCoordinates)));
        contentValues.put(KEY_NOTE_ADDRESS, data.getAddress());

        return contentValues;
    }

    @Override
    public void update(long id, @NonNull Note data) {
        if (data == null){
            return;
        }
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();

        try {
            // Forma 1: mas intuitiva
//            db.update(TABLE_NOTE, getContentValues(data), KEY_NOTE_ID + "=" + id, null);
            // Forma 2: evitas la insercion de codigo SQL malicioso
            db.update(TABLE_NOTE, getContentValues(data), KEY_NOTE_ID + "=?", new String[]{ "" + id });

            // Hacemos el commit: setTransactionSuccessful()
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        dbHelper.close();
    }

    @Override
    public void delete(long id) {
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        if (id == DBHelper.INVALID_ID){
            // Borro todos los registros
            db.delete(TABLE_NOTE, null, null);
        }
        else{
            db.delete(TABLE_NOTE, KEY_NOTE_ID + "=?", new String[]{ "" + id });
        }

        db.close();
    }

    @Override
    public void delete(@NonNull Note data) {
        if (data != null){
            delete(data.getId());
        }
    }

    @Override
    public void deleteAll() {
        delete(DBHelper.INVALID_ID);
    }

    @Nullable
    @Override
    // Consulta de todos los registros
    public Cursor queryCursor() {
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        // Ordenados por fecha de insercion (a traves del KEY_NOTEBOOK_ID)
        Cursor cursor = db.query(TABLE_NOTE, allColumns, null, null, null, null, KEY_NOTE_ID);
        return cursor;
    }

    @Override
    public Note query(long id) {
        Note note = null;

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        final String whereClause = KEY_NOTE_ID + "=" + id;
        Cursor cursor = db.query(TABLE_NOTE, allColumns, whereClause, null, null, null, KEY_NOTE_ID);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                // Nos movemos al primer registro del cursor (inicialmente está en una posicion "beforeFirst")
                cursor.moveToFirst();

                Notebook notebook = new Notebook("");
                final String notebookId = cursor.getString(cursor.getColumnIndex(KEY_NOTE_NOTEBOOK));
                notebook.setId(Long.parseLong(notebookId));

                note = new Note(notebook, cursor.getString(cursor.getColumnIndex(KEY_NOTE_TEXT)));
                note.setId(cursor.getLong(cursor.getColumnIndex(KEY_NOTE_ID)));

                Long creationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTE_CREATION_DATE));
                Long modificationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTE_MODIFICATION_DATE));

                note.setCreationDate(DBHelper.convertLongToDate(creationDate));
                note.setModificationDate(DBHelper.convertLongToDate(modificationDate));

                //TODO: mapeo del resto de campos
            }
        }

        cursor.close();
        db.close();

        return note;
    }
}
