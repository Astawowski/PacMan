import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // --- Loading Game Data and beginning the game
        Data.LoadGameData();
        SwingUtilities.invokeLater(MainMenuScreen::new);
    }
}
