package trail.model;

import java.util.List;
import java.util.Objects;

public record Event(String title, List<String> options, List<Runnable> updateStates) {
    public void selected(int option) {
        if (option <= 0 || option > options().size()) {
            throw new IllegalArgumentException();
        }
        updateStates.get(option - 1).run();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(title, event.title) && Objects.equals(options, event.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, options);
    }
}
