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
public class Supplier implements Table_settings {

    // creates the gui
    JFrame frame = new JFrame("Suppliers");

    // creates the label
    JLabel supplier_id_label = new JLabel("Supplier id will be auto generated");
    JLabel supplier_name_label = new JLabel("Name: ");
    JLabel contact_label = new JLabel("Contact No.: ");
    JLabel address_label = new JLabel("Address: ");
    JLabel search_by_label = new JLabel("Search by: ");
    JLabel header_label = new JLabel("Supplier");

    JPanel supplier_header = new JPanel();

    // create the text fields
    JTextField supplier_name_fields = new JTextField();
    JTextField contact_fields = new JTextField();
    JTextField address_fields = new JTextField();
    JTextField search_by_field = new JTextField();

    // creates the drop-down menu for searching
    String[] search_type = {"ID", "Name", "Contact"};
    JComboBox<String> search_type_box = new JComboBox<>(search_type);

    // creates the buttons
    JButton return_button = new JButton("Return");
    JButton add_button = new JButton("Add supplier");
    JButton update_button = new JButton("Update");
    JButton delete_button = new JButton("Delete");
    JButton load_button = new JButton("Load data");
    JButton search_button = new JButton("Search");

    ImageIcon supplier_icon = new ImageIcon("supplier.png");
    JLabel supplier_image = new JLabel(supplier_icon);
    ImageIcon return_icon = new ImageIcon("return.png");

    // create the table
    DefaultTableModel model;
    JTable supplier_table;
    
    private String user_id;

    Supplier(String user_id) throws IOException {
        
        this.user_id = user_id;

        // frame settings
        frame.getContentPane().setBackground(new Color(0x2B498F));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,600);
        frame.setLayout(null);
        frame.setResizable(false);

        return_button.setBounds(1000,35,150,40);
        return_button.setBackground(Color.red);
        return_button.setFocusable(false);
        return_button.addActionListener(this::return_back);
        return_button.setIcon(return_icon);

        // set position for the buttons
        add_button.setBounds(500,350,100,25);
        add_button.addActionListener(this::add_record);
        add_button.setFocusable(false);

        update_button.setBounds(650,350,100,25);
        update_button.addActionListener(this::update_record);
        update_button.setEnabled(false);
        update_button.setFocusable(false);

        delete_button.setBounds(800,350,100,25);
        delete_button.addActionListener(this::delete_record);
        delete_button.setEnabled(false);
        delete_button.setFocusable(false);

        load_button.setBounds(950,350,100,25);
        load_button.addActionListener(this::load_table);
        load_button.setFocusable(false);

        search_button.setBounds(950,400,100,25);
        search_button.addActionListener(this::search_record);
        search_button.setFocusable(false);

        // set the table column
        String[] column = {"Supplier id", "Supplier name", "Contact No.", "Address"};
        model = new DefaultTableModel(column, 0);

        // add scrolling to the table
        supplier_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(supplier_table);
        scrollPane.setBounds(500,175,600,150);

        supplier_header.setLayout(null);
        supplier_header.setBackground(new Color(0xc7a406));
        supplier_header.setBounds(0,0,1200,100);
        supplier_header.add(header_label);
        supplier_header.add(supplier_image);
        supplier_header.add(return_button);

        header_label.setFont(new Font("Monospaced", Font.BOLD, 50));
        header_label.setBounds(20,20,300,55);

        supplier_image.setBounds(300,35,30,30);

        // set the position for label
        supplier_id_label.setBounds(40,130,400,30);
        supplier_name_label.setBounds(40,175,150,30);
        contact_label.setBounds(40,225,150,25);
        address_label.setBounds(40,275,150,25);
        search_by_label.setBounds(500,400,200,25);

        // change the font and color
        supplier_id_label.setForeground(new Color(0xC3C3BD));
        supplier_id_label.setFont(new Font("Monospaced", Font.BOLD,15));
        supplier_name_label.setForeground(new Color(0xC3C3BD));
        supplier_name_label.setFont(new Font("Monospaced", Font.BOLD,15));
        contact_label.setForeground(new Color(0xC3C3BD));
        contact_label.setFont(new Font("Monospaced", Font.BOLD,15));
        address_label.setForeground(new Color(0xC3C3BD));
        address_label.setFont(new Font("Monospaced", Font.BOLD,15));
        search_by_label.setForeground(new Color(0xC3C3BD));
        search_by_label.setFont(new Font("Monospaced", Font.BOLD,15));

        // set the text fields positions
        supplier_name_fields.setBounds(200,175,250,25);
        contact_fields.setBounds(200,225,250,25);
        address_fields.setBounds(200,275,250,75);
        search_by_field.setBounds(600,400,150,25);
        search_type_box.setBounds(800,400,100,25);

        frame.add(supplier_header);

        // add the labels
        frame.add(supplier_id_label);
        frame.add(supplier_name_label);
        frame.add(contact_label);
        frame.add(address_label);
        frame.add(search_by_label);

