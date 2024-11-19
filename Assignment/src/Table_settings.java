/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author jansu
 */
import java.awt.event.ActionEvent;

public interface Table_settings {

    void add_record(ActionEvent e);
    void update_record(ActionEvent e);
    void delete_record(ActionEvent e);
    void load_table(ActionEvent e);
    void search_record(ActionEvent e);
    void return_back(ActionEvent e);
}