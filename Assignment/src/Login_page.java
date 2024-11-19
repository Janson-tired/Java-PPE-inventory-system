import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class Login_page extends JFrame implements ActionListener {

    JFrame frame = new JFrame("Login");

    JLabel user_id_label = new JLabel("User id: ");
    JLabel password_label = new JLabel("password: ");
    JLabel message_label = new JLabel();

    JTextField user_id_label_field = new JTextField();
    JPasswordField password_label_field = new JPasswordField();
    JCheckBox show_password = new JCheckBox("Show password");

    JButton login_button = new JButton("Login");
    JButton reset_button = new JButton("Reset");

    ImageIcon logo = new ImageIcon("Box & Inc Co..png");
    JLabel logo_holder = new JLabel();

    Login_page() {

        frame.getContentPane().setBackground(new Color(0x2B498F));

        user_id_label.setBounds(100,300,150,25);
        user_id_label.setForeground(new Color(0xC3C3BD));
        user_id_label.setFont(new Font("Monospaced", Font.BOLD, 20));
        password_label.setBounds(100,350,150,25);
        password_label.setForeground(new Color(0xC3C3BD));
        password_label.setFont(new Font("Monospaced", Font.BOLD, 20));

        user_id_label_field.setBounds(250,300,200,25);
        password_label_field.setBounds(250,350,200,25);
        show_password.setBounds(250,400,200,25);
        show_password.addActionListener(this);

        login_button.setBackground(Color.green);
        login_button.setBounds(150,450,100,25);
        login_button.setFocusable(false);
        login_button.addActionListener(this);

        reset_button.setBackground(Color.red);
        reset_button.setBounds(300,450,100,25);
        reset_button.setFocusable(false);
        reset_button.addActionListener(this);

        message_label.setBounds(150,500,300,35);
        message_label.setFont(new Font("Monospaced",Font.BOLD,25));

        // Resize the image
        Image original_logo = logo.getImage();
        Image scaled_logo = original_logo.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        // Set the resized image to the JLabel
        logo_holder.setIcon(new ImageIcon(scaled_logo));
        logo_holder.setBounds(200, 50, 200, 200);  // Set position and size of the JLabel

        frame.add(user_id_label);
        frame.add(password_label);
        frame.add(user_id_label_field);
        frame.add(password_label_field);
        frame.add(message_label);
        frame.add(login_button);
        frame.add(reset_button);
        frame.add(logo_holder);
        frame.add(show_password);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==reset_button) {
            user_id_label_field.setText("");
            password_label_field.setText("");
        }

        if (e.getSource()==login_button) {

            try {
                boolean existing_id = false;
                String password = null;

                BufferedReader reader = new BufferedReader(new FileReader("Staff_record.txt"));
                String line;
                String user_id = null;
                String user_type = null;

                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");

                    // Check if the user ID matches
                    if (Objects.equals(line_container[0], user_id_label_field.getText())) {
                        existing_id = true;
                        user_id = line_container[0];
                        password = line_container[2];
                        user_type = line_container[3];
                        break;
                    }
                }

                reader.close();

                if (existing_id) {
                    if (String.valueOf(password_label_field.getPassword()).equals(password)) {
                        frame.dispose();
                        new Inventory(user_type, user_id);
                    } else {
                        message_label.setForeground(Color.red);
                        message_label.setText("Wrong password");
                    }
                } else {
                    message_label.setForeground(Color.red);
                    message_label.setText("User id not found");
                }

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Something went wrong: " + ioException);
            }
        }

        if (e.getSource() == show_password) {
            if (show_password.isSelected()) {
                password_label_field.setEchoChar((char) 0);
            } else {
                password_label_field.setEchoChar('•');
            }
        }
    }

    public static void main(String[] args) {
        Login_page login_page = new Login_page();
    }
}
