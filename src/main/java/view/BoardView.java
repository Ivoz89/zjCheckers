/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import controller.dao.DaoXStream;
import data.events.GameOverEvent;
import data.events.MoveMadeEvent;
import data.events.NextTurnEvent;
import data.exceptions.InvalidMoveException;
import data.model.Board;
import data.model.Checker;
import data.model.Field;
import data.model.Game;
import data.model.Move;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class BoardView {

    private static final int WINDOW_SIZE = 700;
    private final Game game;
    private transient final EventBus eventBus;
    private final Board board;
    private JFrame boardFrame;
    private JLayeredPane panel;
    private JPanel boardPanel;
    private JLabel checkerLabel;
    private Component oldPosition;
    private double fieldWidth;
    private double fieldHeight;
    private int columnCount;
    private int rowCount;
    private int oldRow;
    private int oldColumn;
    private int sourceRow;
    private int sourceColumn;

    @Inject
    public BoardView(Game game, EventBus eventBus) {
        this.game = game;
        this.eventBus = eventBus;
        this.board = game.getBoard();
        eventBus.register(this);
    }

    /**
     * Displays game over message
     *
     * @param gameOverEvent
     */
    @Subscribe
    public void gameOverEventHandler(GameOverEvent gameOverEvent) {
        JOptionPane.showMessageDialog(boardFrame, "Koniec Gry! Wcisnij ok aby zamknac plansze.",
                "Wygrywa " + gameOverEvent.getWinner().getName(), JOptionPane.INFORMATION_MESSAGE);
        boardFrame.setVisible(false);
    }

    /**
     * Refreshes the board after AI move
     *
     * @param madeEvent
     */
    @Subscribe
    public void handleAIMove(MoveMadeEvent madeEvent) {
        updateBoard();
        boardPanel.repaint();
        boardFrame.repaint();
        boardPanel.updateUI();
    }

    /**
     * Prepares all the board components
     */
    public void initializeBoard() {
        rowCount = board.getRowCount();
        columnCount = board.getColumnCount();
        boardFrame = new JFrame("WARHAMMER 40000 CHECKERS");
        panel = new JLayeredPane();
        Dimension rozmiar = new Dimension(WINDOW_SIZE, WINDOW_SIZE);
        fieldWidth = WINDOW_SIZE / columnCount;
        fieldHeight = WINDOW_SIZE / rowCount;
        boardFrame.getContentPane().add(panel);
        MouseAdapter mouse = new PanelMouseListener();
        panel.setPreferredSize(rozmiar);
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
        KeyListener keyListener = new PanelKeyboardListener();
        boardFrame.addKeyListener(keyListener);
        boardPanel = new JPanel(new GridLayout(columnCount, rowCount));
        boardPanel.setBorder(BorderFactory.createEmptyBorder());
        panel.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        boardPanel.setPreferredSize(rozmiar);
        boardPanel.setBounds(0, 0, rozmiar.width, rozmiar.height);
        updateBoard();
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.pack();
        boardFrame.setResizable(false);
        boardFrame.setLocationRelativeTo(null);
        boardFrame.setVisible(true);
    }

    private void updateBoard() {
        boardPanel.removeAll();
        for (int row = rowCount - 1; row >= 0; row--) {
            for (int column = 0; column < columnCount; column++) {
                JPanel boardField = new JPanel(new BorderLayout());
                boardPanel.add(boardField);
                if ((column + row) % 2 == 0) {
                    boardField.setBackground(Color.GRAY);
                    boardField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.darkGray, Color.black));
                } else {
                    boardField.setBackground(Color.DARK_GRAY);
                    boardField.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.black));
                }
                Field field = board.getField(row, column);
                allocateCheckerLabel(field);
            }
        }
        boardFrame.pack();
    }

    private void allocateCheckerLabel(Field field) {
        Checker occupant = field.getOccupant();
        if (occupant != null) {
            String playerName = occupant.getPlayer().getName();
            JLabel checkerLabel = null;
            if (field.getOccupant().isQueen()) {
                checkerLabel = new JLabel(new ImageIcon(playerName + "_krol.png"));
            } else {
                checkerLabel = new JLabel(new ImageIcon(playerName + ".png"));
            }
            JPanel checkerField = (JPanel) boardPanel.getComponent(boardPanel.getComponentCount() - 1);
            checkerField.add(checkerLabel);
        }
    }

    class PanelKeyboardListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 's') {
                File file = null;
                final JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(boardFrame) == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                }
                try {
                    new DaoXStream().save(game, file);
                    JOptionPane.showMessageDialog(boardFrame, "Zapisano grę", "Sukces",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(boardFrame, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class PanelMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            checkerLabel = null;
            oldPosition = boardPanel.findComponentAt(e.getX(), e.getY());
            Container oldField = oldPosition.getParent();
            if (oldPosition instanceof JPanel) {
                return;
            }
            Point parentLocation = oldPosition.getParent().getLocation();
            sourceRow = parentLocation.x - e.getX();
            sourceColumn = parentLocation.y - e.getY();
            checkerLabel = (JLabel) oldPosition;
            checkerLabel.setLocation(e.getX() + sourceRow, e.getY() + sourceColumn);
            checkerLabel.setSize(checkerLabel.getWidth(), checkerLabel.getHeight());
            panel.add(checkerLabel, JLayeredPane.DRAG_LAYER);
            oldRow = (int) ((WINDOW_SIZE - e.getY()) / fieldHeight);
            oldColumn = (int) (e.getX() / fieldWidth);
            Field sourceField = game.getBoard().getField(oldRow, oldColumn);
            game.getLegalDestinations(sourceField).forEach(destination -> {
                int componentIndex = (rowCount - destination.getRowIndex() - 1)
                        * (columnCount) + destination.getColumnIndex();
                JPanel correspondingField = (JPanel) boardPanel.getComponent(componentIndex);
                correspondingField.setBackground(Color.GREEN);
            });
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (checkerLabel == null) {
                return;
            }
            checkerLabel.setLocation(me.getX() + sourceRow, me.getY() + sourceColumn);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (checkerLabel == null) {
                return;
            }
            checkerLabel.setVisible(false);
            Component newPosition = boardPanel.findComponentAt(e.getX(), e.getY());
            int nowyX = (int) ((WINDOW_SIZE - e.getY()) / fieldHeight);
            int nowyY = (int) (e.getX() / fieldWidth);
            int dx = nowyX - oldRow;
            int dy = nowyY - oldColumn;
            Checker movingChecker = board.getField(oldRow, oldColumn).getOccupant();
            checkerLabel.setVisible(true);
            try {
                Move move = new Move(board.getField(oldRow, oldColumn).getOccupant().getPlayer(), board.getField(oldRow, oldColumn), board.getField(nowyX, nowyY));
                game.makeMove(move);
                Container parent = (Container) newPosition;
                parent.add(checkerLabel);
                eventBus.post(new NextTurnEvent());
            } catch (InvalidMoveException ex) {
                JOptionPane.showMessageDialog(boardFrame, ex.getMessage(), "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            } finally {
                checkerLabel.setVisible(false);
                updateBoard();
                boardPanel.repaint();
                boardFrame.repaint();
                boardPanel.updateUI();
            }
        }
    }
}
