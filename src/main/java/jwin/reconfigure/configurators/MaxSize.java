package jwin.reconfigure.configurators;

import com.google.common.cache.CacheBuilder;
import jwin.reconfigure.Configurator;

import java.util.List;

public class MaxSize implements Configurator {
    @Override
    public boolean canConfigure(String builderMethod) {
        return builderMethod.equals("maximumSize");
    }

    @Override
    public CacheBuilder configure(List<String> parameters, CacheBuilder builder) {
        long size = Long.parseLong(parameters.get(0));
        return builder.maximumSize(size);
    }
}
