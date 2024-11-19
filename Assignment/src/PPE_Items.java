import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class PPE_Items implements Table_settings{

    // creates the gui
    JFrame frame = new JFrame("PPE items");

    JLabel item_name_label = new JLabel("Item name: ");
    JLabel supplier_id_label = new JLabel("Supplier id: ");
    JLabel header_label = new JLabel("PPE Items");

    JPanel ppe_header = new JPanel();

    JTextField item_name_fields = new JTextField();
    JTextField supplier_id_fields = new JTextField();

    // creates the buttons
    JButton return_button = new JButton("Return");
    JButton update_button = new JButton("Update");
    JButton load_button = new JButton("Load data");

    ImageIcon ppe_icon = new ImageIcon("PPE item.png");
    JLabel ppe_image = new JLabel(ppe_icon);
    ImageIcon return_icon = new ImageIcon("return.png");
    
    // create the table
    DefaultTableModel model;
    JTable item_table;

    PPE_Items() {
        // frame settings
        frame.getContentPane().setBackground(new Color(0x2B498F));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,600);
        frame.setLayout(null);
        frame.setResizable(false);

        item_name_label.setBounds(40,175,150,30);

        item_name_label.setForeground(new Color(0xC3C3BD));
        item_name_label.setFont(new Font("Monospaced", Font.BOLD,15));

        supplier_id_label.setBounds(40,225,150,25);

        supplier_id_label.setForeground(new Color(0xC3C3BD));
        supplier_id_label.setFont(new Font("Monospaced", Font.BOLD,15));

        item_name_fields.setBounds(200,175,250,25);
        supplier_id_fields.setBounds(200,225,250,25);

        // set the table column
        String[] column = {"Item code", "Item name", "Quantity", "Supplier"};
        model = new DefaultTableModel(column, 0);

        // add scrolling to the table
        item_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(item_table);
        scrollPane.setBounds(500,175,600,150);

        ppe_header.setLayout(null);
        ppe_header.setBackground(new Color(0xc7a406));
        ppe_header.setBounds(0,0,1200,100);
        ppe_header.add(header_label);
        ppe_header.add(ppe_image);
        ppe_header.add(return_button);

        header_label.setFont(new Font("Monospaced", Font.BOLD, 50));
        header_label.setBounds(20,20,300,55);

        ppe_image.setBounds(300,35,30,30);

        return_button.setBounds(1000,35,150,40);
        return_button.setBackground(Color.red);
        return_button.setFocusable(false);
        return_button.addActionListener(this::return_back);
        return_button.setIcon(return_icon);

        update_button.setBounds(650,350,100,25);
        update_button.addActionListener(this::update_record);
        update_button.setEnabled(false);
        update_button.setFocusable(false);

        load_button.setBounds(500,350,100,25);
        load_button.addActionListener(this::load_table);
        load_button.setFocusable(false);

        frame.add(ppe_header);

        frame.add(item_name_label);
        frame.add(supplier_id_label);

        frame.add(item_name_fields);
        frame.add(supplier_id_fields);

        frame.add(update_button);
        frame.add(load_button);

        frame.add(scrollPane);

        // block of executes when a row gets clicked
        item_table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {

                // set the field content into the fields
                int selected_row = item_table.getSelectedRow();
                if (selected_row != -1) {
                    item_name_fields.setText((String) item_table.getValueAt(selected_row, 1));
                    supplier_id_fields.setText((String) item_table.getValueAt(selected_row,3));

                    // allow delete button and update button to be usable
                    update_button.setEnabled(true);
                }
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void add_record(ActionEvent e) {

    }

    @Override
    public void update_record(ActionEvent e) {
        int selected_row = item_table.getSelectedRow();
        if (selected_row != -1) {

            boolean existing_supplier = false;

            try {
                BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");
                    if (Objects.equals(line_container[0], supplier_id_fields.getText())) {
                        existing_supplier = true;
                        break;
                    }
                }
                reader.close();

            } catch (IOException ioException) {
                System.out.println("Something went wrong: " + ioException);
            }

            if (existing_supplier) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));
                    BufferedWriter temp_writer = new BufferedWriter(new FileWriter("temp_file.txt"));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] line_container = line.split("<>");
                        if (Objects.equals(line_container[0], item_table.getValueAt(selected_row, 0))) {
                            temp_writer.write(
                                    item_table.getValueAt(selected_row, 0) + "<>" +
                                            item_name_fields.getText() + "<>" +
                                            item_table.getValueAt(selected_row, 2) + "<>" +
                                            supplier_id_fields.getText()
                            );
                            temp_writer.newLine();
                        } else {
                            temp_writer.write(line);
                            temp_writer.newLine();
                        }
                    }

                    reader.close();
                    temp_writer.close();

                    replace_file();

                    item_table.setValueAt(item_name_fields.getText(), selected_row, 1);
                    item_table.setValueAt(supplier_id_fields.getText(), selected_row, 3);

                    JOptionPane.showMessageDialog(null, "Record updated");

                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "something went wrong" + ioException);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Supplier id does not exist");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to update.");
        }
    }


    @Override
    public void delete_record(ActionEvent e) {

    }

    @Override
    public void load_table(ActionEvent e) {
        model.setRowCount(0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));
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
        
    }

    @Override
    public void return_back(ActionEvent e) {
        frame.dispose();
    }

    private void replace_file() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
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