package liga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import static liga.ForecasterConstants.DAYS;

public class FileReader {
    /**
     *Считывает из файла последние DAYS курсов, возвращает эти курсы
     * @param currency - запрашиваемая валюта из массива CurrencyName
     * @return - массив из курсов из файла в формате double
     */
        public static double[] readLastSevenRates(String currency) {
            String src = "src\\main\\resourses\\RC_F01_06_2002_T17_06_2022_"+currency+".csv";
            BufferedReader reader = null;
            double[] costNew = new double[DAYS];
            try {
                String line = "";
                String[] cost = new String[DAYS];
                reader = new BufferedReader(new FileReader(src));

                for (int i = 0; i < 8; i++) {
                    line = reader.readLine();
                    if (i > 0) {
                        String[] row = line.split(";");
                        cost[i - 1] = row[2];
                    }
                }
                costNew = parseToDoubleCosts(cost);

            } catch (Exception e) {
                System.out.print("Ошибка при считывании данных из файла");
            }
            return costNew;
        }

    /**
     * Преобразует массив курсов из строки в массив double
     * @param cost - массив с курсами валют, считанных из файла
     * @return - массив валют типа double
     * @throws ParseException
     */
    public static double[] parseToDoubleCosts(String[] cost) throws ParseException {
        double[] costdouble = new double[cost.length];
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols sfs = new DecimalFormatSymbols();
        sfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(sfs);
        for (int i = 0; i < cost.length; i++) {
            costdouble[i] = df.parse(cost[i].substring(2, 9)).doubleValue();
        }
        return costdouble;
    }
}
