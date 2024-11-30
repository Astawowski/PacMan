import javax.swing.*;

public class MainMenuStuffPositionUpdater implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainMenuScreen MenuScreen;
    private JButton Button1;
    private JButton Button2;
    private JButton Button3;
    private JLabel Label1;

    public MainMenuStuffPositionUpdater(MainMenuScreen MenuScreen) {
        this.MenuScreen = MenuScreen;
        synchronized (this.MenuScreen) {
            this.Button1 = this.MenuScreen.getStartNewGameButton();
            this.Button2 = this.MenuScreen.getHighScoresButton();
            this.Button3 = this.MenuScreen.getExitButton();
            this.Label1 = this.MenuScreen.getPackManLogoLabel();
        }
        this.IsRunning = true;
    }

    @Override
    public void run() {
        while (IsRunning) {
            synchronized (this.MenuScreen) {
                // --- Setting positions
                this.Button1.setBounds(this.MenuScreen.getCenterPointX() - 220, this.MenuScreen.getCenterPointY() - 45, this.Button1.getWidth(), this.Button1.getHeight());
                this.Button2.setBounds(this.MenuScreen.getCenterPointX() - 220, this.MenuScreen.getCenterPointY() + 105, this.Button2.getWidth(), this.Button2.getHeight());
                this.Button3.setBounds(this.MenuScreen.getCenterPointX() - 220, this.MenuScreen.getCenterPointY() + 255, this.Button3.getWidth(), this.Button3.getHeight());
                this.Label1.setBounds(this.MenuScreen.getCenterPointX() - 510, this.MenuScreen.getCenterPointY() - 450, Data.DataPacManLogoIcon.getIconWidth(), Data.DataPacManLogoIcon.getIconHeight());
                // --- Updating them
                this.MenuScreen.setStartNewGameButton(this.Button1);
                this.MenuScreen.setHighScoresButton(this.Button2);
                this.MenuScreen.setExitButton(this.Button3);
                this.MenuScreen.setPackManLogoLabel(this.Label1);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void stop(){
        this.IsRunning = false;
    }
}