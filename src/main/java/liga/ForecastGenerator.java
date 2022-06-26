package liga;

import static liga.Forecast.CONST_DAYS;

public class ForecastGenerator {
    //функция рассчета прогноза на 1 день
    public static double countForecast(double[] cost) {
        double sum = 0;
        for (double x : cost) {
            sum += x;
        }
        double forecast = sum / cost.length;
        return forecast;
    }
    //функция для рассчета прогнозов на неделю
    public static double[] countForecastForWeek(double[] cost) {
        double[] costWeek = new double[CONST_DAYS];
        int counter = CONST_DAYS;
        for (int i = 0; i < CONST_DAYS; i++) {
            costWeek[i] = countForecast(cost);
            cost[counter - 1] = costWeek[i];
            counter--;
        }
        return costWeek;
    }
}
