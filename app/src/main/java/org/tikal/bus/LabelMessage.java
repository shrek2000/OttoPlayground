package org.tikal.bus;

/**
 * Created by Oren Kleks on 1/1/2015.
 */
public class LabelMessage {

    private Integer targetNumber;
    private String message;

    public LabelMessage(Integer targetNumber, String message) {
        this.targetNumber = targetNumber;
        this.message = message;
    }

    public Integer getTargetNumber() {
        return targetNumber;
    }

    public String getMessage() {
        return message;
    }
}
