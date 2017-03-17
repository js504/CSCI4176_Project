package in.geobullet.csci_4176_project.db.Classes;

import java.util.Date;


/* Class that corresponds to the Board database table */

public class Board {

    private int id;
    private Date created;
    private int createdByUserId;
    private String name;
    private Date expirationDate;
    private int radiusInMeters;
    private double longitude;
    private double latitude;

    public Board() {

    }

    public Board(int id, Date created, int createdByUserId, String name, Date expirationDate, int radiusInMeters, double longitude, double latitude) {
        this.id = id;
        this.created = created;
        this.createdByUserId = createdByUserId;
        this.name = name;
        this.expirationDate = expirationDate;
        this.radiusInMeters = radiusInMeters;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(int radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
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

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", created=" + created +
                ", createdByUserId=" + createdByUserId +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", radiusInMeters=" + radiusInMeters +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
