import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class Actions extends Blank implements ActionListener {
    JPanel panelActions = new JPanel();
    JPanel panel = new JPanel();
    public Actions() {

        JButton battonPrint = new JButton("Печать");
        JButton buttonIndent = new JButton("Отступ");

        //JScrollPane scrollPaneIncoming = new JScrollPane(panelIncoming);
        panelActions.setBackground(backraund);
        panelActions.setLayout(null);
        panelActions.add(textAreaActions);
        panelActions.setBorder(BorderFactory.createLoweredBevelBorder());
        panelActions.setBounds(50,30,1040,630);
        textAreaActions.setBackground(backraund);
        textAreaActions.setForeground(foregraund);
        textAreaActions.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaActions.setBounds(300,30,500,500);
        panel.setLayout(null);
        panel.setBackground(colorPanel);
        panel.add(panelActions);
        battonPrint.addActionListener(this);
        buttonIndent.addActionListener(this);
        battonPrint.setBounds(1150,60,150,40);
        buttonIndent.setBounds(1150,160,150,40);
        panel.add(battonPrint);
        panel.add(buttonIndent);
    }
    void readData(){
        String readData = "";
        if(Files.exists(Paths.get("ActionsAndIncoming\\Actions.txt"))){
            InputStream in = null;
            try {
                in = new FileInputStream("ActionsAndIncoming\\Actions.txt");
                Reader reader = new InputStreamReader(in,"utf-8");
                int g;
                while ((g = reader.read()) != -1) {
                    readData = readData + ((char) g);
                }

            } catch(IOException e){
                e.printStackTrace();
            }
        }
        else {
            try {
                Files.createDirectory(Paths.get("ActionsAndIncoming"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        textAreaActions.setText(readData);
    }
    void writeData(){
        try {
            OutputStream out = new FileOutputStream("ActionsAndIncoming\\Actions.txt");
            Writer file = new OutputStreamWriter(out,"utf-8");
            file.write(textAreaActions.getText());
            file.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public JPanel getJPanel(){
        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Печать")) {
            try {
                textAreaActions.print();
            } catch (PrinterException printerException) {
                printerException.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Отступ")) {
            int wert = textAreaActions.getCaretPosition();
            try {
                String tyty = textAreaActions.getText(0, wert);
                int indexFin = tyty.lastIndexOf("\n", wert);

                System.out.println("Значение oiuo :" + indexFin);
                String gfdg =(" "+'\u2022'+"    ");
                textAreaActions.insert(gfdg, indexFin+1);


            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            //Pattern p = Pattern.compile("(?<=[,][ ])\\\\w+[0-9]");
            //Matcher m = null;
        }
    }
}