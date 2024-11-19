import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jansu
 */
public class Inventory_system {
    public static void main(String[] args) throws IOException {

        // initial inventory creation
        File staff_record = new File("Staff_record.txt");
        
        File ppe_items = new File("ppe.txt");
        

        // create user_file if it doesn't exist and add a default admin
        if (!staff_record.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Staff_record.txt"));

                writer.write("User1<>DefaultAdmin<>password123<>admin");
                writer.close();

            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }
        }
        
        if (!ppe_items.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
                
                writer.write("HC<>Head Cover<>100<>SP1");
                writer.newLine();
                writer.write("FS<>Face shield<>100<>SP2");
                writer.newLine();
                writer.write("MS<>Mask<>100<>SP2");
                writer.newLine();
                writer.write("GL<>Gloves<>100<>SP2");
                writer.newLine();
                writer.write("GW<>Gown<>100<>SP3");
                writer.newLine();
                writer.write("SC<>Shoe cover<>100<>SP2");
                writer.close();
                               
            } catch (IOException ioException) {
                System.out.println("something went wrong " + ioException);
            }
        }

        new Login_page();
    }
}