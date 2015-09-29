package com.fillingapps.everpobre.model;

import java.lang.ref.WeakReference;
import java.util.Date;

public class Note {

    private long id;
    private String text;
    private Date creationDate;
    private Date modificationDate;
    private String photoUrl;
    private WeakReference<Notebook> notebook;
    private double longitude;
    private double latitude;
    private boolean hasCoordinates;
    private String address;

    public Note(Notebook notebook, String text) {
        this.notebook = new WeakReference<Notebook>(notebook);
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Notebook getNotebook() {
        return notebook.get();
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = new WeakReference<Notebook>(notebook);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isHasCoordinates() {
        return hasCoordinates;
    }

    public void setHasCoordinates(boolean hasCoordinates) {
        this.hasCoordinates = hasCoordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
