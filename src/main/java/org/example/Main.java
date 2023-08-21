package org.example;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Enter path of dependecytree txt file you want to parse in an csv file:");
        Scanner scanner = new Scanner(System.in);

        String filepath = scanner.nextLine();

        scanner.close();

        String path;
        Pattern pattern = Pattern.compile("^.*[/\\\\]");
        Matcher matcher = pattern.matcher(filepath);
        matcher.find();
        path = matcher.group(0);

        pattern = Pattern.compile("[^/\\\\]*$");
        matcher = pattern.matcher(filepath);
        matcher.find();
        String filename = matcher.group(0);
        filename = filename.split("\\.")[0];

        FileReader input;
        try {
            input = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        StringBuilder csv = new StringBuilder();

//        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
//        "s", "t", "u", "v", "w", "x", "y", "z"};

        while ((myLine = bufRead.readLine()) != null) {
            csv.append(parseLine(myLine)).append("\n");
        }

        PrintWriter out = new PrintWriter(path + filename + "converted2.csv");
        out.println(csv);
        out.close();
    }

    private static String parseLine(String myLine) {
        //String csvLine = "";
        //String layerChar = "|";
        int layerCounter = 0;

/*        if (Stream.of(myLine).anyMatch(s -> myLine.startsWith(s))) {
            csvLine = myLine;
        }
        else {*/
            while (myLine.matches("(\\s*\\|.*$)")) {
                myLine= myLine.replaceFirst("\\s*\\|", "");
                layerCounter++;
            }

            if (myLine.matches("(\\s*\\+-\\s*.*$)")) {
                myLine =myLine.replaceFirst("\\s*\\+-\\s*", "");
            }
            if (myLine.matches("(\\s*\\\\-\\s*.*$)")) {
                myLine = myLine.replaceFirst("\\s*\\\\-\\s*", "");
            }
            //csvLine = myLine;
        //}
        return sequenceOfCommas(layerCounter) + myLine;
    }

    private static String sequenceOfCommas(int layerCounter) {
        StringBuilder commas = new StringBuilder();
        for (int i = layerCounter; i > 0; i--) {
            commas.append(",");
        }
        return commas.toString();
    }
}