package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    
   private File tempFile = new File(System.getProperty("user.dir") + "\\file\\Temp.txt");
   private File file = new File(System.getProperty("user.dir") + "\\file\\Create.txt");
    JButton addBtn;
    JButton createBtn;
    JButton deleteBtn;
    JButton editBtn;
    JLabel menuTitle;
    JSeparator horizontalLine;
    JPanel menuPanel;
    JButton showBtn;


    public MainFrame() {

        menuPanel = new JPanel();
        createBtn = new JButton();
        editBtn = new JButton();
        deleteBtn = new JButton();
        showBtn = new JButton();
        addBtn = new JButton();
        menuTitle = new JLabel();
        horizontalLine = new JSeparator();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fuck Files");
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        menuPanel.setBackground(new Color(204, 204, 255));
        menuPanel.setToolTipText("suck files");
        menuPanel.setLayout(null);

        createBtn.setText("Create File");
        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });
        menuPanel.add(createBtn);
        createBtn.setBounds(40, 10, 320, 60);

        editBtn.setText("Edit data");
        editBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });
        menuPanel.add(editBtn);
        editBtn.setBounds(40, 250, 320, 60);

        deleteBtn.setText("Delete File");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        menuPanel.add(deleteBtn);
        deleteBtn.setBounds(40, 330, 320, 60);

        showBtn.setText("Show data");
        showBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                showBtnActionPerformed(evt);
            }
        });
        menuPanel.add(showBtn);
        showBtn.setBounds(40, 170, 320, 60);

        addBtn.setText("Add data");
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        menuPanel.add(addBtn);
        addBtn.setBounds(40, 90, 320, 60);

        getContentPane().add(menuPanel);
        menuPanel.setBounds(190, 60, 400, 410);

        menuTitle.setText("Menu");
        getContentPane().add(menuTitle);
        menuTitle.setBounds(370, 20, 99, 35);
        getContentPane().add(horizontalLine);
        horizontalLine.setBounds(330, 50, 120, 10);

    }

    private String getDataFromFile(String search) {
        String currentData = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {

                currentLine = currentLine.trim();
                String[] trimLine = currentLine.split("!");
                if (trimLine[0].equals(search.trim())) {
                    currentData = trimLine[0];
                }
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentData;
    }

    public void editLineFromFile(String Search, String lineToAdd) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            String currentData;
            String updatedAt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            while ((currentLine = reader.readLine()) != null) {
                currentLine = currentLine.trim();
                String[] trimLine = currentLine.split("!");
                if (trimLine[0].equals(Search.trim())) {
                    writer.write(lineToAdd + "!" + updatedAt + "\n");
                    continue;
                }
                writer.write(currentLine + "\n");
            }
            writer.close();
            reader.close();
            BufferedWriter BW = new BufferedWriter(new FileWriter(file));
            BufferedReader BR = new BufferedReader(new FileReader(tempFile));

            while ((currentData = BR.readLine()) != null) {
                BW.write(currentData + "!\n");
            }
            BW.close();
            BR.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }



    private void createBtnActionPerformed(ActionEvent evt) {
     if(!file.exists()){
        try {
                file.createNewFile();
                System.out.println("file created! " + file.getName());
                JOptionPane.showMessageDialog(this, "File Created!","Success" ,JOptionPane.PLAIN_MESSAGE);
        } catch (IOException ex) {
            System.out.println("system sucks while file creation! " + System.getProperty("user.dir") + ex);
        }
     }
     else{
     JOptionPane.showMessageDialog(this, "File Already Created!","Failed" ,JOptionPane.ERROR_MESSAGE);
     }
    }

    private void addBtnActionPerformed(ActionEvent evt) {
        try {
            BufferedWriter BW = new BufferedWriter(new FileWriter(file,true));
            String data = JOptionPane.showInputDialog(this, "Add data?");
            String createdAt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            BW.write(data.trim() + "!" + createdAt + "\n");
            BW.close();
            System.out.println("Check the file its already bieng write this:" + data + "!");
        } catch (IOException ex) {
            System.out.println("BW and FW sucks! " + ex);
        }
    }

    private void editBtnActionPerformed(ActionEvent evt) {
        String search = null, data = null;
        if (search == null) {
            search = JOptionPane.showInputDialog(this, "search your data");

            if (getDataFromFile(search) == null) {
                JOptionPane.showMessageDialog(this, "No data found", "Failed!", JOptionPane.ERROR_MESSAGE);
            }
            else{

            data = JOptionPane.showInputDialog(this, "edit your data", getDataFromFile(search));
            editLineFromFile(search, data);
            JOptionPane.showMessageDialog(this, "Data edited successfully!", "Success", JOptionPane.PLAIN_MESSAGE);
            }

        }


    }
    private void showBtnActionPerformed(ActionEvent evt) {
        try {
            BufferedReader BR = new BufferedReader(new FileReader(file));
            String currentLine, fullText = "";
            while((currentLine = BR.readLine()) != null){
                fullText += currentLine+"\n";
            }         
            JFrame showDataFrame = new JFrame();
            showDataFrame.setSize(500,400);
            showDataFrame.setLocationRelativeTo(this);
            showDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            showDataFrame.setVisible(true);
            showDataFrame.setLayout(null);
            JTextArea dataField = new JTextArea();
            dataField.append(fullText);
            dataField.setEditable(false);
            dataField.setBounds(50, 50, 400, 300);
            showDataFrame.add(dataField);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  
    private void deleteBtnActionPerformed(ActionEvent evt) {
        if(file.exists()){
            file.delete();
            JOptionPane.showMessageDialog(this, "File Successfully deleted", "Operation Success", JOptionPane.NO_OPTION);
        }else{
            JOptionPane.showMessageDialog(this, "You have not created any file yet!", "Operation Failed", JOptionPane.ERROR_MESSAGE);
        }
    }  
}
