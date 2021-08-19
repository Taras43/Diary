import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class Incoming extends Blank implements ActionListener {
    JPanel panelIncoming = new JPanel();
    JPanel panel = new JPanel();
    public Incoming() {

        //textArea.setAlignmentX(RIGHT_ALIGNMENT);

        //JScrollPane scrollPaneIncoming = new JScrollPane(panelIncoming);
        panelIncoming.setBackground(backraund);
        panelIncoming.setLayout(null);
        panelIncoming.add(textAreaIncoming);
        panelIncoming.setBorder(BorderFactory.createLoweredBevelBorder());
        panelIncoming.setBounds(50,30,1260,630);
        textAreaIncoming.setBackground(backraund);
        textAreaIncoming.setForeground(foregraund);
        textAreaIncoming.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaIncoming.setBounds(300,30,500,500);
        panel.setLayout(null);
        panel.setBackground(colorPanel);
        panel.add(panelIncoming);
       // String text = readData(pathIncoming);
        //textAreaIncoming.setText(text);
        JPopupMenu popupMenuIncoming = new JPopupMenu();
        JMenuItem createProject = new JMenuItem("Создать проект");
        JMenuItem sendToActions = new JMenuItem("В первоочередные ");
        //createProject.setActionCommand("Создать проект");
        createProject.addActionListener(this);
        sendToActions.addActionListener(this);
        popupMenuIncoming.add(createProject);
        popupMenuIncoming.add(sendToActions);
        textAreaIncoming.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    popupMenuIncoming.show(me.getComponent(), me.getX(), me.getY());
                }
            }

            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger())
                    popupMenuIncoming.show(me.getComponent(), me.getX(), me.getY());
                System.out.println("Кнопка отпущенна");
            }
        });
    }
    void readData(){
        String readData = "";
        if(Files.exists(Paths.get("ActionsAndIncoming\\Incoming.txt"))){
            InputStream in = null;
            try {
                in = new FileInputStream("ActionsAndIncoming\\Incoming.txt");
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
        }
        textAreaIncoming.setText(readData);
    }
    void writeData(){
        try {
            OutputStream out = new FileOutputStream("ActionsAndIncoming\\Incoming.txt");
            Writer file = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            file.write(textAreaIncoming.getText());
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
        System.out.println("Активная команда :"+e.getActionCommand());
        if(e.getActionCommand().equals("Создать проект")){
            String selectedText = textAreaIncoming.getSelectedText();
            nameNewTabbe.setText(selectedText);
            buttonCreateProject.doClick();
            // textArea.append((textArea.getSelectedText()) + "\n");
            textAreaIncoming.replaceRange("",textAreaIncoming.getSelectionStart(),textAreaIncoming.getSelectionEnd());
        }
        else if(e.getActionCommand().equals("В первоочередные ")){
            /*String selectedText = textAreaIncoming.getSelectedText();
            String text = textAreaActions.getText();
            text=(text+"\n"+selectedText+"\n");
            textAreaActions.setText(text);
            //textAreaActions.(selectedText+"\n");*/
            textAreaActions.append((textAreaIncoming.getSelectedText()) + "\n");
            textAreaIncoming.replaceRange("",textAreaIncoming.getSelectionStart(),textAreaIncoming.getSelectionEnd());
            //textAreaActions.setToolTipText();
        }
    }
}
