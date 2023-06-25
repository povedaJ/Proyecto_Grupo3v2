package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Binnacle implements Serializable {

    private LocalDateTime dateHour;

    private String user;

    private String Description;

    public Binnacle(LocalDateTime dateHour, String user, String description) {

        this.dateHour = dateHour;
        this.user = user;
        Description = description;
    }

    public Binnacle() {
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
                "dateHour=" + dateHour +
                ", user='" + user + '\'' +
                ", Description='" + Description + '\'' +
                '}'+"\n";
    }
}
