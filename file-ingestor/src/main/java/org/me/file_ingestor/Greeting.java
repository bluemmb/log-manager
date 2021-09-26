package org.me.file_ingestor;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;

public class Greeting {

    @Inject
    Greeting(Dotenv env) {
        System.out.println( "Welcome to " + env.get("APP_NAME") );
    }
}
