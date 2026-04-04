package trail.controller;

import trail.model.State;

import java.util.List;

public interface Event {
    String title();
    List<String> options();
    void selected(int option, State state);
}