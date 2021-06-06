package vs.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Album {

    @NotBlank(message = "Title must not be blank")
    public String title;

    @NotBlank(message = "Band must not be blank")
    public String band;

    @Min(value = 1980, message = "Only years after 1980")
    public int year;

    public Album(String title, String band, int year) {
        this.title = title;
        this.band = band;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Album [title=" + title + ", band=" + band + ", year=" + year + "]";
    }

}
