package liga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;

public class Forecast{
    public static double countForecast(){

    }

    public static double[] setDoubleCosts(String[] cost){
    double[] costdouble = new double[cost.length];
        for (int i = 0; i < cost.length; i++) {
        costdouble[i] = Double.parseDouble(cost[i].substring(2, 9).replace(",","."));
        }
        return costdouble;
    }

    public static String[] csvRead (String currency){
        String src = "";
        if(currency.equals("EUR")){
            src = "src\\RC_F01_06_2002_T17_06_2022_EUR.csv";
        } else if (currency.equals("TRY")) {
            src = "src\\RC_F01_06_2002_T17_06_2022_TRY.csv";
        } else {
            src = "src\\RC_F01_06_2002_T17_06_2022_USD.csv";
        }
        BufferedReader reader = null;
        try {
            String line = "";
            String [] cost = new String [7];
            reader = new BufferedReader(new FileReader(src));

            for (int i = 0; i < 8; i++) {
                line = reader.readLine();
                if(i > 0) {
                    String[] row = line.split(";");

                    for (int j = 0; j < row.length; j++) {
                        System.out.print(row[j] + " ");
                    }
                    cost[i-1] = row[2];
                    System.out.println("");
                }
            }
            double[] costNew = setDoubleCosts(cost);
            for (double index : costNew) {
                System.out.println(index);//System.out.printf("%-10s", index);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String[0];
}

    public static void main( String[] args )
    {
        System.out.print("Enter a request: ");
        String input = new Scanner(System.in).nextLine();
        String currency = input.substring(5, 8);
        String[] str = csvRead(currency);

    }
}
