package com.fillingapps.everpobre.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fillingapps.everpobre.model.Notebook;
import com.fillingapps.everpobre.model.db.DBHelper;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import static com.fillingapps.everpobre.model.db.DBConstants.*;

public class NotebookDAO implements DAOPersistable<Notebook>{

    private final WeakReference<Context> context;
    public static final String[] allColumns = {
            KEY_NOTEBOOK_ID,
            KEY_NOTEBOOK_NAME,
            KEY_NOTEBOOK_CREATION_DATE,
            KEY_NOTEBOOK_MODIFICATION_DATE,
    };

    public NotebookDAO(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public long insert(@NonNull Notebook data) {
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

    private static ContentValues getContentValues(Notebook data) {

        if (data.getCreationDate() == null) {
            data.setCreationDate(new Date());
        }
        if (data.getModificationDate() == null) {
            data.setModificationDate(new Date());
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOTEBOOK_NAME, data.getName());
        contentValues.put(KEY_NOTEBOOK_CREATION_DATE, DBHelper.convertDateToLong(data.getCreationDate()));
        contentValues.put(KEY_NOTEBOOK_MODIFICATION_DATE, DBHelper.convertDateToLong(data.getModificationDate()));

        return contentValues;
    }

    @Override
    public void update(long id, @NonNull Notebook data) {
        if (data == null){
            return;
        }
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();

        try {
            // Forma 1: mas intuitiva
//            db.update(TABLE_NOTEBOOK, getContentValues(data), KEY_NOTEBOOK_ID + "=" + id, null);
            // Forma 2: evitas la insercion de codigo SQL malicioso
            db.update(TABLE_NOTEBOOK, getContentValues(data), KEY_NOTEBOOK_ID + "=?", new String[]{ "" + id });

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
            db.delete(TABLE_NOTEBOOK, null, null);
        }
        else{
            db.delete(TABLE_NOTEBOOK, KEY_NOTEBOOK_ID + "=?", new String[]{ "" + id });
        }

        db.close();
    }

    @Override
    public void delete(@NonNull Notebook data) {
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
        Cursor cursor = db.query(TABLE_NOTEBOOK, allColumns, null, null, null, null, KEY_NOTEBOOK_ID);
        return cursor;
    }

    @Override
    public Notebook query(long id) {
        Notebook notebook = null;

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        final String whereClause = KEY_NOTEBOOK_ID + "=" + id;
        Cursor cursor = db.query(TABLE_NOTEBOOK, allColumns, whereClause, null, null, null, KEY_NOTEBOOK_ID);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                // Nos movemos al primer registro del cursor (inicialmente está en una posicion "beforeFirst")
                cursor.moveToFirst();

                notebook = notebookFromCursor(cursor);
            }
        }

        cursor.close();
        db.close();

        return notebook;
    }

    @NonNull
    public static Notebook notebookFromCursor(Cursor cursor) {
        Notebook notebook;
        notebook = new Notebook(cursor.getString(cursor.getColumnIndex(KEY_NOTEBOOK_NAME)));
        notebook.setId(cursor.getLong(cursor.getColumnIndex(KEY_NOTEBOOK_ID)));

        Long creationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTEBOOK_CREATION_DATE));
        Long modificationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTEBOOK_MODIFICATION_DATE));

        notebook.setCreationDate(DBHelper.convertLongToDate(creationDate));
        notebook.setModificationDate(DBHelper.convertLongToDate(modificationDate));
        return notebook;
    }


}
