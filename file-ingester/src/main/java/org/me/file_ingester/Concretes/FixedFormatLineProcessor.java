package org.me.file_ingester.Concretes;

import org.me.file_ingester.Abstracts.LineProcessor;
import org.me.core.DataObjects.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixedFormatLineProcessor extends LineProcessor {

    private static final String regex = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3})\\s+\\[([\\w\\-\\.]*)\\]\\s+(\\w+)\\s+([\\w.]+)\\s*-\\s*([^\\r\\n]*)$";

    @Override
    public LogData process(String line) {
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS);
        final Matcher matcher = pattern.matcher(line);

        if ( ! matcher.find() )
            return null;

        if ( matcher.groupCount() != 5 )
            return null;

        LogData logData = null;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            Date date = dateFormatter.parse(matcher.group(1));
            String message = matcher.group(5).trim();

            logData = new LogData(date, matcher.group(2), matcher.group(3), matcher.group(4), message);
        }
        catch ( Exception e ) {
            return null;
        }

        return logData;
    }
}
