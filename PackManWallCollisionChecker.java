import javax.swing.*;

public class PackManWallCollisionChecker implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MainGameScreen gameScreen;
    private JLabel PacMan;

    public PackManWallCollisionChecker(MainGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.IsRunning = true;
        synchronized (this.gameScreen) {
            this.PacMan = this.gameScreen.getPacMan();
        }
    }

    @Override
    public void run() {
        while (IsRunning) {
                synchronized (this.gameScreen) {
                    // --- Checking if pacman is heading for a wall
                    if (this.gameScreen.getPacManXVelocity() > 0) {
                        if ( (this.gameScreen.getCurrentFieldType(this.PacMan.getX() + 40, this.PacMan.getY()) != 0)||
                                (this.gameScreen.getCurrentFieldType(this.PacMan.getX() + 40, this.PacMan.getY()+30) != 0 )
                                 )  {
                            this.gameScreen.setPacManXVelocity(0);
                            int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                            int CurrentRow = this.gameScreen.getCurrentRowIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                            this.PacMan.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                                    this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                                    this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
                        }
                    }
                }
                synchronized (this.gameScreen) {
                    if (this.gameScreen.getPacManXVelocity() < 0) {
                        if ((this.gameScreen.getCurrentFieldType(this.PacMan.getX() - 10, this.PacMan.getY()) != 0)||
                                (this.gameScreen.getCurrentFieldType(this.PacMan.getX() - 10, this.PacMan.getY()+30) != 0))  {
                            this.gameScreen.setPacManXVelocity(0);
                            int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                            int CurrentRow = this.gameScreen.getCurrentRowIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                            this.PacMan.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                                    this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                                    this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
                        }
                    }
                }
            synchronized (this.gameScreen) {
                if (this.gameScreen.getPacManYVelocity() > 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.PacMan.getX(), this.PacMan.getY() + 40) != 0)
                        ||(this.gameScreen.getCurrentFieldType(this.PacMan.getX()+30, this.PacMan.getY() + 40) != 0)) {
                        this.gameScreen.setPacManYVelocity(0);
                        int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                        int CurrentRow = this.gameScreen.getCurrentRowIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                        this.PacMan.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                                this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                        this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
                    }
                }
            }
            synchronized (this.gameScreen) {
                if (this.gameScreen.getPacManYVelocity() < 0) {
                    if ((this.gameScreen.getCurrentFieldType(this.PacMan.getX(), this.PacMan.getY() - 10) != 0)
                    || (this.gameScreen.getCurrentFieldType(this.PacMan.getX()+30, this.PacMan.getY() - 10) != 0)) {
                        this.gameScreen.setPacManYVelocity(0);
                        int CurrentColumn = this.gameScreen.getCurrentColumnIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                        int CurrentRow = this.gameScreen.getCurrentRowIndex(this.PacMan.getBounds().x, this.PacMan.getBounds().y);
                        this.PacMan.setBounds(this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().x+10,
                               this.gameScreen.getMap()[CurrentRow][CurrentColumn].getBounds().y+10,
                                this.PacMan.getIcon().getIconWidth(), this.PacMan.getIcon().getIconHeight());
                    }
                }
            }
                try {
                    Thread.sleep(5);
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
