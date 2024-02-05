package com.brandonoium.pyre.util.input;

public class KeyInput {
    private char typedChar;
    private InputAction action;

    public KeyInput(InputAction action, char typedChar) {
        this.action = action;
        this.typedChar = typedChar;
    }

    public char getTypedChar() {
        return typedChar;
    }

    public InputAction getAction() {
        return action;
    }

    public String toString() {
        return "(" + action.name() + ", '" + typedChar + "')";
    }
}
