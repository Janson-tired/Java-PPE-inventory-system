import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class Transactions {

    JFrame frame = new JFrame("PPE items");

    JPanel transaction_header = new JPanel();

    JLabel header_label = new JLabel("Transactions");
    JLabel transaction_id_label = new JLabel("Transaction id will be auto generated");
    JLabel quantity_label = new JLabel("Quantity: ");
    JLabel hospital_label = new JLabel("To Hospital: ");
    JLabel search_by_label = new JLabel("Search by: ");
    JLabel start_date_label = new JLabel("Start date: ");
    JLabel end_date_label = new JLabel("End date: ");

    JTextField quantity_fields = new JTextField();
    JTextField hospital_fields = new JTextField();
    JTextField search_by_field = new JTextField();
    JTextField start_date_field = new JTextField();
    JTextField end_date_field = new JTextField();

    ImageIcon transaction_icon = new ImageIcon("transactions.png");
    JLabel transaction_image = new JLabel(transaction_icon);
    ImageIcon return_icon = new ImageIcon("return.png");

    JButton add_button = new JButton("Add");
    JButton return_button = new JButton("Return");
    JButton load_button = new JButton("Load data");
    JButton search_button = new JButton("Search");

    // creates the drop-down menu for item code
    String[] action_type = {"Distribute", "Received"};
    JComboBox<String> action_box = new JComboBox<>(action_type);

    // creates the drop-down menu for item code
    String[] item_code = {"HC", "FS", "MS", "GL", "GW", "SC"};
    JComboBox<String> item_box = new JComboBox<>(item_code);

    // creates the drop-down menu for searching
    String[] search_type = {"ID", "Distribute/Received", "Item", "Date"};
    JComboBox<String> search_type_box = new JComboBox<>(search_type);

    // create the table
    DefaultTableModel model;
    JTable transaction_table;
    
    private String user_id;

    Transactions(String user_id) {
        
        this.user_id = user_id;
        
        // frame settings
        frame.getContentPane().setBackground(new Color(0x2B498F));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,600);
        frame.setLayout(null);
        frame.setResizable(false);

        transaction_id_label.setBounds(40,130,400,30);
        quantity_label.setBounds(40,175,150,30);
        hospital_label.setBounds(40,225,150,30);
        search_by_label.setBounds(500,400,200,25);
        start_date_label.setBounds(500,450,200,25);
        end_date_label.setBounds(700,450,200,25);

        // change the font and color
        transaction_id_label.setForeground(new Color(0xC3C3BD));
        transaction_id_label.setFont(new Font("Monospaced", Font.BOLD,15));
        quantity_label.setForeground(new Color(0xC3C3BD));
        quantity_label.setFont(new Font("Monospaced", Font.BOLD,15));
        hospital_label.setForeground(new Color(0xC3C3BD));
        hospital_label.setFont(new Font("Monospaced", Font.BOLD,15));
        header_label.setFont(new Font("Monospaced", Font.BOLD, 50));
        search_by_label.setForeground(new Color(0xC3C3BD));
        search_by_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        start_date_label.setForeground(new Color(0xC3C3BD));
        start_date_label.setFont(new Font("Monospaced", Font.BOLD, 15));
        end_date_label.setForeground(new Color(0xC3C3BD));
        end_date_label.setFont(new Font("Monospaced", Font.BOLD, 15));

        // set the table column
        String[] column = {"Transactions id", "Distributed/Received", "From/To", "Item Code", "Quantity", "Date"};
        model = new DefaultTableModel(column, 0);

        // add scrolling to the table
        transaction_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(transaction_table);
        scrollPane.setBounds(500,175,600,150);

        header_label.setBounds(20,20,400,55);

        quantity_fields.setBounds(200,175,50,25);
        hospital_fields.setBounds(200,225,100,25);
        search_by_field.setBounds(750,400,150,25);
        search_type_box.setBounds(600,400,100,25);
        start_date_field.setBounds(500,475,150,25);
        end_date_field.setBounds(700,475,150,25);

        transaction_image.setBounds(400,35,30,30);

        action_box.setBounds(200,275,200,25);
        item_box.setBounds(200,325,100,25);

        transaction_header.setLayout(null);
        transaction_header.setBackground(new Color(0xc7a406));
        transaction_header.setBounds(0,0,1200,100);
        transaction_header.add(header_label);
        transaction_header.add(transaction_image);
        transaction_header.add(return_button);

        return_button.setBounds(1000,35,150,40);
        return_button.setBackground(Color.red);
        return_button.setFocusable(false);
        return_button.addActionListener(this::return_back);
        return_button.setIcon(return_icon);

        add_button.setBounds(500,350,100,25);
        add_button.addActionListener(this::add_record);
        add_button.setFocusable(false);

        load_button.setBounds(650,350,100,25);
        load_button.addActionListener(this::load_table);
        load_button.setFocusable(false);

        search_button.setBounds(950,400,100,25);
        search_button.addActionListener(this::search_record);
        search_button.setFocusable(false);

        frame.add(transaction_header);

        frame.add(transaction_id_label);
        frame.add(quantity_label);
        frame.add(hospital_label);
        frame.add(search_by_label);
        frame.add(start_date_label);
        frame.add(end_date_label);

        frame.add(quantity_fields);
        frame.add(hospital_fields);
        frame.add(search_by_field);
        frame.add(search_type_box);
        frame.add(start_date_field);
        frame.add(end_date_field);

        frame.add(action_box);
        frame.add(item_box);

        frame.add(add_button);
        frame.add(load_button);
        frame.add(search_button);

        frame.add(scrollPane);

        frame.setVisible(true);

    }

    private void search_record(ActionEvent e) {
        model.setRowCount(0);
        String search_text = search_by_field.getText();
        String search_type = (String) search_type_box.getSelectedItem();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                
                String[] new_record = {
                    line_container[0],
                    line_container[1],
                    line_container[2],
                    line_container[3],
                    line_container[4],
                    line_container[5]
                };
                
                switch (search_type) {

                    case "ID" -> {
                        if (line_container[0].contains(search_text)) {
                            model.addRow(new_record);
                        }
                    }

                    case "Distribute/Received" -> {
                        if (line_container[1].contains(search_text)) {
                            model.addRow(new_record);
                        }
                    }

                    case "Item" -> {
                        if (line_container[3].contains(search_text)) {
                            model.addRow(new_record);
                        }
                    }
                    
                    case "Date" -> {
                        
                        LocalDate start_date = LocalDate.parse(start_date_field.getText());
                        LocalDate end_date = LocalDate.parse(end_date_field.getText());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        
                        String date_string = line_container[5];
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

    private void add_record(ActionEvent e) {

        String action = (String) action_box.getSelectedItem();
        String item = (String) item_box.getSelectedItem();
        boolean positive_quantity = true;
        int item_quantity = 0;

        if (Objects.equals(action, "Distribute")) {

            // check if enough resources or not
            try {
                BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");

                    if (Objects.equals(line_container[0], item)) {
                        item_quantity = Integer.parseInt(line_container[2]);
                        int distributed_quantity = Integer.parseInt(quantity_fields.getText());
                        int new_quantity = item_quantity - distributed_quantity;

                        if (new_quantity < 0) {
                            positive_quantity = false;
                        }
                    }
                }

            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }

            if (positive_quantity) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter("temp_file.txt"));
                    
                    BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));

                    String line;
                    
                    GregorianCalendar calendar = new GregorianCalendar();

                    int year = calendar.get(GregorianCalendar.YEAR);
                    int month = calendar.get(GregorianCalendar.MONTH) + 1;
                    int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                    String activity_date = year + "-" + month + "-" + day;

                    new_distribution();

                    while ((line = reader.readLine()) != null) {
                        String[] line_container = line.split("<>");

                        if (Objects.equals(line_container[0], item)) {
                            item_quantity = Integer.parseInt(line_container[2]);
                            int distributed_quantity = Integer.parseInt(quantity_fields.getText());
                            int new_quantity = item_quantity - distributed_quantity;

                            writer.write(
                                    line_container[0] + "<>" +
                                            line_container[1] + "<>" +
                                            new_quantity + "<>" +
                                            line_container[3]
                            );
                            writer.newLine();
                        } else {
                            writer.write(
                                    line_container[0] + "<>" +
                                            line_container[1] + "<>" +
                                            line_container[2] + "<>" +
                                            line_container[3]
                            );
                            writer.newLine();
                        }
                    }
                    
                    ac_writer.write(    
                        user_id + "<>" +
                        "add" + "<>" +
                        "Transactions" + "<>" +
                        activity_date
                    );
                    ac_writer.newLine();
                    ac_writer.close();

                    writer.close();

                    replace_file();
                } catch (IOException ioException) {
                    System.out.println("something went wrong " + ioException);
                }
            } else {
                JOptionPane.showMessageDialog(null, "insufficient resources");
            }
        } else {

            try {
                BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("temp_file.txt"));
                BufferedWriter ac_writer = new BufferedWriter(new FileWriter("activity_log.txt", true));
                String line;
                
                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                
                String activity_date = year + "-" + month + "-" + day;


                while ((line = reader.readLine()) != null) {
                    String[] line_container = line.split("<>");

                    if (Objects.equals(line_container[0], item)) {
                        item_quantity = Integer.parseInt(line_container[2]);
                        int add_quantity = Integer.parseInt(quantity_fields.getText());
                        int new_quantity = item_quantity + add_quantity;

                        writer.write(
                                line_container[0] + "<>" +
                                        line_container[1] + "<>" +
                                        new_quantity + "<>" +
                                        line_container[3]
                                );
                        writer.newLine();
                    } else {
                        writer.write(
                                line_container[0] + "<>" +
                                        line_container[1] + "<>" +
                                        line_container[2] + "<>" +
                                        line_container[3]
                                );
                        writer.newLine();
                    }
                }
                
                ac_writer.write(    
                    user_id + "<>" +
                    "add" + "<>" +
                    "Transactions" + "<>" +
                    activity_date
                );
                ac_writer.newLine();
                ac_writer.close();

                writer.close();

                new_received();

                replace_file();

            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }
        }
    }

    private void load_table(ActionEvent e) {

        model.setRowCount(0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                String[] record = {
                        line_container[0],
                        line_container[1],
                        line_container[2],
                        line_container[3],
                        line_container[4],
                        line_container[5]
                };
                model.addRow(record);
            }
            reader.close();
        } catch (IOException ioException) {
            System.out.println("something went wrong " + ioException);
        }
    }

    private void new_distribution() {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true));

            BufferedReader id_reader = new BufferedReader(new FileReader("ID.txt"));
            BufferedReader hp_reader = new BufferedReader(new FileReader("Hospitals.txt"));

            String id_line = id_reader.readLine();
            String[] id_line_container = id_line.split("<>");

            boolean hp_exist = false;
            String hp_line;
            String hospital_id = "";

            while ((hp_line = hp_reader.readLine()) != null) {
                String[] hp_line_container = hp_line.split("<>");
                if (Objects.equals(hospital_fields.getText(), hp_line_container[0])) {
                    hp_exist = true;
                    hospital_id = hp_line_container[0];
                }
            }

            if (hp_exist) {
                int new_id = Integer.parseInt(id_line_container[3]);
                new_id++;

                String transaction_id = "TS" + new_id;

                BufferedWriter id_writer = new BufferedWriter(new FileWriter("ID.txt"));

                id_writer.write(
                        id_line_container[0] + "<>" +
                                id_line_container[1] + "<>" +
                                id_line_container[2] + "<>" +
                                new_id
                );

                id_writer.close();

                GregorianCalendar calendar = new GregorianCalendar();

                int year = calendar.get(GregorianCalendar.YEAR);
                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

                String transaction_date = year + "-" + month + "-" + day;

                writer.write(
                        transaction_id + "<>" +
                                action_box.getSelectedItem() + "<>" +
                                hospital_id + "<>" +
                                item_box.getSelectedItem() + "<>" +
                                quantity_fields.getText() + "<>" +
                                transaction_date
                );
                writer.newLine();
                writer.close();

                String[] new_record = {
                        transaction_id,
                        (String) action_box.getSelectedItem(),
                        hospital_id,
                        (String) item_box.getSelectedItem(),
                        quantity_fields.getText(),
                        transaction_date
                };
                model.addRow(new_record);
            } else {
                JOptionPane.showMessageDialog(null, "Hospital id not found");
            }
        } catch (IOException ioException) {
            System.out.println("something went wrong " + ioException);
        }
    }

    private void new_received() {

        String item = (String) item_box.getSelectedItem();
        String supplier_id = "";

        GregorianCalendar calendar = new GregorianCalendar();

        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH) + 1;
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        String transaction_date = year + "-" + month + "-" + day;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true));
            BufferedReader id_reader = new BufferedReader(new FileReader("ID.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("ppe.txt"));

            String line;

            String id_line = id_reader.readLine();
            String[] id_line_container = id_line.split("<>");

            int new_id = Integer.parseInt(id_line_container[3]);
            new_id++;

            String transaction_id = "TS" + new_id;

            BufferedWriter id_writer = new BufferedWriter(new FileWriter("ID.txt"));

            id_writer.write(
                    id_line_container[0] + "<>" +
                            id_line_container[1] + "<>" +
                            id_line_container[2] + "<>" +
                            new_id
            );

            id_writer.close();

            while ((line = reader.readLine()) != null) {
                String[] line_container = line.split("<>");
                if (Objects.equals(item, line_container[0])) {
                    supplier_id = line_container[3];
                }
            }

            writer.write(
                    transaction_id + "<>" +
                            action_box.getSelectedItem() + "<>" +
                            supplier_id + "<>" +
                            item_box.getSelectedItem() + "<>" +
                            quantity_fields.getText() + "<>" +
                            transaction_date
            );
            writer.newLine();
            writer.close();

            String[] new_record = {
                    transaction_id,
                    (String) action_box.getSelectedItem(),
                    supplier_id,
                    (String) item_box.getSelectedItem(),
                    quantity_fields.getText(),
                    transaction_date
            };
            model.addRow(new_record);

        } catch (IOException ioException) {
            System.out.println("something went wrong " + ioException);
        }
    }

    private void return_back(ActionEvent e) {
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
