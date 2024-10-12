import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    // Components for Pizza Crust, Size, and Toppings
    private JRadioButton thinCrust, regularCrust, deepDish;
    private JComboBox<String> sizeComboBox;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;
    private JTextArea receiptArea;
    private JButton orderButton, clearButton, quitButton;

    public PizzaGUIFrame() {
        // Set up the frame
        setTitle("Pizza Order Form");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components and layout
        createComponents();
    }

    private void createComponents() {
        // Panel for Crust selection
        JPanel crustPanel = new JPanel();
        crustPanel.setLayout(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDish);

        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDish);

        // Panel for Size selection
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));

        String[] sizes = {"Small - $8.00", "Medium - $12.00", "Large - $16.00", "Super - $20.00"};
        sizeComboBox = new JComboBox<>(sizes);

        sizePanel.add(sizeComboBox);

        // Panel for Toppings
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(3, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings ($1 each)"));

        topping1 = new JCheckBox("Pepperoni");
        topping2 = new JCheckBox("Mushrooms");
        topping3 = new JCheckBox("Onions");
        topping4 = new JCheckBox("Sausage");
        topping5 = new JCheckBox("Bacon");
        topping6 = new JCheckBox("Extra cheese");

        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);

        // Panel for Order Summary
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        receiptArea = new JTextArea(10, 30);
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);

        summaryPanel.add(scrollPane);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel();

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add action listeners
        orderButton.addActionListener(new OrderButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        quitButton.addActionListener(e -> confirmQuit());

        // Add panels to the frame
        add(crustPanel, BorderLayout.WEST);
        add(sizePanel, BorderLayout.NORTH);
        add(toppingsPanel, BorderLayout.EAST);
        add(summaryPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void confirmQuit() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Listener for the Order button
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder order = new StringBuilder();
            double totalCost = 0;

            // Crust selection
            if (thinCrust.isSelected()) {
                order.append("Crust: Thin\n");
            } else if (regularCrust.isSelected()) {
                order.append("Crust: Regular\n");
            } else if (deepDish.isSelected()) {
                order.append("Crust: Deep-dish\n");
            } else {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this, "Please select a crust.");
                return;
            }

            // Size selection and cost
            String sizeSelection = (String) sizeComboBox.getSelectedItem();
            double sizeCost = 0;
            if (sizeSelection != null) {
                if (sizeSelection.contains("Small")) sizeCost = 8.00;
                else if (sizeSelection.contains("Medium")) sizeCost = 12.00;
                else if (sizeSelection.contains("Large")) sizeCost = 16.00;
                else if (sizeSelection.contains("Super")) sizeCost = 20.00;
            }
            order.append("Size: ").append(sizeSelection).append("\n");
            totalCost += sizeCost;

            // Toppings selection and cost
            int toppingCount = 0;
            if (topping1.isSelected()) { order.append("Topping: Pepperoni\n"); toppingCount++; }
            if (topping2.isSelected()) { order.append("Topping: Mushrooms\n"); toppingCount++; }
            if (topping3.isSelected()) { order.append("Topping: Onions\n"); toppingCount++; }
            if (topping4.isSelected()) { order.append("Topping: Sausage\n"); toppingCount++; }
            if (topping5.isSelected()) { order.append("Topping: Bacon\n"); toppingCount++; }
            if (topping6.isSelected()) { order.append("Topping: Extra cheese\n"); toppingCount++; }

            if (toppingCount == 0) {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this, "Please select at least one topping.");
                return;
            }

            double toppingCost = toppingCount * 1.00;
            totalCost += toppingCost;

            // Calculate subtotal, tax, and total
            double tax = totalCost * 0.07;
            double finalTotal = totalCost + tax;

            // Format and display the receipt
            order.append("\nSub-total: $").append(String.format("%.2f", totalCost));
            order.append("\nTax (7%): $").append(String.format("%.2f", tax));
            order.append("\nTotal: $").append(String.format("%.2f", finalTotal));

            receiptArea.setText(order.toString());
        }
    }

    // Listener for the Clear button
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset all selections
            thinCrust.setSelected(false);
            regularCrust.setSelected(false);
            deepDish.setSelected(false);
            sizeComboBox.setSelectedIndex(0);
            topping1.setSelected(false);
            topping2.setSelected(false);
            topping3.setSelected(false);
            topping4.setSelected(false);
            topping5.setSelected(false);
            topping6.setSelected(false);
            receiptArea.setText("");
        }
    }
}
