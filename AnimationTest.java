import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.util.Scanner;


public class AnimationTest extends JComponent
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    final int FRAME_WIDTH = 500;
    final int FRAME_HEIGHT = 600;

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setTitle("House");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Parcel parcelTest = new Parcel(2);
    frame.add(parcelTest);
    frame.setVisible(true);
  }

}
