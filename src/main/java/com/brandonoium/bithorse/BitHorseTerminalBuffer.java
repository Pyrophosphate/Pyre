package com.brandonoium.bithorse;

import java.util.ArrayList;

public class BitHorseTerminalBuffer {
    private int[][] buffer;
    private int rows, columns;
    private int cx = 0, cy = 0;


    public BitHorseTerminalBuffer(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        buffer = new int[this.rows][this.columns];
    }

    public int getFromPosition(int x, int y) {
        if(isLocationValid(x, y)) {
            return buffer[y][x];
        } else {
            return 0;
        }
    }

    public void setCursor(int x, int y) throws CursorOutOfBoundsException {
        if(isLocationValid(x, y)) {
            cx = x;
            cy = y;
        } else {
            throw new CursorOutOfBoundsException("");
        }
    }

    private boolean isLocationValid(int x, int y) {
        return (x >= 0 && x < columns && y >= 0 && y < rows);
    }

    public void advanceCursor() {
        cx++;
        if(cx >= columns) {
            cx = 0;
            cy++;
        }
        if(cy >= rows) {
            cy = rows - 1;
            scroll();
        }
    }

    public void newline() {
        cx = 0;
        cy++;
        if(cy >= rows) {
            cy = rows - 1;
            scroll();
        }
    }

    public void printChar(char c) {
        if(c == '\t') {
            do {
                printGlyph((int)' ');
                advanceCursor();
            } while((cx + 1) % 4 != 0);
        } else if(c == '\n') {
            newline();
        } else {
            printGlyph(c);
        }
    }

    public void printGlyph(int c) {
        buffer[cy][cx] = c;
    }

    public void printString(String s) {
        for(char c : s.toCharArray()) {
            printChar(c);
            advanceCursor();
        }
    }

    public void printStringWordWrap(String s) {
        ArrayList<Character> word = new ArrayList<>();

        for(char c : s.toCharArray()) {
            if(c != ' ' && c != '\n' && c != '\t' && c != '-') {
                word.add(c);
            } else {
                if(word.size() > (columns - cx)) {
                    if(word.size() <= columns) {
                        newline();
                    }
                }
                word.add(c);
                for(char cw : word) {
                    switch (cw) {
                        case '\n':
                            printChar(cw);
                            break;
                        case ' ':
                            if(cx == 0) {
                                break;
                            }
                        default:
                            printChar(cw);
                            advanceCursor();
                    }
                }
                word.clear();
            }
        }

        if(word.size() > (columns - cx)) {
            if(word.size() <= columns) {
                newline();
            }
        }
        for(char cw : word) {
            printChar(cw);
            advanceCursor();
        }
    }

    public void scroll() {
        scroll(1);
    }

    public void scroll(int lines) {
        for(int y = 0; y < (rows - lines); y++) {
            for(int x = 0; x < columns; x++) {
                buffer[y][x] = buffer[y + lines][x];
            }
        }
        for(int y = (rows - lines); y < rows; y++) {
            for(int x = 0; x < columns; x++) {
                buffer[y][x] = 0;
            }
        }
    }

    public void clearBuffer() {
        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < columns; x++) {
                buffer[y][x] = 0;
            }
        }
    }


    public void copyFromBuffer(BitHorseTerminalBuffer other, int xOffset, int yOffset) {
        for(int y = Math.max(0, yOffset); y < Math.min(rows, yOffset + other.rows); y++) {
            for(int x = Math.max(0, xOffset); x < Math.min(columns, xOffset + other.columns); x++) {
                if(isLocationValid(x, y))
                    buffer[y][x] = other.buffer[y - yOffset][x - xOffset];
            }
        }
    }
}
