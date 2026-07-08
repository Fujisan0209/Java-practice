import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    int x, y;
    int dx, dy;
    int size;
    Color color;

    public Ball(int x, int y, int dx, int dy, int size, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.color = color;
    }

    public void move(int width, int height) {
        x += dx;
        y += dy;
        if(x < 0 || x > width - size) {
            dx = -dx;
        }
        if(y < 0 || y > height - size) {
            dy = -dy;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
}