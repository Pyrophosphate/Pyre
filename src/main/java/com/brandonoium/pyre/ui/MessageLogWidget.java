package com.brandonoium.pyre.ui;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;
import com.brandonoium.bithorse.BitTermCharSet;
import com.brandonoium.bithorse.CharSetMap;

import java.util.ArrayDeque;

public class MessageLogWidget extends TerminalUiWidget {

    private int maxCapacity;
    private ArrayDeque<String> messageList;
    private ArrayDeque<String> newMessages;

    public MessageLogWidget(int w, int h, int x, int y, int capacity) {
        super(w, h, x, y);

        messageList = new ArrayDeque<>();
        newMessages = new ArrayDeque<>();

        maxCapacity = capacity;
    }


    public void postMessage(String msg) {
        newMessages.add(msg);
    }

    public void shiftOldMessages() {
        for(String msg : newMessages) {
            messageList.add(msg);
        }

        newMessages.clear();

        int overage = messageList.size() - maxCapacity;

        for(int i = 0; i < overage; i++) {
            messageList.poll();
        }
    }


    @Override
    public BitHorseTerminalBuffer draw() {
        //buffer.clearBuffer();

        for(String msg : newMessages) {
            buffer.newline();
            buffer.printGlyph(CharSetMap.RIGHT_ARROW);
            buffer.advanceCursor();
            buffer.printStringWordWrap(msg);
        }
        shiftOldMessages();

        return buffer;
    }
}
