import com.fasterxml.jackson.annotation.JsonProperty;

public class Autor {
    @JsonProperty("name")
    private String name;
    @JsonProperty("birth")
    private int birth;
    @JsonProperty("country")
    private String country;
    @JsonProperty("artistName")
    private String artistName;
    @JsonProperty("actualImage")
    private String actualImage;

    @Override
    public String toString() {
        return name+" mes conegut com "+artistName+" va naixer a "+country+" en "+birth;
    }

    public String getName() {
        return name;
    }

    public int getBirth() {
        return birth;
    }

    public String getCountry() {
        return country;
    }

    public String getArtistName() {
        return artistName;
    }



    public String getActualImage() {
        return actualImage;
    }
}
