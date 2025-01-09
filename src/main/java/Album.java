import com.fasterxml.jackson.annotation.JsonProperty;

public class Album {
    @JsonProperty("nomArt")
    public String nomArt;
    @JsonProperty("titol")
    public String titol;
    @JsonProperty("any")
    public int any;
    @JsonProperty("ventes")
    public int ventes;
    @JsonProperty("portada")
    public String portada;


    public Album(){};

    public String getNomArt() {
        return nomArt;
    }

    public String getTitol() {
        return titol;
    }

    public int getAny() {
        return any;
    }

    public int getVentes() {
        return ventes;
    }

    public String getPortada() {
        return portada;
    }

    @Override
    public String toString() {
        return "El album "+titol+" del artista "+nomArt+" va ixir en "+any+" ha reunit un total de "+ventes+" de ventes.";
    }
}
