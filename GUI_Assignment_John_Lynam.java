package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GUI_Assignment_John_Lynam implements ActionListener {

    JFrame frame;
    JPanel backGroundPanel;
    JPanel CalculationPanel;
    JPanel TextPanel;

    JTabbedPane tabbedPane;


    JPanel numPanel;
    JPanel EntryPanel;
    JPanel buttonPanel;

    JLabel number1Label;
    JLabel number2Label;
    JTextField EnterNumber1;
    JTextField EnterNumber2;
    JButton addButton;
    JButton mulButton;
    JButton clearButton;
    JButton LoadFileButton;


    JTextArea textArea;
    JFileChooser fc;
    File file;
    String text;
    List<String> lines = new ArrayList<>( 1000 );

    public static void main(String[] args) {

        new GUI_Assignment_John_Lynam();


    }

    public GUI_Assignment_John_Lynam() {
        //super("GUI_Assignment_John_Lynam");

        //1. Create the frame.
        frame = new JFrame("GUI_Assignment_John_Lynam");
        backGroundPanel = new JPanel();

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);

        //3. Create components and put them in the frame.
        //...create Labels...
        //left side
        TextPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        numPanel = new JPanel();
        EntryPanel = new JPanel();
        buttonPanel = new JPanel();


        number1Label = new JLabel("Number 1");
        number2Label = new JLabel("Number 2");
        EnterNumber1 = new JTextField(15);
        EnterNumber2 = new JTextField(15);
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        mulButton = new JButton("Multiply");
        mulButton.addActionListener(this);
        clearButton = new JButton("Clear");
        LoadFileButton = new JButton("Load File");
        LoadFileButton.addActionListener(this);
        CalculationPanel = new JPanel();
        textArea = new JTextArea(20,20);

        CalculationPanel.setSize(300,300);
        TextPanel.setSize(300,300);

        JComponent panel1 = makeTextPanel("");
        tabbedPane.addTab("Calculate", null, panel1,
                "");

        GridLayout gridLeftPane = new GridLayout(3,0);
        panel1.setLayout(gridLeftPane);
        Dimension panelD = new Dimension(170,100);
        numPanel.setPreferredSize(panelD);
        panel1.add(numPanel);
        EntryPanel.setPreferredSize(panelD);
        panel1.add(EntryPanel);
        buttonPanel.setPreferredSize(panelD);
        panel1.add(buttonPanel);

        clearButton.addActionListener(this);


        numPanel.add(number1Label);
        numPanel.add(EnterNumber1);
        numPanel.add(number2Label);
        numPanel.add(EnterNumber2);
        buttonPanel.add(addButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(LoadFileButton);

        CalculationPanel.add(tabbedPane);
        TextPanel.add(new JScrollPane(textArea));


        GridLayout grid = new GridLayout(0,2);
        backGroundPanel.setLayout(grid);

        backGroundPanel.add(TextPanel);
        backGroundPanel.add(CalculationPanel);
        frame.getContentPane().add(backGroundPanel);

        frame.pack();
        frame.setVisible(true);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            EnterNumber1.setText("");
            EnterNumber2.setText("");
        }
        if (e.getSource() == addButton) {
            int ans = 0;

            ans += Integer.parseInt(EnterNumber1.getText());
            ans += Integer.parseInt(EnterNumber2.getText());
            textArea.setText(String.valueOf(ans));


        }
        if (e.getSource() == mulButton) {// This is not working 
            int ans = 0;

            ans *= Integer.parseInt(EnterNumber1.getText());
            ans *= Integer.parseInt(EnterNumber2.getText());
            textArea.setText(String.valueOf(ans));


        }

        if (e.getSource() == LoadFileButton) {// LoadFile Swing worker
            fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(textArea);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }
            textArea.setText(null);
            new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {

                    try (InputStream is = new FileInputStream(file)) {
                        byte[] content = new byte[2048];
                        int bytesRead = -1;
                        while ((bytesRead = is.read(content)) != -1) {
                            text = new String(content);
                            lines.add(text);
                            publish(text);
                            Thread.sleep(0, 1);
                        }
                    }
                    return null;
                }

                @Override
                protected void process(List<String> text) {
                    super.process(text);
                    textArea.append(String.valueOf(text));
                }
            }.execute();
        }
    }
}

