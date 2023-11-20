package gui;

import driver.DatabaseInfo;
import driver.Main;
import driver.Manager;
import entity.Customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignUp extends JFrame implements Frame {
    Manager<Customer> cManager = Main.customerMgr;

    private final Image img = new ImageIcon("img/restaurantLogo.jpg").getImage().getScaledInstance(150, 150,
            Image.SCALE_DEFAULT);

    JButton confirmButton, cancelButton;
    JLabel login = new JLabel(new ImageIcon(img));
    Container contentPane = getContentPane();
    BackgroundPanel backPanel;
    JPanel topPan = new JPanel();
    JPanel btnPan = new JPanel(new BorderLayout());
    JPanel fieldPan = new JPanel();
    JPanel buttonPan = new JPanel();

    JLabel topLabel = new JLabel("Sign Up");
    JLabel mLabel = new JLabel("Go ahead!");
    JLabel constraintsLabel = new JLabel("They should be comma separated if you enter more than 1");
    final String CONSTRAINT = "(,|, )";
    private RoundedTextField identifierField = new RoundedTextField(21);
    private RoundedTextField nameField = new RoundedTextField(21);
    private JComboBox <String> sexField = new JComboBox<>(new String[]{"Male", "Female"});
    private RoundedTextField ageField = new RoundedTextField(21);
    private RoundedTextField phoneNumberField = new RoundedTextField(21);
    private RoundedTextField preferableStyleField = new RoundedTextField(21);
    private RoundedTextField preferableShowField = new RoundedTextField(21);

    void setField(RoundedTextField f) {
        fieldPan.add(f);
        f.setForeground(Color.GRAY);
        f.setFont(f.getFont().deriveFont(18.0f));
    }

    void setFocusListener(RoundedTextField f) {
        String initialText = f.getText();
        f.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String input = f.getText();
                if (input.equals(initialText))
                    f.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String input = f.getText();
                if (input.isBlank()) {
                    nameField.setForeground(Color.GRAY);
                    f.setText(initialText);
                }
            }
        });
    }

    boolean hasError(String name, String id, String [] phone) {
        boolean flag = true;

        if (name.isBlank())
            JOptionPane.showMessageDialog(null,
                    "Your name should be written here", "Error", JOptionPane.ERROR_MESSAGE);
        else if (id.isBlank())
            JOptionPane.showMessageDialog(null,
                    "Your ID should be written here", "Error", JOptionPane.ERROR_MESSAGE);
        else if (Arrays.stream(phone).anyMatch(p -> isDuplicate(id, p)))
            JOptionPane.showMessageDialog(null,
                    "You already signed up using your number", "Error", JOptionPane.ERROR_MESSAGE);
        else
            flag = false;

        return flag;
    }

    boolean isSameAsInitialized(JPanel p) {
        boolean flag = false;
        Component [] components = p.getComponents();
        List <RoundedTextField> fieldList = Arrays.stream(components)
                .filter(c -> c.getClass().equals(RoundedTextField.class))
                .map(c -> (RoundedTextField)c).collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            if (FieldText.NAME[i].equals(fieldList.get(i).getText()))
                flag = true;
        }
        if (flag)
            JOptionPane.showMessageDialog(null,
                    "One of the mandatory field was empty.", "Error", JOptionPane.ERROR_MESSAGE);

        return flag;
    }

    boolean checkOptionalField(RoundedTextField [] fields) {
        final String [] internalString = { fields[0].getText(), fields[1].getText() };
        boolean [] flags = { (internalString[0].isBlank() || FieldText.NAME[4].equals(internalString[0])),
                (internalString[1].isBlank() || FieldText.NAME[5].equals(internalString[1])) };

        for (int i = 0; i < 2; i++) {
            if (flags[i])
                fields[i].setText("");
        }
        return flags[0] && flags[1];
    }

    private static class FieldText {
        static final String [] NAME = { "your name", "your ID", "your age", "your phone number(e.g. 123-456-7890)",
                "your preference for dish styles", "your preference for TV shows" };
    }

    public SignUp() {
        backPanel = new BackgroundPanel(new ImageIcon("img/loginBackground.jpg").getImage());

        confirmButton = new JButton(new ImageIcon("img/complete_off.png"));
        GUIManager.setButtonProperties(confirmButton);
        GUIManager.setMouseListener(confirmButton, "img/complete_on.png", "img/complete_off.png");

        cancelButton = new JButton(new ImageIcon("img/cancel_off.png"));
        GUIManager.setButtonProperties(cancelButton);
        GUIManager.setMouseListener(cancelButton, "img/cancel_on.png", "img/cancel_off.png");

        backPanel.setLayout(new BorderLayout());
        topPan.setLayout(new BoxLayout(topPan, BoxLayout.Y_AXIS));
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setOpaque(false);
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        constraintsLabel.setFont(constraintsLabel.getFont().deriveFont(14.0f));

        topLabel.setBorder(new EmptyBorder(10, 20, 20, 20));
        topPan.setBorder(new EmptyBorder(0, 0, 20, 0));
        topPan.add(topLabel);
        topPan.add(login);
        topPan.add(mLabel);
        topPan.setOpaque(false);

        fieldPan.setBorder(new EmptyBorder(10, 10, 10, 10));
        fieldPan.setLayout(new BoxLayout(fieldPan, BoxLayout.Y_AXIS));

        setField(nameField);
        nameField.setText(FieldText.NAME[0]);

        setField(identifierField);
        identifierField.setText(FieldText.NAME[1]);
        fieldPan.add(sexField);
        setField(ageField);
        ageField.setText(FieldText.NAME[2]);
        fieldPan.setOpaque(false);
        setField(phoneNumberField);
        phoneNumberField.setText(FieldText.NAME[3]);
        fieldPan.add(constraintsLabel);

        setField(preferableStyleField);
        preferableStyleField.setText(FieldText.NAME[4]);
        fieldPan.add(constraintsLabel);
        setField(preferableShowField);
        preferableShowField.setText(FieldText.NAME[5]);
        fieldPan.add(constraintsLabel);

        buttonPan.add(cancelButton, BorderLayout.WEST);
        buttonPan.add(confirmButton, BorderLayout.EAST);
        buttonPan.setOpaque(false);
        buttonPan.setBorder(new EmptyBorder(40, 40, 20, 40));

        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
        backPanel.add(topPan);
        backPanel.add(fieldPan);
        backPanel.add(btnPan);
        backPanel.add(buttonPan);
        backPanel.setLayout(new FlowLayout(1, 0, 20));
        backPanel.setPreferredSize(new Dimension(550, 800));

        cancelButton.addActionListener(e -> dispose());
        confirmButton.addActionListener(e -> {
                    String identifier = identifierField.getText();
                    String name = nameField.getText();
                    String sex = (String) sexField.getSelectedItem();
                    String[] phoneNumber = phoneNumberField.getText().split(CONSTRAINT);

                    if (!hasError(name, identifier, phoneNumber) && !isSameAsInitialized(fieldPan) &&
                            checkOptionalField(new RoundedTextField[]{preferableStyleField, preferableShowField})) {
                        Customer c = new Customer();
                        int age = Integer.parseInt(ageField.getText());
                        c.setIdentifier(identifier);
                        c.name = name;
                        c.age = age;
                        c.earnedPoint = 0;
                        c.sex = sex;

                        Connection conn = new DatabaseInfo().makeConnection();
                        try {
                            PreparedStatement ps = conn.prepareStatement("insert into customer values(?, ?, ?, ?, 0)");
                            ps.setString(1, identifier);
                            ps.setString(2, name);
                            ps.setString(3, sex);
                            ps.setInt(4, age);
                            ps.executeUpdate();

                            HashSet<String> contacts = new HashSet<>();

                            for (String phone : phoneNumber) {
                                ps = conn.prepareStatement("insert into customercontact values(?, ?)");
                                ps.setString(1, identifier);
                                ps.setString(2, phone);
                                contacts.add(phone);
                                ps.executeUpdate();
                            }
                            cManager.idToContacts.put(identifier, contacts);
                            String styleText = preferableStyleField.getText();
                            String showText = preferableShowField.getText();
                            if (!styleText.isBlank()) {
                                String[] preferenceForStyle = styleText.split(CONSTRAINT);
                                for (String preference : preferenceForStyle) {
                                    ps = conn.prepareStatement("insert into prefer values(?, ?);");
                                    ps.setString(1, identifier);
                                    ps.setString(2, preference);
                                    ps.executeUpdate();
                                }
                            }

                            if (!showText.isBlank()) {
                                String[] preferenceForShow = showText.split(CONSTRAINT);
                                for (String preference : preferenceForShow) {
                                    ps = conn.prepareStatement("insert into watch values(?, ?);");
                                    ps.setString(1, identifier);
                                    ps.setString(2, preference);
                                    ps.executeUpdate();
                                }
                            }
                        } catch (SQLException exception) {
                            System.out.println("[Sign Up] SQL query failed");
                            exception.printStackTrace();
                        }
                        cManager.addEntity(c);
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Sign-up process is done");
                        dispose();
                    }
                });
        setFocusListener(nameField);
        setFocusListener(phoneNumberField);
        setFocusListener(preferableStyleField);
        setFocusListener(preferableShowField);
        setFocusListener(ageField);
        setFocusListener(identifierField);

        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GUIManager.frameLocation(this);
        GUIManager.customCursor(this);
        contentPane.add(backPanel);
        pack();
        setSize(950, 850);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    boolean isDuplicate(String id, String phoneNumber) { return cManager.find(id, phoneNumber) != null; }

    @Override
    public void addComponents(JPanel panel) { }

    /*public class RoundedPasswordField extends JPasswordField {
        private Shape shape;
        public RoundedPasswordField(int size) {
            super(size);
            setOpaque(false); // As suggested by @AVD in comment.
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(new Color(246, 229, 141));
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }*/
}