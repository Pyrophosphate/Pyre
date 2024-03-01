package com.brandonoium.pyre.ui.widgets;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;
import com.brandonoium.bithorse.CharSetMap;

import java.util.ArrayDeque;

/**
 * A terminal widget for displaying a list of arbitrary text messages to the player. Messages are shown in chronological order, with the most recent always at the bottom. Old messages will scroll off the top of the widget.
 */
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

    private void shiftOldMessages() {
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
