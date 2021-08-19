import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.awt.Frame.MAXIMIZED_BOTH;

abstract class Blank {
    static Color foregraund = new Color(10, 10, 10);
    static Color backraund = new Color(233, 255, 233);
    static Color colorPanel = new Color(100, 100, 120);
    static Color colorActivTab = new Color(200, 200, 200);
    static JTextArea textAreaIncoming = new JTextArea(20, 50);
    static JTextArea textAreaActions = new JTextArea(10, 30);
    static JTextField nameNewTabbe = new JTextField(100);
    static JButton buttonCreateProject = new JButton("Добавить вкладку");
    static JButton buttonDeleteProject = new JButton("Удалить вкладку");
    Path pathProject = Paths.get("Project");

}



    public class Diary extends Blank {
        Actions actions = new Actions();
        Incoming incoming = new Incoming();
        UseProject project = new UseProject();
        JTabbedPane menu = new JTabbedPane(JTabbedPane.BOTTOM);
        JFrame frame = new JFrame();

        Diary() {
            actions.readData();
            incoming.readData();
            CalendarUser calendarUser2021 = new CalendarUser(2021);
            if (calendarUser2021.exsistData())
                calendarUser2021.read();
            else calendarUser2021.create();
            calendarUser2021.build();
            calendarUser2021.focusForToday();
            CalendarUser calendarUser2022 = new CalendarUser(2022);
            if (calendarUser2022.exsistData())
                calendarUser2022.read();
            else calendarUser2022.create();
            calendarUser2022.build();
            calendarUser2022.focusForToday();


            frame.setSize(1200, 720);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//throws IOException, ClassNotFoundException
            menu.addTab("Первоочередные действия", actions.getJPanel());
            menu.addTab("Входящие", incoming.getJPanel());
            menu.addTab("Календарь 2021", calendarUser2021.getCalendarTablePane());
            menu.addTab("Календарь 2022", calendarUser2022.getCalendarTablePane());
            menu.addTab("Проекты", project.getJPanel());
            frame.add(menu);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    project.saveProject();
                    incoming.writeData();
                    actions.writeData();
                    //writeData(textAreaActions,pathActions);
                    //writeData(textAreaIncoming,pathIncoming);
                    calendarUser2021.write();
                    calendarUser2022.write();
                    System.exit(0);
                }
            });
            frame.setVisible(true);
            frame.setExtendedState(MAXIMIZED_BOTH);
        }


        public static void main(String[] args) {
            Diary diary = new Diary();

        }
    }

