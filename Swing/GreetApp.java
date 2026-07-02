import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GreetApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Greeting");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField textField = new JTextField(15);
        JLabel label = new JLabel("Enter your name");
        JButton button = new JButton("Greet");

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        frame.add(panel);

        frame.setVisible(true);

        button.addActionListener(e -> {
            String name = textField.getText();
            label.setText("Hello, " + name + "!");
        });
    }
}