public class FrameCenterPointUpdater implements Runnable {
    private final MyFrame frame;
    private int[] coordinates = new int[2];
    private boolean IsRunning;

    public FrameCenterPointUpdater(MyFrame frame, int currentX, int currentY) {
        this.frame = frame;
        this.coordinates[0] = currentX;
        this.coordinates[1] = currentY;
        this.IsRunning = true;
    }

    @Override
    public void run() {
        while (IsRunning) {
            synchronized (this.frame) {
                int centerX = frame.getWidth() / 2;
                int centerY = frame.getHeight() / 2;
                frame.setCenterPoint(centerX, centerY);
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        this.IsRunning = false;
    }
}
