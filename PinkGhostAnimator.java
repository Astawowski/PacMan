import javax.swing.*;
public class PinkGhostAnimator implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel PinkGhost;
    private Direction PinkGhostDirection;
    private int Stage;

    public PinkGhostAnimator(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.PinkGhost = this.gameScreen.getPinkGhostEnemy();
            this.PinkGhostDirection = this.gameScreen.getPinkGhostDirection();
        }
        this.Stage = 1;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
            synchronized (this.gameScreen) {
                this.PinkGhostDirection= this.gameScreen.getPinkGhostDirection();
            }
            if(this.Stage == 1) {
                switch (this.PinkGhostDirection) {
                    case Right:
                        this.PinkGhost.setIcon(Data.DataGhostPink2R);
                        break;
                    case Left:
                        this.PinkGhost.setIcon(Data.DataGhostPink2L);
                        break;
                    case Up:
                        this.PinkGhost.setIcon(Data.DataGhostPink2U);
                        break;
                    default:
                        this.PinkGhost.setIcon(Data.DataGhostPink2D);
                }
                this.Stage = 2;
            }else {
                switch (this.PinkGhostDirection) {
                    case Right:
                        this.PinkGhost.setIcon(Data.DataGhostPink1R);
                        break;
                    case Left:
                        this.PinkGhost.setIcon(Data.DataGhostPink1L);
                        break;
                    case Up:
                        this.PinkGhost.setIcon(Data.DataGhostPink1U);
                        break;
                    default:
                        this.PinkGhost.setIcon(Data.DataGhostPink1D);
                }
                this.Stage = 1;
            }
            synchronized (this.gameScreen) {
                this.gameScreen.setPinkGhostEnemy(this.PinkGhost);
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
