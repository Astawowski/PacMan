import javax.swing.*;
public class BlueGhostAnimator implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel BlueGhost;
    private Direction BlueGhostDirection;
    private int Stage;

    public BlueGhostAnimator(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.BlueGhost = this.gameScreen.getBlueGhostEnemy();
            this.BlueGhostDirection = this.gameScreen.getBlueGhostDirection();
        }
        this.Stage = 1;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
            synchronized (this.gameScreen) {
                this.BlueGhostDirection= this.gameScreen.getBlueGhostDirection();
            }
            if(this.Stage == 1) {
                switch (this.BlueGhostDirection) {
                    case Right:
                        this.BlueGhost.setIcon(Data.DataGhostBlue2R);
                        break;
                    case Left:
                        this.BlueGhost.setIcon(Data.DataGhostBlue2L);
                        break;
                    case Up:
                        this.BlueGhost.setIcon(Data.DataGhostBlue2U);
                        break;
                    default:
                        this.BlueGhost.setIcon(Data.DataGhostBlue2D);
                }
                this.Stage = 2;
            }else {
                switch (this.BlueGhostDirection) {
                    case Right:
                        this.BlueGhost.setIcon(Data.DataGhostBlue1R);
                        break;
                    case Left:
                        this.BlueGhost.setIcon(Data.DataGhostBlue1L);
                        break;
                    case Up:
                        this.BlueGhost.setIcon(Data.DataGhostBlue1U);
                        break;
                    default:
                        this.BlueGhost.setIcon(Data.DataGhostBlue1D);
                }
                this.Stage = 1;
            }
            synchronized (this.gameScreen) {
                this.gameScreen.setBlueGhostEnemy(this.BlueGhost);
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
