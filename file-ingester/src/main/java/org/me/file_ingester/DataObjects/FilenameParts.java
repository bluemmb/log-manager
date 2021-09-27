package org.me.file_ingester.DataObjects;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameParts {
    private static final String regex = "^(\\w+)\\-(\\d{4}_\\d{2}_\\d{2}\\-\\d{2}_\\d{2}_\\d{2})\\.log$";

    public String componentName;
    public String datetimeString;

    public FilenameParts(String componentName, String datetimeString) {
        this.componentName = componentName;
        this.datetimeString = datetimeString;
    }

    public static FilenameParts fromPath(Path path) {
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(path.getFileName().toString());

        if ( ! matcher.find() )
            return null;

        if ( matcher.groupCount() != 2 )
            return null;

        return new FilenameParts(matcher.group(1), matcher.group(2));
    }
}
