import javax.swing.*;

public class MapChooseStuffPositionUpdater implements Runnable, MyGUIThread {

    private boolean IsRunning;
    private final MapChooseScreen mapChooseScreen;
    private JComboBox<String> MapComboBox1;
    private JLabel MainPromptText1;
    private JButton backButton;
    private JButton goButton;
    private JLabel mapPreviewLabel;

    public MapChooseStuffPositionUpdater(MapChooseScreen mapChooseScreen) {
        this.mapChooseScreen = mapChooseScreen;
        synchronized (this.mapChooseScreen) {
            this.MapComboBox1 = this.mapChooseScreen.getMapComboBox();
            this.MainPromptText1 = this.mapChooseScreen.getMainPromptText();
            this.backButton = this.mapChooseScreen.getBackButton();
            this.goButton = this.mapChooseScreen.getGoButton();
            this.mapPreviewLabel = this.mapChooseScreen.getMapPreviewLabel();
        }
        this.IsRunning = true;
    }

    @Override
    public void run() {
        while (IsRunning) {
            synchronized (this.mapChooseScreen) {
                // --- Setting positions
                this.MapComboBox1.setBounds(this.mapChooseScreen.CurrentCenterX - 500, this.mapChooseScreen.CurrentCenterY, 250, 50);
                this.MainPromptText1.setBounds(this.mapChooseScreen.CurrentCenterX - 200, this.mapChooseScreen.CurrentCenterY - 400, 500, 70);
                this.backButton.setBounds(this.mapChooseScreen.CurrentCenterX - 467, this.mapChooseScreen.CurrentCenterY + 285, 150, 70);
                this.goButton.setBounds(this.mapChooseScreen.CurrentCenterX + 158, this.mapChooseScreen.CurrentCenterY + 285, 100, 70);
                this.mapPreviewLabel.setBounds(this.mapChooseScreen.CurrentCenterX - 177, this.mapChooseScreen.CurrentCenterY - 275,
                        this.mapChooseScreen.getMapPreviewIcon().getIconWidth(), this.mapChooseScreen.getMapPreviewIcon().getIconHeight());
                this.mapChooseScreen.setMapComboBox(this.MapComboBox1);
                this.mapChooseScreen.setMainPromptText(this.MainPromptText1);
                this.mapChooseScreen.setBackButton(this.backButton);
                this.mapChooseScreen.setGoButton(this.goButton);
                this.mapChooseScreen.setMapPreviewLabel(this.mapPreviewLabel);
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