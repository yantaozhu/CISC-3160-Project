import java.util.*;
import java.io.*;

public class Interpreter {

    private String s;
    private int index;
    private char inputChar;
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    
    public static void main(String args[]) {
        try {
            Scanner sc = new Scanner(new File("test.txt"));
            Interpreter evalExp = new Interpreter();
            evalExp.run(sc);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void run(Scanner sc) {
        while (sc.hasNextLine()) {
            removeSpace(sc.nextLine());
            assignment();
        }
    }

    void removeSpace(String s) {
        this.s = s.replaceAll("\\s", "");
        index = 0;
        nextChar();
    }

    void nextChar(){
        char c;
        if(!s.endswith(';')) {
            throw new RuntimeException("error");
        }
        c = s.charAt(index++);
        inputChar = c;
    }

    void assignment() {
        String var = identifier();
        int operand = eval();
        map.put(var, operand);
        System.out.println(var + " = " + operand);
    }

    String identifier(){
        StringBuilder sb = new StringBuilder();

        if (Character.isLetter(inputChar)) {
            sb.append(inputChar);
        }
        else
            throw new RuntimeException("error");
        nextChar();

        while (Character.isLetter(inputChar) || Character.isDigit(inputChar) || inputChar == '_' ) {
            sb.append(inputChar);
            nextChar();
        }

        if (inputChar != '=') {
            throw new RuntimeException("error");
        }
        nextChar();
        return sb.toString();
    }

    int eval() {
        int x = exp();
        if (inputChar == ';') {     
            return x;
        } else {
            throw new RuntimeException("error");
        }
    }

    int exp() {
        int t = term();
        while (inputChar == '+' || inputChar == '-') {
            char inputChar = inputChar;
            nextChar();
            int t2 = term();
            t = apply(inputChar, t, t2);
        }
        return t;
    }

    int term() {
        int f = factor();
        while (inputChar == '*' || inputChar == '/') {
            char inputChar = inputChar;
            nextChar();
            int f2 = factor();
            f = apply(inputChar, f, f2);
        }
        return f;
    }

    int factor() {
        int x = 0;
        String temp = String.valueOf(inputChar);
        if (map.containsKey(temp)) {
            x = map.get(temp).intValue();
            nextChar();
            return x;
        } else if (inputChar == '(') {
            nextChar();
            x = exp();
            match(')');
            return x;
        } else if (inputChar == '-') {
            nextChar();
            x = factor();
            return -x;
        } else if (inputChar == '+') {
            nextChar();
            x = factor();
            return x;
        } else if (inputChar == '0') {
            nextChar();
            if (Character.isDigit(inputChar))
                throw new RuntimeException("error");
            return 0;
        }
        temp = "";
        while (Character.isDigit(inputChar)) {
            temp += inputChar;
            nextChar();
        }
        return Integer.parseInt(temp);
    }

    void match(char token) {
        if (inputChar == token) {
            nextChar();
        } else {
            throw new RuntimeException("error");
        }
    }

    static int apply(char ch, int t, int t2) {
        int t3 = 0;
        switch (ch) {
            case '+':
                t3 = t + t2;
                break;
            case '-':
                t3 = t - t2;
                break;
            case '*':
                t3 = t * t2;
                break;
            case '/':
                t3 = t / t2;
                break;
        }
        return t3;
    }
}
