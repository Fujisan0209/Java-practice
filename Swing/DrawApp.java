import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

class DrawPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawRect(50, 50, 100, 80);

        g.setColor(Color.RED);
        g.fillOval(200, 50, 80, 80);

        g.setColor(Color.BLUE);
        g.drawLine(50, 200, 300, 200);

        g.setColor(Color.BLACK);
        g.drawString("Hello Graphics", 100, 250);
    }
}

public class DrawApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawPanel panel = new DrawPanel();
        frame.add(panel);
        frame.setVisible(true); 
    }
}