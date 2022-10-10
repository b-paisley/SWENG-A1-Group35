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
        char[] eqnSplit = new char[equation.length()];

        for (int i=0; i<equation.length(); i++) 
        {
           eqnSplit[i] = equation.charAt(i);
           System.out.print(eqnSplit[i] + " ");
        }
        System.out.print("\n");

        if(noUnexpectedString(eqnSplit) && noOperatorsOnStartOrEnd(eqnSplit) &&
         noOperatorsTogether(eqnSplit) && noUnclosedBackets(eqnSplit))
        {
           infixToPostfix(peqn2(equation));
           System.out.println(""+evaluateExp(infixToPostfix(peqn2(equation)))+"");
        }
        else 
        {
            System.out.println("Errors detetcted in your equation. Please try again.");
        }
    }

    public static String[] parseEquation (String equationToParse) {

        String delimiters = "[+\\-*/]+"; // delimiters:  + - *
        String[] parsedEquation = equationToParse.split(delimiters);
        //System.out.print(Arrays.toString(parsedEquation));
        return parsedEquation;
    }

    public static boolean noUnexpectedString(char[] eqn)
    {
        //checking for unexpected string characters (letters, etc)
        for(int i =0; i < eqn.length; i++)
        {
              if (eqn[i] == '+' ||
              eqn[i] == '-' ||
              eqn[i] == '*' ||
              eqn[i] == '/' ||
              eqn[i] == '%' ||
              eqn[i] == '(' ||
              eqn[i] == ')' ||
              (eqn[i] >= '0' && eqn[i] <= '9'))
              {
                continue;
              }
              else
              {
                 System.out.println("Your equation contains invalid characters.");
                 return false;
              }
       }
        return true;
    }

    public static boolean noOperatorsOnStartOrEnd(char[] eqn)
    {
        //checking if equation starts or ends with an operator
        if(eqn[0] == '+' || eqn[eqn.length-1] == '+' ||
        eqn[0] == '-'  || eqn[eqn.length-1] == '-' ||
        eqn[0] == '*' || eqn[eqn.length-1] == '*' ||
        eqn[0] == '/' || eqn[eqn.length-1] == '/' ||
        eqn[0] == '%' || eqn[eqn.length-1] == '%')
        {
            System.out.println("Equation cannot start or end with an operator");
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean noOperatorsTogether(char[] eqn)
    {
        //checking for two operators next to eachother
        for(int i = 1; i < eqn.length-1; i++)
        {
            if(eqn[i] == '+' ||
            eqn[i] == '-'  ||
            eqn[i] == '*'  ||
            eqn[i] == '/'  ||
            eqn[i] == '%')
            {
                if(eqn[i+1] == '+' ||
                eqn[i+1] == '-'  ||
                eqn[i+1] == '*'  ||
                eqn[i+1] == '/'  ||
                eqn[i+1] == '%')
                {
                    System.out.println("Equation cannot have two operators next to eachother");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean noUnclosedBackets(char[] eqn)
    {
        //checking for unclosed brackets
        int balance = 0;
        for(int i = 0; i < eqn.length; i++)
        {
            if(eqn[i] == '(')
            {
                balance++;
            }
            else if(eqn[i] == ')')
            {
                if(balance !=0)
                {
                    balance--;
                }
                else
                {
                    System.out.println("Equation cannot have unclosed brackets");
                }
            }
        }
        if (balance !=0)
        {
            System.out.println("Equation cannot have unclosed brackets");
            return false;
        }
        else
        {
           return true;
        }
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
