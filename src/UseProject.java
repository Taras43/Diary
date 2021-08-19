import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

class UseProject extends Blank implements ActionListener {
    JButton buttonIndentUseProject = new JButton("Отступ ");
    JButton buttonPrintProject = new JButton("Печать");
    ArrayList<Page> pages = new ArrayList<Page>();
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel panel = new JPanel();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Добавить вкладку")) {
            String newNamePage = nameNewTabbe.getText();
            for (Page pag : pages) {
                if (newNamePage.equals(pag.namePage)) {
                    String message = "Названия вкладок не должны сопадать";
                    showMessageDialog(null, message);
                    nameNewTabbe.setText("");
                    return;
                }
            }
            if (newNamePage.isEmpty()) {
                String message = "Название вкладки не должно быть пустым";
                showMessageDialog(null, message);
                return;
            } else if (!newNamePage.matches("[0-9а-яА-ЯёЁa-zA-Z\\s]{1,}")) {
                String message = "Название вкладки должны содержать только буквы и цифры";
                showMessageDialog(null, message);
                nameNewTabbe.setText("");
                return;
            }
            Page page = new Page(nameNewTabbe.getText(), pathProject);
            pages.add(page);
            tabbedPane.addTab(nameNewTabbe.getText(), (pages.get(pages.size() - 1)).getPanelPage());
            nameNewTabbe.setText("");


        } else if (e.getActionCommand().equals("Удалить вкладку")) {
            String messageDelete = "Вы точно хотите удалить вкладку?";
            int answer = showConfirmDialog(null, messageDelete, "Удаление", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {
                int select = tabbedPane.getSelectedIndex();
                tabbedPane.removeTabAt(select);
                // String nameDeleteFile = pages.get(select).getNamePage();
                Path delete = pages.get(select).getPathPage();
                pages.remove(select);
                if (Files.exists(delete)) {
                    System.out.println(delete.toString());
                    try {
                        Files.delete(delete);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        else if (e.getActionCommand().equals("Печать проекта")){
            try {
                int indexSelectedTabb=tabbedPane.getSelectedIndex();
                JTextArea selectedTextArea = pages.get(indexSelectedTabb).getTextArea();
                selectedTextArea.print();
            } catch (PrinterException printerException) {
                printerException.printStackTrace();
            }

        }
        else if (e.getActionCommand().equals("Отступ ")) {
            int indexSelectedTabb=tabbedPane.getSelectedIndex();
            JTextArea selectedTextArea = pages.get(indexSelectedTabb).getTextArea();
            int wert = selectedTextArea.getCaretPosition();
            try {
                String tyty = selectedTextArea.getText(0, wert);
                int indexFin = tyty.lastIndexOf("\n", wert);

                System.out.println("Значение oiuo :" + indexFin);
                String gfdg =(" "+'\u2022'+"    ");
                selectedTextArea.insert(gfdg, indexFin+1);


            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            //Pattern p = Pattern.compile("(?<=[,][ ])\\\\w+[0-9]");
            //Matcher m = null;

        }

    }

    public UseProject() {
        UIManager.put("TabbedPane.selected", colorActivTab);
        tabbedPane.setBackground(backraund);
        buttonCreateProject.addActionListener(this);
        buttonDeleteProject.addActionListener(this);
        buttonPrintProject.addActionListener(this);
        buttonIndentUseProject.addActionListener(this);
        buttonPrintProject.setActionCommand("Печать проекта");
        panel.setLayout(null);
        panel.setBackground(colorPanel);
        tabbedPane.setBounds(50,22,1040,640);
        nameNewTabbe.setBounds(1150,80,150,40);
        buttonCreateProject.setBounds(1150,130,150,40);
        buttonDeleteProject.setBounds(1150,230,150,40);
        buttonIndentUseProject.setBounds(1150,330,150,40);
        buttonPrintProject.setBounds(1150,430,150,40);
        panel.add(tabbedPane);
        panel.add(buttonDeleteProject);
        panel.add(buttonIndentUseProject);
        panel.add(buttonCreateProject);
        panel.add(nameNewTabbe);
        panel.add(buttonPrintProject);
        if (!Files.exists(pathProject)) {
            try {
                Files.createDirectory(pathProject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (!Files.exists(pathProject)){
            try {
                Files.createDirectory(pathProject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathProject, "*.txt")) {
            for (Path obj : stream) {
                pages.add(new Page(obj));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(Arrays.toString(arrayTitle));
        for (Page page : pages) {
            //if(pages[i]!=null) {
            tabbedPane.addTab(page.namePage, page.scrollPanePage);
            tabbedPane.setPreferredSize(new Dimension(900, 400));
            //System.out.println(arrayTitle[i]);
            // }
            // else
            //  break;
        }
    }
    public JPanel getJPanel(){
      return panel;
    };



    public void saveProject() {
        for (Page pageSave : pages) {
            String contentArea = pageSave.getContentTextArea();
            //System.out.println("Должно сохраниться :" + contentArea);
            try {
                OutputStream out = new FileOutputStream(String.valueOf(pageSave.pathPage));
                Writer file = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                file.write(contentArea);
                file.close();
                // byte[] recByte = contentArea.getBytes("utf-8");
                //Files.write(pageSave.pathPage, recByte);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}