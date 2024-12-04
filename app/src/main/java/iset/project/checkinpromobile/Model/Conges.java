package iset.project.checkinpromobile.Model;

public class Conges {
    private String date;
    private String duree;
    private String titre;

    public Conges(String date, String duree, String titre) {
        this.date = date;
        this.duree = duree;
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
