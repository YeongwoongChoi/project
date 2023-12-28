package gui;

import driver.Main;
import entity.Customer;
import view.CustomerView;

import java.awt.*;
import javax.swing.*;

public class ModifyInfo extends JFrame {
    final char LF = '\n';
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JLabel mainLabel = new JLabel("Modify your account");

    JTextArea phoneNumberArea = new JTextArea(5, 12);
    JTextArea nameArea = new JTextArea(1, 15);
    JButton cancelButton = new JButton("Cancel");
    JButton confirmButton = new JButton("Confirm");
    private Customer c;

    void setTextAreaOptions(JTextArea t) {
        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        t.setOpaque(true);
        t.setEditable(true);
    }

    void addComponents() {        // Before changing info.
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add(mainLabel, BorderLayout.NORTH);
        topPanel.setBackground(Color.getHSBColor(154, 254, 25));
        centerPanel.add(new JLabel("Name "));
        centerPanel.add(nameArea);
        centerPanel.add(new JLabel("Phone number: "));
        centerPanel.add(phoneNumberArea);

        nameArea.append(c.name);
        var contacts = Main.customerMgr.idToContacts.get(c.getIdentifier());

        StringBuilder sb = new StringBuilder();

        for (String phoneNumber: contacts)
            sb.append(phoneNumber).append(LF);

        phoneNumberArea.append(sb.toString());

        // make text areas editable
        setTextAreaOptions(phoneNumberArea);
        setTextAreaOptions(nameArea);
        bottomPanel.add(cancelButton, BorderLayout.WEST);
        bottomPanel.add(confirmButton, BorderLayout.EAST);
    }

    public ModifyInfo(Customer c) {
        this.c = c;
        setTitle("Modify Your Infomation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GUIManager.frameLocation(this);
        setLayout(new BorderLayout(3, 3));
        centerPanel.setLayout(new GridLayout(2, 2, 15, 15));
        addComponents();

        cancelButton.addActionListener(e -> dispose());
        confirmButton.addActionListener(e -> {
            String newName = nameArea.getText();
            String phoneNumbers = phoneNumberArea.getText();
            if (newName.isBlank())
                JOptionPane.showMessageDialog(null, "Name should be written.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            else if (phoneNumbers.isBlank())
                JOptionPane.showMessageDialog(null, "The number of phone numbers should be at least one.", "Error",
                        JOptionPane.ERROR_MESSAGE);

            CustomerView.engine.update(this.c, newName, phoneNumbers.split("\\R"));
            new Thread(() -> {
                for (int i = 0; i < 3; i++)
                    try { Thread.sleep(500); } catch (InterruptedException ex) { ex.printStackTrace(); }
            }).start();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Process Completed");
            Login.c = this.c; // Set up new information for the customer
            dispose();
        });
        setSize(450, 500);
        setVisible(true);
    }
}