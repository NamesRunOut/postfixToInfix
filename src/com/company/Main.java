package com.company;

class stack{
    char[] a;
    int n;

    public stack() {
        a = new char[256];
        n=0;
    }
    int currentSize(){
        return n;
    }
    char top(){
        return a[n-1];
    }
    void pop(){
        n--;
    }
    void push(char x){
        a[n]=x;
        n++;
    }
    boolean isEmpty(){
        return n == 0;
    }
}

class stackStr{
    String[] a;
    int n;

    public stackStr() {
        a = new String[256];
        n=0;
    }
    int currentSize(){
        return n;
    }
    String top(){
        return a[n-1];
    }
    void pop(){
        n--;
    }
    void push(String x){
        a[n]=x;
        n++;
    }
    boolean isEmpty(){
        return n == 0;
    }
}

class postToIn {
    private static int priority(char s) {
        int priorityValue = -1;
        if (s == '=')
            priorityValue = 0;
        if (s == '<' || s == '>')
            priorityValue = 1;
        if (s == '+' || s == '-')
            priorityValue = 2;
        if (s == '*' || s == '/' || s == '%')
            priorityValue = 3;
        if (s == '^')
            priorityValue = 4;
        if (s == '~')
            priorityValue = 5;
        if (s >= 'a' && s <= 'z')
            priorityValue = 6;
        return priorityValue;
    }

    private static char leftOrRight(char c) {
        int n = priority(c);
        if (n == 0 || n == 4 || n == 5) {
            return 'p';
        }
        if (n == 1 || n == 2 || n == 3) {
            return 'l';
        }
        return '?';
    }

    private static char getNextOperator(String a, int s) {
        if (s + 1 < a.length())
            for (int i = s + 1; i < a.length(); i++) {
                char c = a.charAt(i);
                if (priority(c) < 6) {
                    return c;
                }
            }
        return '0';
    }

    private static int getNext(String a, int s) {
        if (s + 1 < a.length()) {
            return priority(a.charAt(s + 1));
        }
        return 0;
    }

    static String convert(String a) {
        stackStr tmp = new stackStr();
        stack priorities = new stack();
        StringBuilder tmp2 = new StringBuilder();
        boolean d = false;
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            tmp2.delete(0, 256);

            if (priority(c) == 6) {
                String k = String.valueOf(c);
                tmp.push(k);
                priorities.push(c);
            } else {
                if (priority(c) < 5) {
                    String o1 = tmp.top();
                    tmp.pop();
                    if ((leftOrRight(c) == 'l' && priority(priorities.top()) <= priority(c)) || (leftOrRight(c) == 'p' && priority(priorities.top()) < priority(c))) {
                        tmp2.insert(0, ")").insert(0, o1).insert(0, "(");
                    } else {
                        tmp2.insert(0, o1);
                    }
                    priorities.pop();
                    o1 = tmp.top();
                    tmp.pop();
                    if (priority(priorities.top()) < priority(c)) {
                        tmp2.insert(0, c).insert(0, ")").insert(0, o1).insert(0, "(");
                        d = true;
                    } else {
                        if (leftOrRight(c) == 'p' && leftOrRight(getNextOperator(a, i)) == 'p' && getNext(a, i) == 6 && priority(getNextOperator(a, i)) != 5 && !d) {
                            tmp2.insert(0, c).insert(0, o1).insert(0, "(").append(")");
                            d = false;
                        } else {
                            tmp2.insert(0, c).insert(0, o1);
                        }
                    }
                    priorities.pop();
                } else {
                    String o1 = tmp.top();
                    tmp.pop();
                    if (c == '~' && priority(priorities.top()) < priority(c)) {
                        tmp2.insert(0, ")").insert(0, o1).insert(0, "(").insert(0, c);
                    } else {
                        tmp2.insert(0, c).insert(1, o1);
                    }
                    priorities.pop();
                }
                tmp.push(tmp2.toString());
                priorities.push(c);
            }
        }
        return tmp.top();
    }
}

public class Main {

    public static void main(String[] args) {
        // assuming that the input expression is correct
        // returns a postfix statement converted to infix
        // with a minimal number of parenthesis indicating the correct order of operations
        String statement = "xab*cd+ef/gh/*-+ij^^k=l^=";
        System.out.println(postToIn.convert(statement));
        statement = "xab+c*=";
        System.out.println(postToIn.convert(statement));
    }
}
