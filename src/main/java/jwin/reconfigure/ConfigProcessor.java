package jwin.reconfigure;

import com.google.common.cache.CacheBuilder;
import jwin.reconfigure.configurators.ExpireAfterWrite;
import jwin.reconfigure.configurators.MaxSize;

import java.util.ArrayList;
import java.util.List;

public class ConfigProcessor {

    private List<Configurator> configurators = new ArrayList<>();

    public ConfigProcessor() {
        configurators.add(new MaxSize());
        configurators.add(new ExpireAfterWrite());
    }

    public CacheBuilder configure(List<List<String>> configurations, CacheBuilder builder) {
        for (List<String> configuration : configurations) {
            builder = exec(configuration.get(0), configuration.subList(1, configuration.size()), builder);
        }
        return builder.recordStats();
    }

    private CacheBuilder exec(String name, List<String> parameters, CacheBuilder builder) {
        for (Configurator configurator : configurators) {
            if (configurator.canConfigure(name)) {
                return configurator.configure(parameters, builder);
            }
        }
        return builder;
    }
}
