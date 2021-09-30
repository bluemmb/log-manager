package org.me.core.DataObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class LogData {
    public String componentName;
    public Date date;
    public String threadName;
    public String type;
    public String className;
    public String message;

    public LogData(
            @JsonProperty("componentName") String componentName,
            @JsonProperty("date") Date date,
            @JsonProperty("threadName") String threadName,
            @JsonProperty("type") String type,
            @JsonProperty("className") String className,
            @JsonProperty("message") String message
    ) {
        this.componentName = componentName;
        this.date = date;
        this.threadName = threadName;
        this.type = type;
        this.className = className;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogData logData = (LogData) o;
        return componentName.equals(logData.className) && date.equals(logData.date) && threadName.equals(logData.threadName) && type.equals(logData.type) && className.equals(logData.className) && message.equals(logData.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName, date, threadName, type, className, message);
    }
}
