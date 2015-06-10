package data.model;

import lombok.Data;

/**
 * Represents player's checker.
 * Created by Ivo on 06/05/15.
 */
@Data
public class Checker {
    private Player player;
    private boolean queen;

    /**
     *
     * @param player the Checker's owner
     */
    public Checker(Player player) {
        this.player = player;
        queen = false;
    }

    private Checker() {
    }
}
