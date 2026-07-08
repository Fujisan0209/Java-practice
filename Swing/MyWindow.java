import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Window");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Hello, Ryotaro!");
        JButton button = new JButton("Click me");

        panel.add(label);
        panel.add(button);
        frame.add(panel);
        
        frame.setVisible(true);
    }
}