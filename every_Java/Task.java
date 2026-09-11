public class Task {
    private String title;
    private boolean done = false;

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public void complete() {
        done = true;
    }
}

