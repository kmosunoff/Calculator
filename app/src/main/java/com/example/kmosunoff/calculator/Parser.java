package com.example.kmosunoff.calculator;

import java.util.Stack;

public abstract class Parser {

    public static String evaluateFraction(String expression) {
        RationalFraction answer = evaluate(expression);
        if (answer == null) {
            return "Incorrect input";
        }
        return answer.toString();
    }

    public static String evaluateDecimal(String expression) {
        RationalFraction answer = evaluate(expression);
        if (answer == null) {
            return "Incorrect input";
        }
        return answer.toDecimalString();
    }

    private static RationalFraction evaluate(String expression) {
        try {
            boolean mayUnary = true;
            Stack<RationalFraction> numbers = new Stack<>();
            Stack<Operator> operators = new Stack<>();
            for (int i = 0; i < expression.length(); ++i) {
                switch (expression.charAt(i)) {
                    case '(' : {
                        operators.push(new Operator('('));
                        mayUnary = true;
                        break;
                    }
                    case ')' : {
                        while (!operators.peek().equals(new Operator('('))) {
                            processOperation(numbers, operators.pop());
                        }
                        operators.pop();
                        mayUnary = false;
                        break;
                    }
                    case ':' : {
                        while (!operators.peek().equals(new Operator('?'))) {
                            processOperation(numbers, operators.pop());
                        }
                        operators.pop();
                        RationalFraction number = numbers.pop();
                        operators.push(new Operator('T', number));
                        mayUnary = false;
                        break;
                    }
                    default : {
                        if (isOperator(expression.charAt(i))) {
                            char c = expression.charAt(i);
                            Operator currentOperator = new Operator(c);
                            if (mayUnary && (c == '+' || c == '-')) {
                                currentOperator.setUnary(true);
                            }
                            while (!operators.isEmpty()
                                    && priority(operators.peek()) >= priority(currentOperator)) {
                                processOperation(numbers, operators.pop());
                            }
                            operators.push(currentOperator);
                            mayUnary = true;
                        }
                        else {
                            StringBuilder operand = new StringBuilder();
                            while (i < expression.length() && isNumeric(expression.charAt(i))) {
                                operand.append(expression.charAt(i++));
                            }
                            --i;
                            numbers.push(new RationalFraction(operand.toString()));
                            mayUnary = false;
                        }
                    }
                }
            }
            while (!operators.empty()) {
                processOperation(numbers, operators.pop());
            }
            return numbers.pop();
        }
        catch (Exception e) {
            return null;
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '?';
    }

    private static boolean isNumeric(char c) {
        return "0123456789.".indexOf(c) != -1;
    }

    private static int priority(Operator operator) {
        if (operator.isUnary()) {
            return 3;
        }
        if (operator.getOperation() == '+' || operator.getOperation() == '-') {
            return 1;
        }
        if (operator.getOperation() == '*' || operator.getOperation() == '/') {
            return 2;
        }
        if (operator.getOperation() == '?' || operator.getOperation() == ':') {
            return 2;
        }
        if (operator.getOperation() == '<' || operator.getOperation() == '>') {
            return 3;
        }
        if (operator.getOperation() == 'T') {
            return 3;
        }
        return -1;
    }

    private static void processOperation(Stack<RationalFraction> numbers, Operator operator) {
        if (operator.isUnary()) {
            RationalFraction left = numbers.pop();
            switch (operator.getOperation()) {
                case '+' : {
                    numbers.push(left);
                    break;
                }
                case '-' : {
                    numbers.push(left.negate());
                    break;
                }
            }
        }
        else {
            RationalFraction right = numbers.pop();
            RationalFraction left = numbers.pop();
            switch (operator.getOperation()) {
                case '+': {
                    numbers.push(left.plus(right));
                    break;
                }
                case '-': {
                    numbers.push(left.minus(right));
                    break;
                }
                case '*': {
                    numbers.push(left.multiply(right));
                    break;
                }
                case '/': {
                    numbers.push(left.divide(right));
                    break;
                }
                case '<': {
                    if (left.lessThan(right)) {
                        numbers.push(new RationalFraction("1"));
                    }
                    else {
                        numbers.push(new RationalFraction("0"));
                    }
                    break;
                }
                case '>': {
                    if (left.greaterThan(right)) {
                        numbers.push(new RationalFraction("1"));
                    }
                    else {
                        numbers.push(new RationalFraction("0"));
                    }
                    break;
                }
                case 'T': {
                    RationalFraction answer = (left.equals(new RationalFraction("0")))
                            ? right
                            : operator.getNumber();
                    numbers.push(answer);
                    break;
                }
            }
        }
    }
}
