import javax.swing.*;

public class PacManAnimator implements Runnable, MyGUIThread {
    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel PacMan;
    private int PacManAnimPart;
    private int PacManAnimPrevPart;

    public PacManAnimator(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.PacMan = this.gameScreen.getPacMan();
        }
        this.PacManAnimPart = 1;
        this.PacManAnimPrevPart = 2;
    }

    @Override
    public void run() {
        while (this.IsRunning) {
                    synchronized (this.gameScreen) {
                if(this.PacManAnimPart == 1&&this.PacManAnimPrevPart == 2) {
                    switch (this.gameScreen.getPacManDirection()){
                        case Direction.Right:
                            this.PacMan.setIcon(Data.DataPacMan3ImageR);
                            break;
                        case Direction.Left:
                            this.PacMan.setIcon(Data.DataPacMan3ImageL);
                            break;
                        case Direction.Up:
                            this.PacMan.setIcon(Data.DataPacMan3ImageU);
                            break;
                        default:
                            this.PacMan.setIcon(Data.DataPacMan3ImageD);
                    }
                    this.PacManAnimPart = 3;
                    this.PacManAnimPrevPart = 1;
                }else if(this.PacManAnimPart == 1&&this.PacManAnimPrevPart == 3){
                    switch (this.gameScreen.getPacManDirection()){
                        case Direction.Right:
                            this.PacMan.setIcon(Data.DataPacMan2ImageR);
                            break;
                        case Direction.Left:
                            this.PacMan.setIcon(Data.DataPacMan2ImageL);
                            break;
                        case Direction.Up:
                            this.PacMan.setIcon(Data.DataPacMan2ImageU);
                            break;
                        default:
                            this.PacMan.setIcon(Data.DataPacMan2ImageD);
                    }
                    this.PacManAnimPart = 2;
                    this.PacManAnimPrevPart = 1;
                }else if(this.PacManAnimPart == 3&&this.PacManAnimPrevPart == 1){
                    switch (this.gameScreen.getPacManDirection()){
                        case Direction.Right:
                            this.PacMan.setIcon(Data.DataPacMan1ImageR);
                            break;
                        case Direction.Left:
                            this.PacMan.setIcon(Data.DataPacMan1ImageL);
                            break;
                        case Direction.Up:
                            this.PacMan.setIcon(Data.DataPacMan1ImageU);
                            break;
                        default:
                            this.PacMan.setIcon(Data.DataPacMan1ImageD);
                    }
                    this.PacManAnimPart = 1;
                    this.PacManAnimPrevPart = 3;
                }
                else if(this.PacManAnimPart == 2&&this.PacManAnimPrevPart == 1){
                    switch (this.gameScreen.getPacManDirection()){
                        case Direction.Right:
                            this.PacMan.setIcon(Data.DataPacMan1ImageR);
                            break;
                        case Direction.Left:
                            this.PacMan.setIcon(Data.DataPacMan1ImageL);
                            break;
                        case Direction.Up:
                            this.PacMan.setIcon(Data.DataPacMan1ImageU);
                            break;
                        default:
                            this.PacMan.setIcon(Data.DataPacMan1ImageD);
                    }
                    this.PacManAnimPart = 1;
                    this.PacManAnimPrevPart = 2;
                }
                    this.gameScreen.setPacMan(this.PacMan);
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
        IsRunning = false;
    }

}
