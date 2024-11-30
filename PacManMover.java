import javax.swing.*;

public class PacManMover implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel PacMan;
    private int OldCenterX;
    private int OldCenterY;

    public PacManMover(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.PacMan = this.gameScreen.getPacMan();
            this.OldCenterX = this.gameScreen.CurrentCenterX;
            this.OldCenterY = this.gameScreen.CurrentCenterY;
        }
    }

    @Override
    public void run() {
        while (IsRunning) {
                // -- PacMan position is also synchronized with frame center point, so I'm checking whether the center has moved
                int xOffSet = 0;
                int yOffSet = 0;
            synchronized (this.gameScreen) {
                if (this.gameScreen.CurrentCenterX != this.OldCenterX || this.gameScreen.CurrentCenterY != this.OldCenterY) {
                    xOffSet = this.gameScreen.CurrentCenterX - this.OldCenterX;
                    yOffSet = this.gameScreen.CurrentCenterY - this.OldCenterY;
                    this.OldCenterX = this.gameScreen.CurrentCenterX;
                    this.OldCenterY = this.gameScreen.CurrentCenterY;
                }
                this.PacMan.setBounds(this.PacMan.getBounds().x + xOffSet + (int)(this.gameScreen.getPacManXVelocity()*this.gameScreen.getPlayerSpeedMultiplier()*0.75),
                        this.PacMan.getBounds().y + yOffSet + (int)(this.gameScreen.getPacManYVelocity()*this.gameScreen.getPlayerSpeedMultiplier()*0.75),
                        this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
                this.gameScreen.setPacMan(this.PacMan);
            }
                try {
                    Thread.sleep(30);
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
