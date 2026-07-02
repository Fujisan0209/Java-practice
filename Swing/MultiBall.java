import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class MultiBallPanel extends JPanel {
    List<Ball> balls = new ArrayList<>();

    public MultiBallPanel() {
        balls.add(new Ball(50, 50, 3, 2, 30, Color.RED));
        balls.add(new Ball(100, 80, 2, 4, 40, Color.BLUE));
        balls.add(new Ball(150, 120, 4, 3, 20, Color.GREEN));

        Timer timer = new Timer(16, e -> {
            for(Ball b : balls) {
                b.move(getWidth(), getHeight());
            }
            repaint(); // -> paintComponentat
        });
        timer.start(); // stop -> start
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Ball b : balls) {
            b.draw(g);
        }
    }
}

public class MultiBall {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multi Ball 3");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MultiBallPanel panel = new MultiBallPanel();
        frame.add(panel);

        frame.setVisible(true);   
    }
}