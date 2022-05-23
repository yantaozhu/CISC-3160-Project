import java.util.*;
import java.io.*;

public class Interpreter {
    public static void main(String[] args) {
        try {
            String input;
            String[] variable = new int[10];
            String[] value = new int[10];
            int count = 0, pos = -1, result;
            Scanner sc = new Scanner(new File("input1.txt"));
            while (sc.hasNextLine()) {
                input = sc.nextLine().trim();
                basicCheck(input);
                for (int i = 0; i < input.length();i++){
                    if (input.charAt(i) == "=") {
                        if(Character.isDigit(s.charAt(i+1))) {
                            if(s.charAt(i+1) != 'O' || s.charAt(i+2) != '0') {
                                System.out.println(input);
                                variable[count] = input.substring(0,i-1);
                                value[count] = input.substring(i+1,input    .length()-2);
                                count++;
                                break;
                            }
                            else{
                                throw new RuntimeException("error");
                            }
                        }
                        else if(Character.isLetter(s.charAt(i+1)) && noOperation(input.substring(i+1,input.length()-2))) {
                            for (int i = 0; i < variable.length; i++){
                                if(variable[i] == input.substring(i+1,input.length()-2)) {
                                    pos = i;
                                }
                            }
                            if (pos != -1){
                                System.out.println(input.substring(0,i-1) + value[pos]);
                                variable[count] = input.substring(0,i-1);
                                value[count] = value[pos];
                                pos = -1;
                                count++;
                                break;
                            }
                            else{
                                throw new RuntimeException("error");
                            }
                        }
                        else {
                            result = doMath(input.substring(i+1,input.length()-2),variable,value);
                            System.out.println(input.substring(0,i-1) + result);
                            variable[count] = input.substring(0,i-1);
                            value[count] = Integer.toString(result);
                            count++;
                            break
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void basiCheck(String s){
        if ((s.charAt(s.length()-1) != ';') || (Character.isDigit(s.charAt(0)))){
            throw new RuntimeException("error");
        }
    }
    public static boolean noOperation(String s){
        String letter;
        boolean result;
        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);
            switch (letter) {
                case '+': result = false;
                    break;
                case '-': result = false;
                    return false;
                    break;
                case '*': result = false;
                    return false;
                    break;
                case '/': result = false;
                    break;
                default: break;
            }
        }
        return result;
    }
    public static int doMath(String s, String[] x, String[] y) {
        Stack<Integer> stack=new Stack<>();
        String s1, s2;
        int val1, val2;
        postfix = infixToPostfix(s);
        for(int i = 0; i < postfix.length();i++)
        {
            char c = postfix.charAt(i);
            if(Character.isLetterOrDigit(c))
                stack.push(c);
            else
            {
                s1 = stack.pop();
                if (Character.isDigit(val1)) {
                    val1 = parseInt(s1);
                }
                else {
                    for (int i = 0; i < x.length; i++) {
                        if (s1 == x[i]) {
                            val1 = parseInt(y[i]);
                            break;
                        }
                        if (i == (x.length - 1)) {
                            throw new RuntimeException("error");
                        }
                    }
                }
                s2 = stack.pop();
                if (Character.isDigit(val2)) {
                    val2 = parseInt(s2);
                }
                else {
                    for (int i = 0; i < x.length; i++) {
                        if (s2 == x[i]) {
                            val2 = parseInt(y[i]);
                            break;
                        }
                        if (i == (x.length - 1)) {
                            throw new RuntimeException("error");
                        }
                    }
                }
                switch(c)
                {
                    case '+':
                    stack.push(val2+val1);
                    break;
                     
                    case '-':
                    stack.push(val2- val1);
                    break;
                     
                    case '/':
                    stack.push(val2/val1);
                    break;
                     
                    case '*':
                    stack.push(val2*val1);
                    break;
              }
            }
        }
        return stack.pop();
    }
    public static String infixToPostfix(String s) {
        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i<s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                result = result + c;
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result = result + stack.pop();
                    stack.pop();
            }
            else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())){
                    result = result + stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()){
            if(stack.peek() == '('){
                throw new RuntimeException("error");
            }
            result = result + stack.pop();
         }
        return result;
    }
    public static int precedence(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
}
