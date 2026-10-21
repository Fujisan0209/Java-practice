public class Movie {
    private String title;
    private int minutes;

    public Movie(String title, int minutes) {
        this.title = title;
        this.minutes = minutes;
    }

    public String getTitle() {
        return this.title;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public boolean isLong() {
        return this.minutes >= 120;
    }
}