package liga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import static liga.Forecast.*;

public class csvReader {
    //функция для считывания из файла последних CONST_DAYS курсов, возвращает массив из курсов в формате double
        public static double[] csvRead(String currency) {
            String src = "";
            if (currency.equals("EUR")) {
                src = "src\\main\\resourses\\RC_F01_06_2002_T17_06_2022_EUR.csv";
            } else if (currency.equals("TRY")) {
                src = "src\\main\\resourses\\RC_F01_06_2002_T17_06_2022_TRY.csv";
            } else {
                src = "src\\main\\resourses\\RC_F01_06_2002_T17_06_2022_USD.csv";
            }
            BufferedReader reader = null;
            double[] costNew = new double[CONST_DAYS];
            try {
                String line = "";
                String[] cost = new String[CONST_DAYS];
                reader = new BufferedReader(new FileReader(src));

                for (int i = 0; i < 8; i++) {
                    line = reader.readLine();
                    if (i > 0) {
                        String[] row = line.split(";");
                        cost[i - 1] = row[2];
                    }
                }
                costNew = setDoubleCosts(cost);

            } catch (Exception e) {
                System.out.print(e.toString());
            }
            return costNew;
        }

    //функция для преобразования массива курсов из String в double
    public static double[] setDoubleCosts(String[] cost) throws ParseException {
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
