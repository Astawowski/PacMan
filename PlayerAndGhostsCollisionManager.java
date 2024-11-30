public class PlayerAndGhostsCollisionManager implements Runnable, MyGUIThread{
    private boolean IsRunning;
    private MainGameScreen gameScreen;

    public PlayerAndGhostsCollisionManager(MainGameScreen gameScreen) {
        this.IsRunning = true;
        this.gameScreen = gameScreen;
    }


    @Override
    public void run(){
        while(IsRunning) {
            synchronized(this.gameScreen) {
                this.gameScreen.PacManAndGhostCollisionChecker();
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
