import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class Activity_Log {
    
    JFrame frame = new JFrame("Activity");
    
    // create the table
    DefaultTableModel model;
    JTable transaction_table;
    
    JLabel header_label = new JLabel("Activity");
    
    JPanel activity_header = new JPanel();
    JPanel input_panel = new JPanel();
    
    ImageIcon activity_icon = new ImageIcon("activity_icon.png");
    JLabel activity_image = new JLabel(activity_icon);
    
    JLabel search_by_label = new JLabel("Search by: ");
    JLabel start_date_label = new JLabel("Start date: ");
    JLabel end_date_label = new JLabel("End date: ");
    
    JTextField search_by_field = new JTextField();
    JTextField start_date_field = new JTextField();
    JTextField end_date_field = new JTextField();
    
    ImageIcon return_icon = new ImageIcon("return.png");
    
    JButton return_button = new JButton("Return");
    JButton search_button = new JButton("Search");
    JButton load_button = new JButton("Load data");
    
    // creates the drop-down menu for searching
    String[] search_type = {"ID", "Date"};
    JComboBox<String> search_type_box = new JComboBox<>(search_type);
    
    Activity_Log() {
        
        // frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().setBackground(new Color(0x2B498F));
        
        activity_image.setBounds(270,35,30,30);
        
        activity_header.setLayout(null);
        activity_header.setBackground(new Color(0xc7a406));
        activity_header.setPreferredSize(new Dimension(500,100));
        activity_header.add(header_label);
        activity_header.add(activity_image);
        activity_header.add(return_button);
        
        header_label.setFont(new Font("Monospaced", Font.BOLD, 50));
        header_label.setBounds(20,20,400,55);
        search_by_label.setForeground(new Color(0xC3C3BD));
        search_by_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        start_date_label.setForeground(new Color(0xC3C3BD));
        start_date_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        end_date_label.setForeground(new Color(0xC3C3BD));
        end_date_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        
        search_by_label.setBounds(20,15,100,25);
        start_date_label.setBounds(20,60,150,25);
        end_date_label.setBounds(150,60,100,25);
        
        search_by_field.setBounds(20,40,100,25);
        start_date_field.setBounds(20,90,100,25);
        end_date_field.setBounds(150,90,100,25);
        
        search_type_box.setBounds(150,40,100,25);
        
        return_button.setBounds(320,35,150,40);
        return_button.setBackground(Color.red);
        return_button.setFocusable(false);
        return_button.addActionListener(this::return_back);
        return_button.setIcon(return_icon);
        
        search_button.setBounds(330,30,100,25);
        search_button.setFocusable(false);
        search_button.addActionListener(this::search_record);
        
        load_button.setBounds(330,90,100,25);
        load_button.setFocusable(false);
        load_button.addActionListener(this::load_table);
        
        // set the table column
        String[] column = {"User", "Activity", "Table", "Date"};
        model = new DefaultTableModel(column, 0);

        // add scrolling to the table
        transaction_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(transaction_table);    
        scrollPane.setPreferredSize(new Dimension(450,200));
        
        input_panel.setBackground(new Color(0x2B498F));
        input_panel.setLayout(null);
        input_panel.setPreferredSize(new Dimension(450,150));
        
        input_panel.add(search_by_label);
        input_panel.add(start_date_label);
        input_panel.add(end_date_label);
        input_panel.add(search_type_box);
        
        input_panel.add(search_button);
        input_panel.add(load_button);
        
        input_panel.add(search_by_field);
        input_panel.add(start_date_field);
        input_panel.add(end_date_field);
        
        frame.add(activity_header);
        frame.add(scrollPane);
        frame.add(input_panel);
        
        frame.setVisible(true);
    }
    
    private void return_back(ActionEvent e) {
        frame.dispose();
    }
    
    private void load_table(ActionEvent e) {
        model.setRowCount(0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("activity_log.txt"));
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
    
    private void search_record(ActionEvent e) {
        model.setRowCount(0);
        String search_text = search_by_field.getText();
        String search_type = (String) search_type_box.getSelectedItem();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("activity_log.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                
                String[] new_record = {
                    line_container[0],
                    line_container[1],
                    line_container[2],
                    line_container[3],
                };
                
                switch (search_type) {

                    case "ID" -> {
                        if (line_container[0].contains(search_text)) {
                            model.addRow(new_record);
                        }
                    }
                    
                    case "Date" -> {
                        
                        LocalDate start_date = LocalDate.parse(start_date_field.getText());
                        LocalDate end_date = LocalDate.parse(end_date_field.getText());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        
                        String date_string = line_container[3];
                        LocalDate date = LocalDate.parse(date_string, formatter);
                        
                        if (!date.isBefore(start_date) && !date.isAfter(end_date)) {
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
    
}
