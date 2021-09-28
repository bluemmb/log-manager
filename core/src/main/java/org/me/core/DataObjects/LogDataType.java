package org.me.core.DataObjects;

public enum LogDataType {
    DEBUG(0),
    INFO(1),
    WARNING(2),
    ERROR(3),
    CRITICAL(4);

    private final int value;

    LogDataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