        // add the fields
        frame.add(supplier_name_fields);
        frame.add(contact_fields);
        frame.add(address_fields);
        frame.add(search_by_field);
        frame.add(search_type_box);

        // add the buttons to the frame
        frame.add(add_button);
        frame.add(update_button);
        frame.add(delete_button);
        frame.add(load_button);
        frame.add(search_button);

        frame.add(scrollPane);

        // block of executes when a row gets clicked
        supplier_table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {

                // set the field content into the fields
                int selected_row = supplier_table.getSelectedRow();
                if (selected_row != -1) {
                    supplier_name_fields.setText((String) supplier_table.getValueAt(selected_row, 1));
                    contact_fields.setText((String) supplier_table.getValueAt(selected_row,2));
                    address_fields.setText((String) supplier_table.getValueAt(selected_row,3));

                    // allow delete button and update button to be usable
                    delete_button.setEnabled(true);
                    update_button.setEnabled(true);
                }
            }
        });

        // make sure everything appears in the frame
        frame.setVisible(true);
    }

    @Override
    public void add_record(ActionEvent e) {
        if (supplier_name_fields.getText().isEmpty() ||
                contact_fields.getText().isEmpty() ||
                address_fields.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "cannot have empty field");
        } else {

            int new_id = 0;

            try {
                // read the id file
                BufferedReader reader = new BufferedReader(new FileReader("ID.txt"));
                
                BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));
                
                String line = reader.readLine();
                String[] line_container = line.split("<>");
                
                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                String activity_date = year + "-" + month + "-" + day;


                new_id = Integer.parseInt(line_container[2]);
                new_id++;

                // clear the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("ID.txt"));
                // write the new number in

                writer.write(
                        line_container[0] + "<>" +
                                line_container[1] + "<>" +
                                new_id + "<>" +
                                line_container[3]
                );
                writer.close();
                reader.close();
                
                ac_writer.write(    
                    user_id + "<>" +
                    "add" + "<>" +
                    "Suppliers" + "<>" +
                    activity_date
                );
                ac_writer.newLine();
                ac_writer.close();

            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }

            // store the input into a variable
            String[] new_record = {
                    "SP"+new_id,
                    supplier_name_fields.getText(),
                    contact_fields.getText(),
                    address_fields.getText()
            };
            // add record input into the table
            model.addRow(new_record);
            clearFields();
            JOptionPane.showMessageDialog(null, "record added");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Supplier.txt",true));
                writer.write(new_record[0]+"<>"+new_record[1]+"<>"+new_record[2]+"<>"+new_record[3]);
                writer.newLine();
                writer.close();
            } catch (IOException ioException) {
                System.out.println("something went wrong" + ioException);
            }

        }
    }

    @Override
    public void update_record(ActionEvent e) {
        int selected_row = supplier_table.getSelectedRow();
        if (selected_row != -1) {
            supplier_table.setValueAt(supplier_name_fields.getText(), selected_row, 1);
            supplier_table.setValueAt(contact_fields.getText(), selected_row, 2);
            supplier_table.setValueAt(address_fields.getText(), selected_row, 3);
            JOptionPane.showMessageDialog(null, "record updated");

            try {
                BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
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
                    if (Objects.equals(line_container[0], supplier_table.getValueAt(selected_row, 0))) {
                        temp_writer.write(
                                supplier_table.getValueAt(selected_row, 0) + "<>" +
                                        supplier_name_fields.getText() + "<>" +
                                        contact_fields.getText() + "<>" +
                                        address_fields.getText()
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
                    "Suppliers" + "<>" +
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

    @Override
    public void delete_record(ActionEvent e) {
        int selected_row = supplier_table.getSelectedRow();
        if (selected_row != -1) {
            int response = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this record?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            String delete_data = supplier_table.getValueAt(selected_row, 0).toString();

            // Check if user clicked 'Yes'
            if (response == JOptionPane.YES_OPTION) {
                model.removeRow(selected_row);
                clearFields();
            }

            try {
                BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
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
                    "Suppliers" + "<>" +
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

        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete");
        }
    }

    @Override
    public void load_table(ActionEvent e) {
        model.setRowCount(0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
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

    @Override
    public void search_record(ActionEvent e) {
        model.setRowCount(0);
        String search_text = search_by_field.getText();
        String search_type = (String) search_type_box.getSelectedItem();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
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

                    case "Contact" -> {
                        if (line_container[2].contains(search_text)) {
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

    @Override
    public void return_back(ActionEvent e) {
        frame.dispose();
    }

    private void replace_file() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Supplier.txt"));
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

    // a function that remove the content in all fields
    private void clearFields() {
        supplier_name_fields.setText("");
        contact_fields.setText("");
        address_fields.setText("");
        delete_button.setEnabled(false);
        update_button.setEnabled(false);
    }
}
