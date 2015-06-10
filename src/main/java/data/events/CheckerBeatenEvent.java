package data.events;

import data.model.Checker;

/**
 * Created by Ivo on 07/06/15.
 */
public class CheckerBeatenEvent {

    private final Checker checker;

    public CheckerBeatenEvent(Checker checker) {
        this.checker = checker;
    }
}
