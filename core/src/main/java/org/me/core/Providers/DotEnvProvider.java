package org.me.core.Providers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvProvider extends AbstractModule {
    @Provides
    Dotenv provideDotenv(Dotenv implementation) {
        return Dotenv.load();
    }
}
