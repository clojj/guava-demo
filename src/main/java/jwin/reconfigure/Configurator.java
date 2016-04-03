package jwin.reconfigure;

import com.google.common.cache.CacheBuilder;

import java.util.List;

public interface Configurator {
    boolean canConfigure(String builderMethod);

    CacheBuilder configure(List<String> parameters, CacheBuilder builder);
}
