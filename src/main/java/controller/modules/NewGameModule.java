package controller.modules;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import controller.ai.AIPlayer;
import controller.populators.ClassicPopulator;
import data.annotations.*;
import data.api.BoardPopulator;
import data.model.Board;
import data.model.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Binds game parameters to instances and constants.
 */
public class NewGameModule extends AbstractModule {

    private final Player humanPlayer;
    private final AIPlayer CPUPlayer;
    private final int rowCount;
    private final int columnCount;
    private final int checkerRows;
    private Board board;
    private EventBus eventBus;

    public NewGameModule(@NotNull Player firstPlayer, @NotNull AIPlayer secondPlayer,
            @NotNull int rowCount, @NotNull int columnCount, @NotNull int checkerRows) {
        this.humanPlayer = firstPlayer;
        this.checkerRows = checkerRows;
        this.CPUPlayer = secondPlayer;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.eventBus = new EventBus();
    }

    public NewGameModule(@NotNull Player firstPlayer, @NotNull AIPlayer secondPlayer,
            @NotNull int rowCount, @NotNull int columnCount, @NotNull int checkerRows,
            @NotNull Board board) {
        this(firstPlayer, secondPlayer, rowCount, columnCount, checkerRows);
        this.board = board;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(RowCount.class).to(rowCount);
        bindConstant().annotatedWith(ColumnCount.class).to(columnCount);
        bindConstant().annotatedWith(CheckerRows.class).to(checkerRows);
        bind(Player.class).annotatedWith(HumanPlayer.class).toInstance(humanPlayer);
        bind(AIPlayer.class).annotatedWith(CPUPlayer.class).toInstance(CPUPlayer);
        bind(EventBus.class).toInstance(eventBus);
        bind(BoardPopulator.class).to(ClassicPopulator.class);
        if (board != null) {
            bind(Board.class).toInstance(board);
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
