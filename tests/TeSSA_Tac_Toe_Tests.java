// Version für JUnit 5
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import gfx.MainWindow;
import gfx.Ressources;
import logic.Board;
import logic.Player;
import logic.WinState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TeSSA_Tac_Toe_Tests {
    private Player p1, p2;
    private Board board;
    private MainWindow frame;
    private MainWindow frame2;
    private MainWindow frame3;
    private Board board2;
    private Board board3;

    private JComboBox<String> combo_p1;
    private JComboBox<String> combo_p2;

    private static final int TIME_OUT = 0;

    @BeforeEach
    public void setUp() throws Exception {
        p1 = new Player("Player 1", Ressources.icon_x);
        p2 = new Player("Player 2", Ressources.icon_o);
        board = new Board(3, 3, 3, p1, p2);
        frame = new MainWindow(p1, p2, board);
        board2 = new Board(5, 5, 3, p1, p2);
        board3= new Board(2,2,2, p1, p2);
        frame2 = new MainWindow(p1, p2, board2);
        frame3= new MainWindow(p1, p2, board3);

        frame.setVisible(true);
        MainWindow.setDebugg(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        p1 = p2 = null;
        board = null;
        frame = null;
        board2 = null;
        frame2 = null;
        board3 = null;
    }

    @Test
    public void test01() throws InterruptedException {
        frame.turn(0, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 1);
        Thread.sleep(TIME_OUT);
        frame.turn(1, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 2);
        Thread.sleep(TIME_OUT);
        WinState winner = frame.turn(2, 0);
        assertSame(WinState.player1, winner);
    }

    @Test
    public void test02() {
        frame.turn(0, 0);
        String retString = board.getPlayerNameInField(0, 0);
        assertEquals("Player 1", retString);
    }

    @Test
    public void test03() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        String retString = board.getPlayerNameInField(0, 1);
        assertEquals("Player 2", retString);
    }

    @Test
    public void test04() {
        String retString = board.getPlayerNameInField(0, 0);
        assertEquals("        ", retString);
    }

    @Test
    public void test05() {
        WinState wst = frame.turn(0, 0);
        frame.checkWinner(wst);
    }

    @Test
    public void test06() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 0);
        frame.turn(1, 1);
        frame.turn(2, 1);
        WinState winSt = frame.turn(1, 0);
        board.checkWin();
        frame.checkWinner(winSt);
    }

    @Test
    public void test07() {
        frame.settingsFrame();
    }

    @Test
    public void testSpielfeld() {
        frame.turn(2, 2);
        String retString = board.getPlayerNameInField(2, 2);
        assertEquals("Player 1", retString);
    }

    @Test
    public void testGewinnUeberEcke() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 0);
        frame.turn(0, 2);
        board.checkWin();
        WinState winSt = frame.turn(1, 1);
        assertNotEquals(winSt, WinState.player1);

    }

    @Test
    public void testSpielstandPlayer1() {
        JLabel player1Score = frame.getPlayer1_score();
        JLabel player2Score = frame.getPlayer2_score();
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 0);
        frame.turn(0, 2);
        board.checkWin();
        WinState winState = frame.turn(2, 0);
        assertEquals(1, player1Score.getText());

    }

    @Test
    public void testSpielstandPlayer2() {
        JLabel player1Score = frame.getPlayer1_score();
        JLabel player2Score = frame.getPlayer2_score();

        frame.turn(0, 1);
        frame.turn(0, 0);
        frame.turn(0, 2);
        frame.turn(1, 0);
        frame.turn(1, 2);
        frame.turn(2, 0);
        frame.checkWinner(WinState.player1);
        assertEquals(1, player2Score.getText());

    }

    @Test
    public void testfallNeun() {
        frame2.turn(0, 0);
        frame2.turn(0, 1);
        frame2.turn(1, 0);
        frame2.turn(1, 1);
        WinState winState = frame2.turn(3, 0);
        assertNotEquals(winState, WinState.player1);
    }

    @Test
    public void chooseIconTessaRedOrX() {
        Player player1 = new Player("Player 1", Ressources.icon_o);
        Player player2 = new Player("Player 2", Ressources.icon_tessa_blue);
        assertEquals("O", player1.getIconString());
        assertEquals("TeSSA blue", player2.getIconString());

    }

    @Test
    public void mehrfachAnklicken() {
        String retString = "";
        String retString2 = "";
        for (int i = 0; i < 20; i++) {
            frame.turn(1, 1);
            retString = board.getPlayerNameInField(1, 1);
            retString2 = board.getPlayerNameInField(0, 0);
        }

        assertEquals("Player 1", retString);
        assertEquals("", retString2);
    }

    @Test
    public void spielerBautVstruktur() {
        frame.turn(0, 0);
        frame.turn(1, 0);
        frame.turn(1, 1);
        frame.turn(0, 1);
        WinState winState = frame.turn(0, 2);
        assertNotEquals(WinState.player1, winState);

    }

    @Test
    public void backslashFormation() {
        frame.turn(0, 0);
        frame.turn(1, 0);
        frame.turn(1, 1);
        frame.turn(0, 1);
        WinState winState = frame.turn(2, 2);
        assertEquals(WinState.player1, winState);
    }

    @Test
    public void feldWirdNichtAusgewertet() {
        frame2.turn(4, 4);
        assertEquals("Player 1", board2.getPlayerNameInField(4, 4));
    }

    @Test
    public void testCheckWinnerTie() {
        JLabel player1Score = frame.getPlayer1_score();
        JLabel player2Score = frame.getPlayer2_score();

        // Simulate a tie
        frame.turn(0, 0);
        frame.turn(1, 0);
        frame.turn(2, 0);
        frame.turn(0, 1);
        frame.turn(1, 1);
        frame.turn(2, 1);
        frame.turn(1, 2);
        frame.turn(2, 2);

        frame.checkWinner(WinState.tie);
        assertEquals("0", player1Score.getText());
        assertEquals("0", player2Score.getText());

    }

    @Test
    public void testCheckWinnerPlayer1() {
        JLabel player1Score = frame.getPlayer1_score();
        JLabel player2Score = frame.getPlayer2_score();
        // Simulate player 1 winning
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 0);
        frame.turn(0, 2);
        frame.turn(2, 0);

        frame.checkWinner(WinState.player1);
        assertEquals("1", player1Score.getText());
        assertEquals("0", player2Score.getText());
    }

    @Test
    public void testCheckWinnerPlayer2() {
        JLabel player1Score = frame.getPlayer1_score();
        JLabel player2Score = frame.getPlayer2_score();

        // Simulate player 2 winning
        frame.turn(0, 1);
        board.checkWin();
        frame.turn(0, 0);
        board.checkWin();
        frame.turn(0, 2);
        board.checkWin();
        frame.turn(1, 0);
        board.checkWin();
        frame.turn(2, 0);
        board.checkWin();
        frame.checkWinner(WinState.player2);
        assertEquals("0", player1Score.getText());
        assertEquals("1", player2Score.getText());
    }

    @Test
    public void testGetIconString() {
        Player player1 = new Player("X", Ressources.icon_x);
        Player player2 = new Player("TeSSA red", Ressources.icon_tessa_red);
        Player player3 = new Player("TeSSA blue", Ressources.icon_tessa_blue);
        Player player4 = new Player("O", Ressources.icon_o);

        assertEquals("X", player1.getIconString());
        assertEquals("TeSSA red", player2.getIconString());
        assertEquals("TeSSA blue", player3.getIconString());
        assertEquals("O", player4.getIconString());
    }

    @Test
    public void testChangePlayerIconInTicTacToeGame() {

        JFrame settingsFrame = frame.settingsFrame();
        settingsFrame.setAlwaysOnTop(true);
        settingsFrame.setVisible(true);

        JComboBox<String> combo_p1 = null;
        JComboBox<String> combo_p2 = null;
        Component[] components = settingsFrame.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getName() != null && components[i].getName().equals("Save Button")) {
                JButton saveButton = (JButton) components[i];
                // perform actions on the saveButton, such as simulating a click
                break;
            }
        }


    }

    @Test
    public void test077() throws InterruptedException {
        JFrame settingsFrame = frame.settingsFrame();
        JPanel panel = (JPanel) settingsFrame.getContentPane().getComponent(0);
        JComboBox<String> combo_p1 = (JComboBox<String>) panel.getComponent(1);
        JComboBox<String> combo_p2 = (JComboBox<String>) panel.getComponent(3);

        combo_p1.setSelectedIndex(1);
        combo_p2.setSelectedIndex(1);

        JButton button = (JButton) panel.getComponent(4);
        button.doClick();

        System.out.println(Arrays.toString(panel.getComponents()));
    }

    @Test
    public void testGetIconPlayer(){
        JFrame settingsFrame = frame.settingsFrame();
        JPanel panel = (JPanel) settingsFrame.getContentPane().getComponent(0);
        JComboBox<String> combo_p1 = (JComboBox<String>) panel.getComponent(1);
        JComboBox<String> combo_p2 = (JComboBox<String>) panel.getComponent(3);

        combo_p1.setSelectedIndex(0);
        combo_p2.setSelectedIndex(1);

        JButton button = (JButton) panel.getComponent(4);
        button.doClick();
        assertEquals("X", combo_p1.getSelectedItem());
        assertEquals("O", combo_p2.getSelectedItem());

        combo_p1.setSelectedIndex(1);
        button.doClick();
        assertEquals("O", combo_p2.getSelectedItem());

        combo_p1.setSelectedIndex(1);
        combo_p2.setSelectedIndex(2);
        button.doClick();
        assertEquals("O", combo_p1.getSelectedItem());
        assertEquals("TeSSA Red", combo_p2.getSelectedItem());

        combo_p1.setSelectedIndex(2);
        combo_p2.setSelectedIndex(3);
        button.doClick();
        assertEquals("TeSSA Red", combo_p1.getSelectedItem().toString());
        assertEquals("TeSSA Blue", combo_p2.getSelectedItem().toString());

        combo_p1.setSelectedIndex(3);
        combo_p2.setSelectedIndex(0);
        button.doClick();
        assertEquals("TeSSA Blue", combo_p1.getSelectedItem());
        assertEquals("X", combo_p2.getSelectedItem());




    }

    @Test
    public void getString(){
        assertEquals("[Player 1]", p1.toString());
        p1.setIcon(null);
        assertEquals("", p1.getIconString());
        p1.setIcon(Ressources.icon_tessa_red);
        assertEquals("TeSSA red", p1.getIconString());
    }

    @Test
    public void checkDiagonalWin(){
        frame2.turn(0,0);
        frame2.turn(0,1);
        frame2.turn(1,1);
        frame2.turn(0,2);
        frame2.turn(2,2);
        WinState winState= board2.checkWin();
        assertEquals(WinState.player1, board2.checkWin());
    }

    @Test
    public void testCheckWinHorizontal(){
        frame.turn(0,0);
        frame.turn(1,0);
        frame.turn(0,1);
        frame.turn(1,1);
        frame.turn(0,2);
        assertEquals(WinState.player1, board.checkWin());

    }

    @Test
    public void testCheckWinVertikal(){
        frame.turn(0, 0);
        frame.turn(0,1);
        frame.turn(1,0);
        frame.turn(1,1);
        frame.turn(2,0);
        assertEquals(WinState.player1, board.checkWin());
    }

    @Test
    public void smallerBoard(){
        frame3.turn(0,0);
        frame3.turn(0,1);
        assertEquals(WinState.none, board3.checkWin());
        frame3.turn(1,1);
        assertEquals(WinState.player1, board3.checkWin());
    }

    @Test
    public void testCheckWinHorizontalLeft(){
        frame.turn(0,2);
        frame.turn(0,1);
        frame.turn(1,1);
        board.checkWin();
        frame.turn(0,0);
        frame.turn(2,0);
        assertEquals(WinState.player1, board.checkWin());

    }
}







