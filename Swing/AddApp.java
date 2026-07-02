
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Adder");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField field1 = new JTextField(5);
        JTextField field2 = new JTextField(5);
        JLabel label = new JLabel("Result: ");
        JButton button = new JButton("Add");

        panel.add(field1);
        panel.add(field2);
        panel.add(label);
        panel.add(button);
        frame.add(panel);

        frame.setVisible(true);

        button.addActionListener(e -> {
            try {
                int a = Integer.parseInt(field1.getText());
                int b = Integer.parseInt(field2.getText());
                label.setText("Result: " + (a + b));
            } catch (NumberFormatException ex) {
                label.setText("Error: enter numbers");
            }
        });

    }
}