import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class User_record extends JFrame implements Table_settings {

    // creates the gui
    JFrame frame = new JFrame("User records");

    // create the label for text inputs
    JLabel user_id_label = new JLabel("User id will be auto generated ");
    JLabel user_name_label = new JLabel("Name: ");
    JLabel user_password_label = new JLabel("Password: ");
    JLabel search_by_label = new JLabel("Search by: ");
    JLabel header_label = new JLabel("Users");

    JPanel user_header = new JPanel();

    // creates the text fields
    JTextField user_name_field = new JTextField();
    JTextField user_password_field = new JTextField();
    JTextField search_by_field = new JTextField();

    // creates the drop-down menu for staff type
    String[] staff_type = {"admin", "user"};
    JComboBox<String> user_type_box = new JComboBox<>(staff_type);

    // creates the drop-down menu for searching
    String[] search_type = {"ID", "Name", "Type"};
    JComboBox<String> search_type_box = new JComboBox<>(search_type);

    // creates the buttons
    JButton return_button = new JButton("Return");
    JButton add_button = new JButton("add user");
    JButton update_button = new JButton("update");
    JButton delete_button = new JButton("delete user");
    JButton load_button = new JButton("load data");
    JButton search_button = new JButton("Search");

    ImageIcon user_icon = new ImageIcon("user.png");
    JLabel user_image = new JLabel(user_icon);
    ImageIcon return_icon = new ImageIcon("return.png");

    // creates the columns and table
    DefaultTableModel model;
    JTable user_table;
    
    private String user_id;

    User_record(String user_id) throws IOException {
        
        this.user_id = user_id;
        
        // frame settings
        frame.getContentPane().setBackground(new Color(0x2B498F));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950,600);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // creates the column contents
        String[] columns = {"staff id", "staff name", "password", "staff type"};
        model = new DefaultTableModel(columns, 0);

        // add the column to table and allow scrolling
        user_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(user_table);
        scrollPane.setBounds(300,175,600,150);

        user_header.setLayout(null);
        user_header.setBackground(new Color(0xc7a406));
        user_header.setBounds(0,0,950,100);
        user_header.add(header_label);
        user_header.add(user_image);
        user_header.add(return_button);

        header_label.setFont(new Font("Monospaced", Font.BOLD, 50));
        header_label.setBounds(20,30,300,40);

        user_image.setBounds(200,35,30,30);

        // set position for the label
        user_id_label.setBounds(40,130,300,25);
        user_name_label.setBounds(40,200,200,25);
        user_password_label.setBounds(40,250,200,25);
        search_by_label.setBounds(300,400,200,25);

        // change the font and color
        user_id_label.setForeground(new Color(0xC3C3BD));
        user_name_label.setForeground(new Color(0xC3C3BD));
        user_password_label.setForeground(new Color(0xC3C3BD));
        search_by_label.setForeground(new Color(0xC3C3BD));

        user_id_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        user_name_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        user_password_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        search_by_label.setFont(new Font("Monospaced", Font.BOLD, 15));

        // set position for the text inputs
        user_name_field.setBounds(120,200,150,25);
        user_password_field.setBounds(120,250,150,25);
        search_by_field.setBounds(400,400,150,25);
        user_type_box.setBounds(120,300,100,25);
        search_type_box.setBounds(600,400,100,25);

        return_button.setBounds(750,35,150,40);
        return_button.setBackground(Color.red);
        return_button.setFocusable(false);
        return_button.addActionListener(this::return_back);
        return_button.setIcon(return_icon);

        // set position for the buttons
        add_button.setBounds(300,350,100,25);
        add_button.addActionListener(this::add_record);
        add_button.setFocusable(false);

        update_button.setBounds(450,350,100,25);
        update_button.addActionListener(this::update_record);
        update_button.setEnabled(false);
        update_button.setFocusable(false);

        delete_button.setBounds(600,350,100,25);
        delete_button.addActionListener(this::delete_record);
        delete_button.setEnabled(false);
        delete_button.setFocusable(false);

        load_button.setBounds(750,350,100,25);
        load_button.addActionListener(this::load_table);
        load_button.setFocusable(false);

        search_button.setBounds(750,400,100,25);
        search_button.addActionListener(this::search_record);
        search_button.setFocusable(false);

        frame.add(user_header);

        // add the labels to frame
        frame.add(user_id_label);
        frame.add(user_name_label);
        frame.add(user_password_label);
        frame.add(search_by_label);

        // add the text input to the frame
        frame.add(user_name_field);
        frame.add(user_password_field);
        frame.add(search_by_field);
        frame.add(user_type_box);
        frame.add(search_type_box);

        // add the table to frame
        frame.add(scrollPane);

        // add the buttons to the frame
        frame.add(add_button);
        frame.add(update_button);
        frame.add(delete_button);
        frame.add(load_button);
        frame.add(search_button);

        // block of executes when a row gets clicked
        user_table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {

                // set the field content into the fields
                int selected_row = user_table.getSelectedRow();
                if (selected_row != -1) {
                    user_name_field.setText((String) user_table.getValueAt(selected_row, 1));
                    user_password_field.setText((String) user_table.getValueAt(selected_row, 2));
                    String staffType = user_table.getValueAt(selected_row, 3).toString();
                    user_type_box.setSelectedItem(staffType);

                    // allow delete button and update button to be usable
                    delete_button.setEnabled(true);
                    update_button.setEnabled(true);
                }
            }
        });
    }


    public void return_back(ActionEvent event) {
        frame.dispose();
    }

    // add user function
    @Override
    public void add_record(ActionEvent e) {
        if (user_name_field.getText().isEmpty() ||
                user_password_field.getText().isEmpty() ||
                user_type_box.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "cannot have empty field");
        } else {

            int new_id = 0;

            try {
                
                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                String activity_date = year + "-" + month + "-" + day;
                
                // read the id file
                BufferedReader reader = new BufferedReader(new FileReader("ID.txt"));
                BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));
                
                String line = reader.readLine();
                String[] line_container = line.split("<>");

                new_id = Integer.parseInt(line_container[0]);
                new_id++;

                // clear the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("ID.txt"));
                // write the new number in

                writer.write(
                        new_id + "<>" +
                        line_container[1] + "<>" +
                        line_container[2] + "<>" +
                        line_container[3]
                );
                writer.close();
                reader.close();
               
                ac_writer.write(    
                    user_id + "<>" +
                    "add" + "<>" +
                    "Users" + "<>" +
                    activity_date
                );
                ac_writer.newLine();
                ac_writer.close();

            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }

            // store the input into a variable
            String[] new_record = {
                    "User" + new_id,
                    user_name_field.getText(),
                    user_password_field.getText(),
                    (String) user_type_box.getSelectedItem()
            };
            // add record input into the table
            model.addRow(new_record);
            clearFields();
            JOptionPane.showMessageDialog(null, "record added");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Staff_record.txt", true));
                writer.write(new_record[0] + "<>" + new_record[1] + "<>" + new_record[2] + "<>" + new_record[3]);
                writer.newLine();
                writer.close();
            } catch (IOException ioException) {
                System.out.println("something went wrong" + ioException);
            }

        }
    }

    // update button function
    public void update_record(ActionEvent e) {
        int selected_row = user_table.getSelectedRow();
        if (selected_row != -1) {
            user_table.setValueAt(user_name_field.getText(), selected_row, 1);
            user_table.setValueAt(user_password_field.getText(), selected_row, 2);
            user_table.setValueAt(user_type_box.getSelectedItem(), selected_row, 3);
            JOptionPane.showMessageDialog(null, "record updated");

            try {
                BufferedReader reader = new BufferedReader(new FileReader("Staff_record.txt"));
                BufferedWriter temp_writer = new BufferedWriter(new FileWriter("temp_file.txt"));
                
                BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));
                String line;
                
                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                String activity_date = year + "-" + month + "-" + day;

                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");
                    if (Objects.equals(line_container[0], user_table.getValueAt(selected_row, 0))) {
                        temp_writer.write(
                                user_table.getValueAt(selected_row, 0) + "<>" +
                                        user_name_field.getText() + "<>" +
                                        user_password_field.getText() + "<>" +
                                        user_type_box.getSelectedItem()
                        );
                        temp_writer.newLine();
                    } else {
                        temp_writer.write(line);
                        temp_writer.newLine();
                    }
                }
                
                ac_writer.write(    
                    user_id + "<>" +
                    "update" + "<>" +
                    "Users" + "<>" +
                    activity_date
                );
                ac_writer.newLine();
                ac_writer.close();


                reader.close();
                temp_writer.close();

                replace_file();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "something went wrong" + ioException);
            }
        }
    }

    // delete button function
    public void delete_record(ActionEvent e) {
        int selected_row = user_table.getSelectedRow();
        if (selected_row != -1) {
            int response = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this record?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            String delete_data = user_table.getValueAt(selected_row, 0).toString();

            // Check if user clicked 'Yes'
            if (response == JOptionPane.YES_OPTION) {
                model.removeRow(selected_row);
                clearFields();
            }

            try {
                BufferedReader reader = new BufferedReader(new FileReader("Staff_record.txt"));
                BufferedWriter temp_writer = new BufferedWriter(new FileWriter("temp_file.txt"));
                
                BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));
                String line;
                
                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                String activity_date = year + "-" + month + "-" + day;


                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");
                    if (Objects.equals(line_container[0], delete_data)) {
                        continue;
                    } else {
                        temp_writer.write(line);
                        temp_writer.newLine();
                    }
                }
                
                ac_writer.write(    
                    user_id + "<>" +
                    "delete" + "<>" +
                    "Users" + "<>" +
                    activity_date
                );
                ac_writer.newLine();

                reader.close();
                temp_writer.close();

                replace_file();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "something went wrong" + ioException);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete");
        }
    }

    // load data into the table
    public void load_table(ActionEvent e) {
        model.setRowCount(0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Staff_record.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                String[] file_record = {line_container[0], line_container[1], line_container[2], line_container[3]};
                model.addRow(file_record);
            }
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Something went wrong while loading the file: " + ioException.getMessage());
        }
    }

    // search user
    public void search_record(ActionEvent e) {
        model.setRowCount(0);
        String search_text = search_by_field.getText();
        String search_type = (String) search_type_box.getSelectedItem();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Staff_record.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                switch (search_type) {

                    case "ID" -> {
                        if (line_container[0].contains(search_text)) {
                            String[] new_record = {
                                    line_container[0],
                                    line_container[1],
                                    line_container[2],
                                    line_container[3]
                            };
                            model.addRow(new_record);
                        }
                    }

                    case "Name" -> {
                        if (line_container[1].contains(search_text)) {
                            String[] new_record = {
                                    line_container[0],
                                    line_container[1],
                                    line_container[2],
                                    line_container[3]
                            };
                            model.addRow(new_record);
                        }
                    }

                    case "Type" -> {
                        if (line_container[3].contains(search_text)) {
                            String[] new_record = {
                                    line_container[0],
                                    line_container[1],
                                    line_container[2],
                                    line_container[3]
                            };
                            model.addRow(new_record);
                        }
                    }
                    case null -> {}
                    default -> throw new IllegalStateException("Unexpected value: " + search_type);
                }
            }
        } catch (IOException ioException) {
            System.out.println("something went wrong " + ioException);
        }
    }

    // a function that remove the content in all fields
    private void clearFields() {
        user_name_field.setText("");
        user_password_field.setText("");
        user_type_box.setSelectedIndex(0);
        delete_button.setEnabled(false);
        update_button.setEnabled(false);
    }

    private void replace_file() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Staff_record.txt"));
            BufferedReader temp_reader = new BufferedReader(new FileReader("temp_file.txt"));
            String line;

            while ((line = temp_reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            temp_reader.close();

        } catch (IOException ioException) {
            System.out.println("something went wrong " + ioException);
        }
    }
}
