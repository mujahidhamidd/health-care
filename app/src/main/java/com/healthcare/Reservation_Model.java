package com.healthcare;

public class Reservation_Model {

    public String nameD ;
    public String nameC ;
    public String time;
    public  String date;
    public int id;
    public int id_patient;

    public Reservation_Model() {
    }

    public Reservation_Model(int id,String nameD, String nameC, String time, String date, int id_patient) {
        this.nameD = nameD;
        this.nameC = nameC;
        this.time = time;
        this.date = date;
        this.id_patient = id_patient;
        this.id = id;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameD() {
        return nameD;
    }

    public void setNameD(String nameD) {
        this.nameD = nameD;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
