package trail.model;

import java.util.List;

public class Event {
    private final String title;
    private final List<String> options;
    private final List<Runnable> updateStates;

    public Event(String title, List<String> options, List<Runnable> updateStates) {
        this.title = title;
        this.options = options;
        this.updateStates = updateStates;
    }

    public String getTitle() {
        return title;
    }

    public List<String> options() {
        return options;
    }

    public void selected(int option) {
        if (option <= 0 || option > options().size()) {
            throw new IllegalArgumentException();
        }
        updateStates.get(option - 1).run();
    }
}
