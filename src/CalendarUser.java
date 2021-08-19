import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

class CalendarUser extends Blank implements Serializable {
    private JTable[] tablesMonth = new JTable[12];
    private JTabbedPane calendarTablePane = new JTabbedPane();
    private String[] montht = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private String[] headings = {"Число", "День недели", "Событие", "Событие", "Событие",
            "Событие", "Событие", "Событие",};
    private int year;


    public CalendarUser(int year){
        this.year=year;
    }
    public boolean exsistData() {
        boolean answer = false;
        Path path = Paths.get("Calendar"+year);
        if(Files.exists(path))
            answer=true;
        return answer;
    }
    void read() {

        for (int y = 0; y < 12; y++) {
            String dataRead = "";
            String file = ("Calendar" + year + "\\" + montht[y] + year + ".txt");
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in,"utf-8");
                int g;
                while ((g = reader.read()) != -1) {
                    dataRead = dataRead + ((char) g);

                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String regex = "\\$";
            String[] rows = dataRead.split(regex);
            for (int i = 0; i < rows.length; i++) {
                if (rows[i].equals("null"))
                    rows[i] = null;
            }
            int quntityRows = (rows.length) / 8;
            String[][] monthData = new String[quntityRows][8];
            int numberCell = 0;
            //for(int z =0; z<rows.length; z++){
            for (int i = 0; i < quntityRows; i++) {
                for (int r = 0; r < 8; r++) {
                    monthData[i][r] = rows[numberCell];
                    numberCell++;
                }
            }
            tablesMonth[y]= new JTable(monthData, headings);
        }
    }
    void create() {
            for (int y = 0; y < 12; y++) {
                //UseCalendar.createMonth(y+1,year));
                LocalDate d = LocalDate.of(year, y + 1, 1);
                int numberOfDaysInAMonth = d.lengthOfMonth();
                String[][] monthData = new String[numberOfDaysInAMonth][8];
                for (int day = 1; day <= numberOfDaysInAMonth; day++) {
                    LocalDate dat = LocalDate.of(year, y + 1, day);//"<html>Моя<br>
                    monthData[day - 1][0] = String.valueOf(day);
                    //String dayWeek = String.format("%tA", dat.plusDays(6));
                    String dayWeek = String.format("%tA", dat);
                    if (dayWeek.equals("суббота") | dayWeek.equals("воскресенье"))
                        dayWeek = ("<html><b><font color='red'>" + dayWeek);
                    monthData[day - 1][1] = dayWeek;
                    // String r = new String("<html><font color=RED werty");
                    //System.out.println(r);
                }
                tablesMonth[y]= new JTable(monthData, headings);
            }
        }
        void build() {

            for (int month = 0; month < 12; month++) {
                //JTable calendar = new JTable(monthf, headings);
                // calendar.setFillsViewportHeight(true);
                //tablesMonth[month] = calendar;
                //JScrollPane er = new JScrollPane(calendar);
               // calendarTablePane.addTab(montht[month], er);
                tablesMonth[month].setFont(new Font("Tahoma", Font.PLAIN, 13));
                tablesMonth[month].getColumnModel().getColumn(0).setMaxWidth(45);
                tablesMonth[month].getColumnModel().getColumn(1).setPreferredWidth(120);
                tablesMonth[month].getColumnModel().getColumn(1).setMaxWidth(120);
                tablesMonth[month].setRowHeight(19);
                tablesMonth[month].setFillsViewportHeight(true);
                tablesMonth[month] = tablesMonth[month];
                JScrollPane er = new JScrollPane(tablesMonth[month]);
                calendarTablePane.addTab(montht[month], er);
            }
        }
        public void focusForToday(){
            LocalDate localDateToday = LocalDate.now();
            int todaysMonth = localDateToday.getMonthValue() - 1;
            calendarTablePane.setSelectedIndex(todaysMonth);
            int todaysDate = localDateToday.getDayOfMonth() - 1;
            tablesMonth[localDateToday.getMonthValue() - 1].setRowSelectionInterval(todaysDate, todaysDate);
        }
        void write() {
            for (int i = 0; i < 12; i++) {
                try {
                    String way = "Calendar"+year+"\\"+montht[i] + year + ".txt";
                    String wayFolder = "Calendar"+year;
                    System.out.println("Путь :" + way);
                    File file = new File(way);
                    File fileFolder = new File(wayFolder);

                    if (!fileFolder.exists()){
                        fileFolder.mkdir();
                    }
                    else if (!file.exists()) {
                        file.createNewFile();
                    }
                    OutputStream out = new FileOutputStream(way);
                    Writer filew = new OutputStreamWriter(out,"utf-8");
                    JTable object = tablesMonth[i];
                    String data="";
                    String[][] calendarData = new String[object.getRowCount()][8];
                    for (int row = 0; row < object.getRowCount(); row++) {
                        for (int column = 0; column < 8; column++) {
                            String cell = (String) object.getValueAt(row, column);
                            if(cell==null)
                                cell="null";
                            if(!data.isEmpty())
                                data=data+"$";
                            data = data+cell;
                            //calendarData[row][column] = (String) object.getValueAt(row, column);
                        }
                        // data=(data+"#");
                    }

                    filew.write(data);
                    filew.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




        }
    public JPanel getCalendarTablePane() {
        JPanel panelCalendar = new JPanel();
        calendarTablePane.setBounds(50,22,1260,640);

        panelCalendar.setLayout(null);
        panelCalendar.setBackground(colorPanel);
        panelCalendar.add(calendarTablePane);
        return panelCalendar;
    }

    }

