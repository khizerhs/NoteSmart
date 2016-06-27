package com.khizerhasan.notesmart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KhizerHasan on 4/10/2016.
 */
public class DatabaseConnector extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME= "NotesData.db";

    public DatabaseConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            final String CREATE_TABLE_NOTES = "CREATE TABLE IF NOT EXISTS tbl_notes ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "CONTACT TEXT,"
                    + "NAME TEXT,"
                    + "NOTE TEXT,"
                    + "TIME TEXT);";
            db.execSQL(CREATE_TABLE_NOTES);
        }
        catch(Exception e){
            Log.d("Database Error",e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DoNothing
    }

    public List<NoteItem> getAllNotes(){
        List<NoteItem> notesItems = new ArrayList<NoteItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            final String GET_TABLE_NOTES = "SELECT * FROM tbl_notes";
            Cursor c = db.rawQuery(GET_TABLE_NOTES,null);
            c.moveToFirst();
            while(!c.isAfterLast()) {
                NoteItem i = new NoteItem();
                i.setId(c.getString(0));
                i.setNumber(c.getString(1));
                i.setName(c.getString(2));
                i.setNote(c.getString(3));

                c.moveToNext();
                notesItems.add(i);
            }
            c.close();
            db.close();
        } catch(Exception e){
            Log.d("DatabaseException", e.getMessage());
        }
        return notesItems;
    }

    public List<NoteItem> getNotesFor(String number){
        List<NoteItem> notesItems = new ArrayList<NoteItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            final String GET_TABLE_NOTES = "SELECT * FROM tbl_notes WHERE CONTACT = '"+number+"'";
            Cursor c = db.rawQuery(GET_TABLE_NOTES,null);
            c.moveToFirst();
            while(!c.isAfterLast()) {
                NoteItem i = new NoteItem();
                i.setId(c.getString(0));
                i.setNumber(c.getString(1));
                i.setName(c.getString(2));
                i.setNote(c.getString(3));
                c.moveToNext();
                notesItems.add(i);
            }
            c.close();
            db.close();
        } catch(Exception e){
            Log.d("DatabaseException", e.getMessage());
        }
        return notesItems;
    }

    public int saveData(String number, String name, String noteContent, String time){
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql =
                    "INSERT INTO tbl_notes (CONTACT, NAME, NOTE, TIME) VALUES('"+number+"','"+name+"','"+noteContent+"','"+time+"')";
            db.execSQL(sql);
            db.close();
            return 1;
        }
        catch (Exception e) {
            Log.d("Exception",e.getMessage());
            return 0;
        }
    }

    public int updateNote(String updatedNoteContent,String id){

        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql =
                    "UPDATE tbl_notes SET NOTE = '"+updatedNoteContent+"' WHERE ID ="+Integer.parseInt(id);
            db.execSQL(sql);
            db.close();
            return 1;
        }
        catch (Exception e) {
            Log.d("Exception",e.getMessage());
            return 0;
        }
    }

    public int deleteThis(String id){
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql =
                    "DELETE FROM tbl_notes WHERE ID ="+Integer.parseInt(id);
            db.execSQL(sql);
            db.close();
            return 1;
        }
        catch (Exception e) {
            Log.d("Exception",e.getMessage());
            return 0;
        }
    }
}
