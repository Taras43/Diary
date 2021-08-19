import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Page extends Blank implements ActionListener {
    String namePage;
    Path pathPage;
    JTextArea textArea = new JTextArea(20, 60);
    JPanel panelPage = new JPanel();
    JScrollPane scrollPanePage = new JScrollPane(panelPage);
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Отправить в Первоочередные"))
            textAreaActions.append((textArea.getSelectedText()) + "\n");
        textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
    }


    Page(Path pathPage) {
        //;
        namePage = (pathPage.getFileName()).toString();
        this.pathPage = pathPage;
        namePage = namePage.replaceAll(".txt", "");
        //textAreaMontag.setAlignmentX(RIGHT_ALIGNMENT);
        textArea.setBackground(backraund);
        textArea.setForeground(foregraund);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        panelPage.setBackground(backraund);
        panelPage.add(textArea/*,BorderLayout.CENTER*/);
        //String textInArea="";
        List<String> arrMontag = null;
        try {
            arrMontag = Files.readAllLines(pathPage, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arrMontag.size(); i++) {
            textArea.append(arrMontag.get(i) + "\n");
        }
        //textInArea=textInArea.substring(0,(textInArea.length()-1));
        JPopupMenu popupMenuProject = new JPopupMenu();
        JMenuItem sendToPriority = new JMenuItem("Отправить в Первоочередные");
        sendToPriority.addActionListener(this);
        popupMenuProject.add(sendToPriority);
        textArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    popupMenuProject.show(me.getComponent(), me.getX(), me.getY());


                }
            }

            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger())
                    popupMenuProject.show(me.getComponent(), me.getX(), me.getY());
                System.out.println("Кнопка отпущенна");
            }
        });


    }

    Page(String name, Path pathDirectory) {
        namePage = name;
        pathPage = Paths.get(pathDirectory + "\\" + namePage + ".txt");
        //textAreaMontag.setAlignmentX(RIGHT_ALIGNMENT);
        textArea.setBackground(backraund);
        textArea.setForeground(foregraund);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        panelPage.setBackground(backraund);
        panelPage.add(textArea/*,BorderLayout.CENTER*/);
        JPopupMenu popupMenuProject = new JPopupMenu();
        JMenuItem sendToPriority = new JMenuItem("Отправить в Первоочередные");
        sendToPriority.addActionListener(this);
        popupMenuProject.add(sendToPriority);
        textArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    popupMenuProject.show(me.getComponent(), me.getX(), me.getY());
                    System.out.println("Кнопка нажата");
                    JTextArea area = (JTextArea) me.getComponent();
                    textAreaActions.append((area.getSelectedText()) + "\n");
                    textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
                }
            }

            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger())
                    popupMenuProject.show(me.getComponent(), me.getX(), me.getY());
                System.out.println("Кнопка отпущенна");

            }
        });

    }

    //public String getNamePage() {
    //  return namePage;
    //}

    public JScrollPane getPanelPage() {
        return scrollPanePage;
    }

    public Path getPathPage() {
        return pathPage;
    }

    public String getContentTextArea() {
        return textArea.getText();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
