import javax.swing.*;
public class OrangeGhostAnimator implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel OrangeGhost;
    private Direction OrangeGhostDirection;
    private int Stage;

    public OrangeGhostAnimator(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.OrangeGhost = this.gameScreen.getOrangeGhostEnemy();
            this.OrangeGhostDirection = this.gameScreen.getOrangeGhostDirection();
        }
        this.Stage = 1;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
            synchronized (this.gameScreen) {
                this.OrangeGhostDirection= this.gameScreen.getOrangeGhostDirection();
            }
            if(this.Stage == 1) {
                switch (this.OrangeGhostDirection) {
                    case Right:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange2R);
                        break;
                    case Left:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange2L);
                        break;
                    case Up:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange2U);
                        break;
                    default:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange2D);
                }
                this.Stage = 2;
            }else {
                switch (this.OrangeGhostDirection) {
                    case Right:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange1R);
                        break;
                    case Left:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange1L);
                        break;
                    case Up:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange1U);
                        break;
                    default:
                        this.OrangeGhost.setIcon(Data.DataGhostOrange1D);
                }
                this.Stage = 1;
            }
            synchronized (this.gameScreen) {
                this.gameScreen.setOrangeGhostEnemy(this.OrangeGhost);
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
