package liga;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import static liga.ForecastDates.generateDatesForWeek;
import static liga.ForecastDates.generateDaysForWeek;
import static liga.ForecastGenerator.countForecast;
import static liga.ForecastGenerator.countForecastForWeek;
import static liga.csvReader.csvRead;


public class Forecast {
    public static final int CONST_DAYS = 7;

    public static void main(String[] args) {
        while (true) {
            System.out.println("To Quit Forecaster enter Q");
            System.out.print("Enter request: ");
            String input = new Scanner(System.in).nextLine();
            if (input.equals("Q")) {
                break;
            }//завершаем, если с клавиатуры введена Q

            String currency = input.substring(5, 8);//получаем нужную валюту
            String period = input.substring(9);//получаем период на который нужен прогноз

            if (currency.equals("USD") || currency.equals("EUR") || currency.equals("TRY")) {
                double[] cost = csvRead(currency); // получаем массив последних CONST_DAYS значений курса из файла в формате дабл

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");//формат для итоговой даты
                LocalDate today = LocalDate.now();//текущая дата

                if (period.equals("tomorrow")) {
                    LocalDate tomorrow = today.plusDays(1); //считает завтра
                    String day = tomorrow.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("Ru"));//получаем день недели
                    double forecast = countForecast(cost);//получаем прогноз валюты
                    System.out.println(day + " " + tomorrow.format(formatter) + " - " + String.format("%.2f", forecast));
                } else if (period.equals("week")) {
                    double[] forecast = countForecastForWeek(cost);
                    LocalDate[] weekDates = generateDatesForWeek(today);
                    String[] weekDays = generateDaysForWeek(weekDates);
                    for (int i = 0; i < CONST_DAYS; i++) {
                        System.out.println(weekDays[i] + " " + weekDates[i].format(formatter) + " - " + String.format("%.2f", forecast[i]));
                    }
                } else System.out.print("Ошибка ввода периода");
            } else {
                System.out.print("Ошибка ввода валюты");
            }
        }
    }
}
