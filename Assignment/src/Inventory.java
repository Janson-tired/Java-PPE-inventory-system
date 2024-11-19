import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Inventory {

    // creates the gui
    JFrame frame = new JFrame("Inventory");

    JLabel inventory_label = new JLabel("  PPE Inventory");

    // importing the images
    ImageIcon user_icon = new ImageIcon("user.png");
    ImageIcon hospital_icon = new ImageIcon("hospital.png");
    ImageIcon supplier_icon = new ImageIcon("supplier.png");
    ImageIcon transactions_icon = new ImageIcon("transactions.png");
    ImageIcon log_out_icon = new ImageIcon("log_out.png");
    ImageIcon activity_icon = new ImageIcon("activity_icon.png");
    ImageIcon PPE_item_icon = new ImageIcon("PPE item.png");

    // create panels
    JPanel button_container = new JPanel();
    JPanel inventory_header = new JPanel();

    // creates the redirect buttons
    JButton users_button = new JButton("Users");
    JButton hospitals_button = new JButton("Hospitals");
    JButton suppliers_button = new JButton("Suppliers");
    JButton transactions_button = new JButton("Transaction");
    JButton PPE_button = new JButton("PPE Items");
    JButton activity_button = new JButton("Activity Log");
    JButton log_out_button = new JButton("Log out");

    private String user_id;
    
    Inventory(String user_type, String user_id) {
        
        this.user_id = user_id;

        // frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().setBackground(new Color(0x2B498F));

        inventory_header.setBackground(new Color(0xc7a406));
        inventory_header.setPreferredSize(new Dimension(500,100));
        inventory_header.setLayout(new BorderLayout());

        inventory_label.setFont(new Font("Monospaced", Font.BOLD, 30));

        button_container.setBackground(new Color(0x2B498F));

        Dimension buttonSize = new Dimension(200, 50);
        Dimension panel_size = new Dimension(500,300);

        // no button focusable
        users_button.setFocusable(false);
        hospitals_button.setFocusable(false);
        suppliers_button.setFocusable(false);
        transactions_button.setFocusable(false);
        PPE_button.setFocusable(false);
        activity_button.setFocusable(false);
        log_out_button.setFocusable(false);

        // set icons into the buttons
        users_button.setIcon(user_icon);
        hospitals_button.setIcon(hospital_icon);
        suppliers_button.setIcon(supplier_icon);
        transactions_button.setIcon(transactions_icon);
        PPE_button.setIcon(PPE_item_icon);
        activity_button.setIcon(activity_icon);
        log_out_button.setIcon(log_out_icon);

        // set button sizes
        users_button.setPreferredSize(buttonSize);
        hospitals_button.setPreferredSize(buttonSize);
        suppliers_button.setPreferredSize(buttonSize);
        transactions_button.setPreferredSize(buttonSize);
        PPE_button.setPreferredSize(buttonSize);
        activity_button.setPreferredSize(buttonSize);
        log_out_button.setPreferredSize(buttonSize);

        // add action listeners to buttons (no exception handling needed unless necessary)
        users_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new User_record(user_id);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        hospitals_button.addActionListener(e -> {
            try {
                new Hospitals(user_id);  // Call Hospitals
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        suppliers_button.addActionListener(e -> {
            try {
                new Supplier(user_id);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        transactions_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Transactions(user_id);
            }
        });

        PPE_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PPE_Items();
            }
        });
        
        activity_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Activity_Log();
            }
        });


        log_out_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Login_page();
            }
        });

        // set the layout of the header
        inventory_header.add(inventory_label, BorderLayout.CENTER);

        // Set the layout of the button container and add buttons
        button_container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        button_container.setPreferredSize(panel_size);
       
        button_container.add(hospitals_button);
        button_container.add(suppliers_button);
        button_container.add(transactions_button);
        button_container.add(PPE_button);
        
        if (Objects.equals(user_type, "admin")) {
             button_container.add(users_button);
             button_container.add(activity_button);
        }
        
        button_container.add(log_out_button);

        // red button for log out
        log_out_button.setBackground(new Color(0xd54728));

        // Add the button container to the center of the frame
        frame.add(inventory_header);
        frame.add(button_container);

        frame.setVisible(true);
    }
}
