
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class BallPanel extends JPanel {
    int x = 50;
    int y = 50;
    int dx = 3;
    int dy = 2;
    int size = 30;

    public BallPanel() {
        Timer timer = new Timer(16, e -> {
            x += dx;
            y += dy;

            if(x < 0 || x > getWidth() - size) {
                dx = -dx;
            }
            if(y < 0 || y > getHeight() - size) {
                dy = -dy;
            }

            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }
}

public class BounceBall {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BallPanel panel = new BallPanel();
        frame.add(panel);

        frame.setVisible(true);   
    }
}