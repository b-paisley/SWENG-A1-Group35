import java.util.Scanner;
import java.util.Arrays;
public class Calculator {

    public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
	System.out.print("Enter equation:");
	String equation = input.next();
    input.close();
    parseEquation(equation);

    }

    public static String[] parseEquation (String equationToParse) {

    String delimiters = "[+\\-*/]+"; // delimiters:  + - *
    String[] parsedEquation = equationToParse.split(delimiters);
    System.out.print(Arrays.toString(parsedEquation));
    return parsedEquation;
    }
}
