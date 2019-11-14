package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GuiForm extends JFrame {
    private JButton buttonAddCountry;
    private JButton buttonDeleteCountry;
    static Connect conn;

    static {
        try {
            conn = new Connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel panel;
    private JMenuBar menuBar;
    private JTextField textFieldCountry;
    private JTextField textFieldRegion;
    private JTextField textFieldDistrict;
    private JTextField textFieldPlace;
    private DefaultListModel listModelCountry = new DefaultListModel();
    private DefaultListModel listModelRegion = new DefaultListModel();
    private DefaultListModel listModelDistrict = new DefaultListModel();
    private DefaultListModel listModelPlace = new DefaultListModel();
    private JList listCountry;
    private JList listRegion;
    private JList listDistrict;
    private JList listPlace;
    private JComboBox comboBox;
    private JButton buttonDeleteRegion;
    private JButton buttonAddRegion;
    private JButton buttonAddDistrict;
    private JButton buttonDeleteDistrict;
    private JButton buttonAddPlace;
    private JButton buttonDeletePlace;
    private JLabel labelCountry;
    private JLabel labelRegion;
    private JLabel labelDistrict;
    private JLabel labelPlace;
    private JMenu menuInfo;
    private JMenuItem itemHelp;
    private JMenuItem itemIndex;
    private JMenu menuHelp;

    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    public static String querySelect = "SELECT country FROM test.directoryofcity;";
    private JScrollPane scrollPane;
    private PreparedStatement statementInsertDeleteInDb = null;

    public GuiForm() {
        super("Справочник городов");

        Font font = new Font("Lucida Console", Font.BOLD, 13);
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        labelCountry.setFont(font.deriveFont(attributes));
        labelRegion.setFont(font.deriveFont(attributes));
        labelDistrict.setFont(font.deriveFont(attributes));
        labelPlace.setFont(font.deriveFont(attributes));

        listCountry.setModel(listModelCountry);
        listRegion.setModel(listModelRegion);
        listDistrict.setModel(listModelDistrict);
        listPlace.setModel(listModelPlace);

        add(panel);
        setPreferredSize(new Dimension(870, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            statement = conn.getConnection()
                    .prepareStatement("SELECT country FROM test.directoryofcity GROUP BY country;");
            resultSet = statement.executeQuery(); //run your query
            while (resultSet.next()) {
                listModelCountry.addElement(resultSet.getString("Country"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        buttonAddCountry.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
        buttonAddCountry.addActionListener(e -> {
            listModelCountry.addElement(textFieldCountry.getText());
            int index = listModelCountry.size() - 1;
            listCountry.setSelectedIndex(index);
            listCountry.ensureIndexIsVisible(index);
            try {
                String element = textFieldCountry.getText();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("insert into test.directoryofcity (country, region, district, place) value (?,'','','')");
                statementInsertDeleteInDb.setString(1, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            textFieldCountry.setText("");
        });

        buttonAddRegion.addActionListener(e -> {
            listModelRegion.addElement(textFieldRegion.getText());
            int index = listModelRegion.size() - 1;
            listRegion.setSelectedIndex(index);
            listRegion.ensureIndexIsVisible(index);
            try {
                String element = textFieldRegion.getText();
                String itemCountry = listCountry.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("insert into test.directoryofcity (country, region, district, place) value (?,?,'','')");
                statementInsertDeleteInDb.setString(1, itemCountry);
                statementInsertDeleteInDb.setString(2, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            textFieldRegion.setText("");
        });

        buttonAddDistrict.addActionListener(e -> {
            listModelDistrict.addElement(textFieldDistrict.getText());
            int index = listModelDistrict.size() - 1;
            listDistrict.setSelectedIndex(index);
            listDistrict.ensureIndexIsVisible(index);
            try {
                String element = textFieldDistrict.getText();
                String itemCountry = listCountry.getSelectedValue().toString();
                String itemRegion = listRegion.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("insert into test.directoryofcity (country, region, district, place) value (?,?,?,'')");
                statementInsertDeleteInDb.setString(1, itemCountry);
                statementInsertDeleteInDb.setString(2, itemRegion);
                statementInsertDeleteInDb.setString(3, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            textFieldDistrict.setText("");
        });

        buttonAddPlace.addActionListener(e -> {
            String itemcomboBox = comboBox.getSelectedItem().toString();
            listModelPlace.addElement(itemcomboBox + " " + textFieldPlace.getText());
            int index = listModelPlace.size() - 1;
            listPlace.setSelectedIndex(index);
            listPlace.ensureIndexIsVisible(index);
            try {
                String element = textFieldPlace.getText();
                String itemCountry = listCountry.getSelectedValue().toString();
                String itemRegion = listRegion.getSelectedValue().toString();
                String itemDistrict = listDistrict.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("insert into test.directoryofcity (country, region, district, place) value (?,?,?,?)");
                statementInsertDeleteInDb.setString(1, itemCountry);
                statementInsertDeleteInDb.setString(2, itemRegion);
                statementInsertDeleteInDb.setString(3, itemDistrict);
                statementInsertDeleteInDb.setString(4, itemcomboBox + " " + element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            textFieldPlace.setText("");
        });


        buttonDeleteCountry.addActionListener(e -> {
            try {
                String element = listCountry.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("delete from test.directoryofcity where country = ? limit 1000");
                statementInsertDeleteInDb.setString(1, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listModelCountry.remove(listCountry.getSelectedIndex());
        });

        buttonDeleteRegion.addActionListener(e -> {
            try {
                String element = listRegion.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("delete from test.directoryofcity where region = ? limit 1000");
                statementInsertDeleteInDb.setString(1, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listModelRegion.remove(listRegion.getSelectedIndex());
        });

        buttonDeleteDistrict.addActionListener(e -> {
            try {
                String element = listDistrict.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("delete from test.directoryofcity where district = ? limit 1000");
                statementInsertDeleteInDb.setString(1, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listModelDistrict.remove(listDistrict.getSelectedIndex());
        });

        buttonDeletePlace.addActionListener(e -> {
            try {
                String element = listPlace.getSelectedValue().toString();
                statementInsertDeleteInDb = Main.conn.getConnection()
                        .prepareStatement("delete from test.directoryofcity where place = ? limit 1000");
                statementInsertDeleteInDb.setString(1, element);
                statementInsertDeleteInDb.executeUpdate();
                statementInsertDeleteInDb.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listModelPlace.remove(listPlace.getSelectedIndex());
        });

        listCountry.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //почему не раб лямбда
                if (e.getClickCount() == 1) {
                    textFieldCountry.setText(listCountry.getSelectedValue().toString());
                    listModelRegion.removeAllElements();
                    try {
                        String itemCountry = listCountry.getSelectedValue().toString();
                        statement = Main.conn.getConnection()
                                .prepareStatement("select region from test.directoryofcity where country = ? group by region ASC");
                        statement.setString(1, itemCountry);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            listModelRegion.addElement(resultSet.getString("Region"));
                        }
                        resultSet.close();
                        statement.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        listRegion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    textFieldRegion.setText(listRegion.getSelectedValue().toString());
                    listModelDistrict.removeAllElements();
                    try {
                        String itemCountry = listCountry.getSelectedValue().toString();
                        String itemRegion = listRegion.getSelectedValue().toString();
                        PreparedStatement statement = Main.conn.getConnection()
                                .prepareStatement("select district from test.directoryofcity where country = ? and region = ? group by district ASC");
                        statement.setString(1, itemCountry);
                        statement.setString(2, itemRegion);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            listModelDistrict.addElement(resultSet.getString("District"));
                        }
                        resultSet.close();
                        statement.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        listDistrict.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    textFieldDistrict.setText(listDistrict.getSelectedValue().toString());
                    listModelPlace.removeAllElements();
                    try {
                        String itemCountry = listCountry.getSelectedValue().toString();
                        String itemRegion = listRegion.getSelectedValue().toString();
                        String itemDistrict = listDistrict.getSelectedValue().toString();
                        PreparedStatement statement = Main.conn.getConnection()
                                .prepareStatement("select place from test.directoryofcity where country = ? and region = ? and district = ? GROUP BY place ASC");
                        statement.setString(1, itemCountry);
                        statement.setString(2, itemRegion);
                        statement.setString(3, itemDistrict);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            listModelPlace.addElement(resultSet.getString("Place"));
                        }
                        resultSet.close();
                        statement.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        listPlace.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    textFieldPlace.setText(listPlace.getSelectedValue().toString());
                }
            }
        });

        itemHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JOptionPane.showMessageDialog(itemHelp, "Список населенных пунктов стран.");
            }
        });

        ///добавить изменение комбобокса /// поиск элемента в комбобоксе /// загрузить файл с городами, изменить, сохранить
       //новое окно
        itemIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Почтовые индексы городов");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setPreferredSize(new Dimension(400, 300));
                frame.setLayout(new GridLayout(4, 1, 5, 5));

                frame.add(new JLabel("Выберите страну:"));
                JComboBox comboBoxFrameCountry = new JComboBox();
                try {
                    PreparedStatement statement = Main.conn.getConnection()
                            .prepareStatement("select country from test.directoryofcity group by country ASC");
                    // statement.setString(1, itemCountry);
                    ResultSet resultSet = statement.executeQuery();
                    System.out.println(statement);
                    while (resultSet.next()) {
                        comboBoxFrameCountry.addItem(resultSet.getString("Country"));
                    }
                    resultSet.close();
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.add(comboBoxFrameCountry);

                frame.add(new JLabel("Выберите область:"));
                JComboBox comboBoxFrameRegion = new JComboBox();
                frame.add(comboBoxFrameRegion);
                frame.add(new JLabel("Выберите город:"));
                JComboBox comboBoxFramePlace = new JComboBox();
                frame.add(comboBoxFramePlace);

                JButton buttonIndex = new JButton("Показать индекс:");
                frame.add(buttonIndex);
                JTextField textFieldIndex = new JTextField();
                frame.add(textFieldIndex);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                comboBoxFrameCountry.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent eventCountry) {
                        if (eventCountry.getStateChange() == ItemEvent.SELECTED) {
                            Object item = eventCountry.getItem();
                            System.out.println("item = " + item);
                            try {
                                PreparedStatement statement = Main.conn.getConnection()
                                        .prepareStatement("select region from test.directoryofcity  where country = ? group by region ASC");
                                statement.setObject(1, item);
                                ResultSet resultSet = statement.executeQuery();
                                System.out.println(statement);
                                comboBoxFrameRegion.removeAllItems();
                                while (resultSet.next()) {
                                    comboBoxFrameRegion.addItem(resultSet.getString("Region"));
                                }
                                resultSet.close();
                                statement.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                comboBoxFrameRegion.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent eventCountry) {
                        if (eventCountry.getStateChange() == ItemEvent.SELECTED) {
                            Object item = eventCountry.getItem();
                            System.out.println("item = " + item);
                            try {
                                PreparedStatement statement = Main.conn.getConnection()
                                        .prepareStatement("select place from test.directoryofcity where region = ?");
                                statement.setObject(1, item);
                                ResultSet resultSet = statement.executeQuery();
                                System.out.println(resultSet);
                                comboBoxFramePlace.removeAllItems();
                                while (resultSet.next()) {
                                    comboBoxFramePlace.addItem(resultSet.getString("Place"));
                                }
                                resultSet.close();
                                statement.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

                buttonIndex.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String itemCountry = (String) comboBoxFrameCountry.getSelectedItem();
                        System.out.println("itemCountry = " + itemCountry);
                        String itemRegion = (String) comboBoxFrameRegion.getSelectedItem();
                        System.out.println("itemRegion = " + itemRegion);
                        String itemPlace = (String) comboBoxFramePlace.getSelectedItem();
                        System.out.println("itemPlace = " + itemPlace);
                        try {
                            PreparedStatement statement = Main.conn.getConnection()
                                    .prepareStatement("select indexCity from test.directoryofcity where country = ? and region = ? and place = ?");
                            statement.setString(1, itemCountry);
                            statement.setString(2, itemRegion);
                            statement.setString(3, itemPlace);
                            ResultSet resultSet = statement.executeQuery();
                            System.out.println(statement);
                            while (resultSet.next()) {
                                textFieldIndex.setText(resultSet.getString("indexCity"));
                            }
                            System.out.println(textFieldIndex);
                            resultSet.close();
                            statement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
