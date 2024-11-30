import javax.swing.*;
public class RedGhostAnimator implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel RedGhost;
    private Direction RedGhostDirection;
    private int Stage;

    public RedGhostAnimator(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.RedGhost = this.gameScreen.getRedGhostEnemy();
            this.RedGhostDirection = this.gameScreen.getRedGhostDirection();
        }
        this.Stage = 1;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
            synchronized (this.gameScreen) {
                this.RedGhostDirection= this.gameScreen.getRedGhostDirection();
            }
            if(this.Stage == 1) {
                switch (this.RedGhostDirection) {
                    case Right:
                        this.RedGhost.setIcon(Data.DataGhostRed2R);
                        break;
                    case Left:
                        this.RedGhost.setIcon(Data.DataGhostRed2L);
                        break;
                    case Up:
                        this.RedGhost.setIcon(Data.DataGhostRed2U);
                        break;
                    default:
                        this.RedGhost.setIcon(Data.DataGhostRed2D);
                }
                this.Stage = 2;
            }else {
                switch (this.RedGhostDirection) {
                    case Right:
                        this.RedGhost.setIcon(Data.DataGhostRed1R);
                        break;
                    case Left:
                        this.RedGhost.setIcon(Data.DataGhostRed1L);
                        break;
                    case Up:
                        this.RedGhost.setIcon(Data.DataGhostRed1U);
                        break;
                    default:
                        this.RedGhost.setIcon(Data.DataGhostRed1D);
                }
                this.Stage = 1;
            }
            synchronized (this.gameScreen) {
                this.gameScreen.setRedGhostEnemy(this.RedGhost);
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void stop() {
        this.IsRunning = false;
    }
}
