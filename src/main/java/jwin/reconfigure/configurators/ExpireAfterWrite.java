package jwin.reconfigure.configurators;

import com.google.common.cache.CacheBuilder;
import jwin.reconfigure.Configurator;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExpireAfterWrite implements Configurator {
    @Override
    public boolean canConfigure(String builderMethod) {
        return builderMethod.equals("expireAfterWrite");
    }

    @Override
    public CacheBuilder configure(List<String> parameters, CacheBuilder builder) {
        long duration = Long.parseLong(parameters.get(0));
        return builder.expireAfterWrite(duration, TimeUnit.SECONDS);
    }
}
