package org.me.file_ingester.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameParts {
    private static final String regex = "^(\\w+)\\-(\\d{4}_\\d{2}_\\d{2}\\-\\d{2}_\\d{2}_\\d{2})\\.log$";
    private static final List<String> validComponentNames;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        validComponentNames = Arrays.asList(dotenv.get("COMPONENTS").split(","));
    }

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

        String componentName = matcher.group(1);
        String datetimeString = matcher.group(2);

        if ( ! validComponentNames.contains(componentName) )
            return null;

        return new FilenameParts(componentName, datetimeString);
    }
}
