package org.me.file_ingester.DataObjects;

import java.util.Date;

public class LogData {
    public Date date;
    public String threadName;
    public String type;
    public String className;
    public String message;

    public LogData(Date date, String threadName, String type, String className, String message) {
        this.date = date;
        this.threadName = threadName;
        this.type = type;
        this.className = className;
        this.message = message;
    }
}
