import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter equation:");
        String equation = input.next();
        input.close();
        parseEquation(equation);
        infixToPostfix(peqn2(equation));
        System.out.println(""+evaluateExp(infixToPostfix(peqn2(equation)))+"");
    }

    public static String[] parseEquation (String equationToParse) {

        String delimiters = "[+\\-*/]+"; // delimiters:  + - *
        String[] parsedEquation = equationToParse.split(delimiters);
        System.out.print(Arrays.toString(parsedEquation));
        return parsedEquation;
    }

    public static double evaluateExp(String[] postfix){
        double x;
        double y;
        double ans;
        Stack<String> stack = new Stack<>();
        for(String s : postfix){
            if(s.charAt(0)=='+'){
                x = Double.parseDouble(stack.pop());
                y = Double.parseDouble(stack.pop());
                stack.push(""+(x+y)+"");
            }
            else if(s.charAt(0)=='-'){
                x = Double.parseDouble(stack.pop());
                y = Double.parseDouble(stack.pop());
                stack.push(""+(y-x)+"");
            }
            else if(s.charAt(0)=='*'){
                x = Double.parseDouble(stack.pop());
                y = Double.parseDouble(stack.pop());
                stack.push(""+(x*y)+"");
            }
            else if(s.charAt(0)=='/'){
                x = Double.parseDouble(stack.pop());
                y = Double.parseDouble(stack.pop());
                stack.push(""+(y/x)+"");
            }
            else
                stack.push(s);
        }
        ans = Double.parseDouble(stack.pop());
        return ans;
    }
    public static String[] peqn2(String entry){
        StringBuilder eqn = new StringBuilder();
        char[] cs = entry.toCharArray();
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i<entry.length(); i++){
            if(cs[i] == '(' || cs[i] == ')' || cs[i] == '+' || cs[i] == '-' || cs[i] == '*'){
                temp.add(eqn.toString());
                temp.add(String.valueOf(cs[i]));
                eqn = new StringBuilder();
            }
            else if(cs[i]==' '){
                continue;
            }
            else {
                eqn.append(cs[i]);
            }
        }
        temp.add(eqn.toString());
        return temp.toArray(new String[0]);
    }

    public static String[] infixToPostfix(String[] eqn){
        String entry;
        Stack<String> stack = new Stack<>();
        ArrayList<String> postfix = new ArrayList<>();
        for (String s : eqn) {
            entry = s;
            if (Character.isDigit(entry.charAt(0)) || Character.isLetter(entry.charAt(0))) {
                postfix.add(entry);
            } else if (entry.charAt(0) == '(') {
                stack.push(entry);
            } else if (entry.charAt(0) == ')') {
                while (stack.peek().charAt(0) != '(') {
                    postfix.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && stack.peek().charAt(0) == '(' && prec(entry) <= prec(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(entry);
            }
        }
        while(!stack.isEmpty()){
            postfix.add(stack.pop());
        }
        return postfix.toArray(new String[0]);
    }
    static int prec (String s){
        if (s.charAt(0)=='+' || s.charAt(0)=='-'){
            return 1;
        }
        if (s.charAt(0) == '*' ||s.charAt(0) == '/' ||s.charAt(0) == '%'){
            return 2;
        }
        return 0;
    }
}
