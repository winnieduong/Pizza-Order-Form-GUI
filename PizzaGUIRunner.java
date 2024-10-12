import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        // Start the GUI
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}
