package com.khizerhasan.notesmart;

/**
 * Created by KhizerHasan on 4/9/2016.
 */
public class NoteItem {
    private String name;
    private String number;
    private String note;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number= number;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
