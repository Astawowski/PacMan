import javax.swing.*;

public abstract class MyFrame extends JFrame {
    protected int CurrentCenterX;
    protected int CurrentCenterY;

    MyFrame(String title){
        super(title);
    }

    public void setCenterPoint(int x, int y){
        this.CurrentCenterX=x;
        this.CurrentCenterY=y;
    }

}
