package com.company;

import javax.swing.*;
import java.sql.SQLException;

public class Main extends GuiForm{

    public static void main(String[] args) throws SQLException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        GuiForm gui = new GuiForm();
        gui.setVisible(true);
    }

}
