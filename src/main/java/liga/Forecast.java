package liga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Forecast{
    private static final int const_days = 7;
    private static final int const_str = 8;

    //функция для рассчета прогнозов на неделю
    public static double[] countForecastForWeek(double[] cost){
        double[] costWeek = new double[const_days];
        int counter = const_days;
        for (int i=0; i<const_days; i++){
            costWeek[i]=countForecast(cost);
            cost[counter-1] = costWeek[i];
            counter--;
        }
        return costWeek;
    }

    //функция для генерации массива из дат на const_days дней начиная с завтрашней даты
    public static LocalDate[] generateDatesForWeek(LocalDate today){
        LocalDate[] weekDates = new LocalDate[const_days];
        for(int i = 0; i<const_days; i++){
            if(i==0){weekDates[i] = today.plusDays(1);}
            else {weekDates[i] = weekDates[i-1].plusDays(1);}
        }
        return weekDates;
    }

    //функция для получения массива Дней недели исходя из массива дат
    public static DayOfWeek[] generateDaysForWeek(LocalDate[] week){
        DayOfWeek[] weekDays = new DayOfWeek[const_days];
        for(int i = 0; i<const_days; i++){
            weekDays[i] = week[i].getDayOfWeek();
        }
        return weekDays;
    }

    //функция рассчета прогноза на 1 день
    public static double countForecast(double[] cost){
        double sum = 0;
        for (double x: cost) {
            sum += x;
        }
        double forecast = sum/cost.length;
        return forecast;
    }

    //функция для преобразования массива курсов из String в double
    public static double[] setDoubleCosts(String[] cost){
    double[] costdouble = new double[cost.length];
        for (int i = 0; i < cost.length; i++) {
        costdouble[i] = Double.parseDouble(cost[i].substring(2, 9).replace(",","."));
        }
        return costdouble;
    }

    //функция для считывания из файла последних const_days курсов, возвращает массив из курсов в формате double
    public static double[] csvRead (String currency){
        String src = "";
        if(currency.equals("EUR")){
            src = "src\\RC_F01_06_2002_T17_06_2022_EUR.csv";
        } else if (currency.equals("TRY")) {
            src = "src\\RC_F01_06_2002_T17_06_2022_TRY.csv";
        } else {
            src = "src\\RC_F01_06_2002_T17_06_2022_USD.csv";
        }
        BufferedReader reader = null;
        double[] costNew = new double[const_days];
        try {
            String line = "";
            String [] cost = new String [const_days];
            reader = new BufferedReader(new FileReader(src));

            for (int i = 0; i < const_str; i++) {
                line = reader.readLine();
                if(i > 0) {
                    String[] row = line.split(";");
                    cost[i-1] = row[2];
                }
            }
            costNew = setDoubleCosts(cost);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return costNew;
    }

    public static void main( String[] args )
    {
        System.out.print("Enter request: ");
        String input = new Scanner(System.in).nextLine();

        String currency = input.substring(5, 8);//получаем нужную валюту
        String period = input.substring(9);//получаем период на который нужен прогноз


        if(currency.equals("USD") || currency.equals("EUR") || currency.equals("TRY")) {
            double[] cost = csvRead(currency); // получаем массив последних const_days значений курса из файла в формате дабл

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");//формат для итоговой даты
            LocalDate today = LocalDate.now();//текущая дата

            // словарь для дней недели
            Map<String,String> engRus = new HashMap<String,String>();
            //добавление элементов в мапу
            engRus.put("MONDAY", "Пн");
            engRus.put("TUESDAY", "Вт");
            engRus.put("WEDNESDAY", "Ср");
            engRus.put("THURSDAY", "Чт");
            engRus.put("FRIDAY", "Пт");
            engRus.put("SATURDAY", "Сб");
            engRus.put("SUNDAY", "Вс");

            if(period.equals("tomorrow")) {
                LocalDate tomorrow = today.plusDays(1); //считает завтра
                DayOfWeek day = tomorrow.getDayOfWeek();//получаем день недели
                double forecast = countForecast(cost);//получаем прогноз валюты

                System.out.print( engRus.get(day.toString())+ " " + tomorrow.format(formatter) +" - "+ String.format("%.2f",forecast));
            }
            else if(period.equals("week")){
                double[] forecast = countForecastForWeek(cost);
                LocalDate[] weekDates = generateDatesForWeek(today);
                DayOfWeek[] weekDays = generateDaysForWeek(weekDates);
                for (int i=0; i<const_days; i++){
                    System.out.println( engRus.get(weekDays[i].toString())+ " " + weekDates[i].format(formatter) +" - "+ String.format("%.2f",forecast[i]));
                }
            }
            else System.out.print("Ошибка ввода периода");
            }
        else {System.out.print("Ошибка ввода валюты");}

    }
}
