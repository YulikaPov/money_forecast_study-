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
            }//���������, ���� � ���������� ������� Q

            String currency = input.substring(5, 8);//�������� ������ ������
            String period = input.substring(9);//�������� ������ �� ������� ����� �������

            if (currency.equals("USD") || currency.equals("EUR") || currency.equals("TRY")) {
                double[] cost = csvRead(currency); // �������� ������ ��������� CONST_DAYS �������� ����� �� ����� � ������� ����

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");//������ ��� �������� ����
                LocalDate today = LocalDate.now();//������� ����

                if (period.equals("tomorrow")) {
                    LocalDate tomorrow = today.plusDays(1); //������� ������
                    String day = tomorrow.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("Ru"));//�������� ���� ������
                    double forecast = countForecast(cost);//�������� ������� ������
                    System.out.println(day + " " + tomorrow.format(formatter) + " - " + String.format("%.2f", forecast));
                } else if (period.equals("week")) {
                    double[] forecast = countForecastForWeek(cost);
                    LocalDate[] weekDates = generateDatesForWeek(today);
                    String[] weekDays = generateDaysForWeek(weekDates);
                    for (int i = 0; i < CONST_DAYS; i++) {
                        System.out.println(weekDays[i] + " " + weekDates[i].format(formatter) + " - " + String.format("%.2f", forecast[i]));
                    }
                } else System.out.print("������ ����� �������");
            } else {
                System.out.print("������ ����� ������");
            }
        }
    }
}
