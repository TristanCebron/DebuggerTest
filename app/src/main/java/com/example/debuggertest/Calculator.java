package com.example.debuggertest;

public final class Calculator {

    public enum Operator {
        ADD, SUB, DIV, MUL
    }

    private Calculator() {
    }

    public static double add(double operandOne, double operandTwo) {
        return operandOne + operandTwo;
    }

    public static double subtract(double operandOne, double operandTwo) {
        return operandOne - operandTwo;
    }

    public static double divide(double operandOne, double operandTwo) {
        return operandOne / operandTwo;
    }

    public static double multiply(double operandOne, double operandTwo) {
        return operandOne * operandTwo;
    }
}
