package liga;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static liga.Forecast.CONST_DAYS;

public class ForecastDates {
    //функция для генерации массива из дат на CONST_DAYS дней начиная с завтрашней  даты
    public static LocalDate[] generateDatesForWeek(LocalDate today) {
        LocalDate[] weekDates = new LocalDate[CONST_DAYS];
        for (int i = 0; i < CONST_DAYS; i++) {
            if (i == 0) {
                weekDates[i] = today.plusDays(1);
            } else {
                weekDates[i] = weekDates[i - 1].plusDays(1);
            }
        }
        return weekDates;
    }

    //функция для получения массива Дней недели исходя из массива дат
    public static String[] generateDaysForWeek(LocalDate[] week) {
        String[] weekDays = new String[CONST_DAYS];
        for (int i = 0; i < CONST_DAYS; i++) {
            weekDays[i] = week[i].getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("Ru"));
        }
        return weekDays;
    }
}
