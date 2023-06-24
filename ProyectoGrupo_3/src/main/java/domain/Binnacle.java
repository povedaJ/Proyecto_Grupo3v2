package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Binnacle implements Serializable {

    private int id;

    private static int autoId;// id autogenerado

    private LocalDateTime dateHour;

    private String user;

    private String Description;

    public Binnacle(LocalDateTime dateHour, String user, String description) {
        this.id= ++autoId;
        this.dateHour = dateHour;
        this.user = user;
        Description = description;
    }

    public Binnacle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        Binnacle.autoId = autoId;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Binnacle{" +
                "id=" + id +
                ", dateHour=" + dateHour +
                ", user='" + user + '\'' +
                ", Description='" + Description + '\'' +
                '}'+"\n";
    }
}
