package jwin;

import com.google.common.cache.*;
import jwin.pojo.SomeInput;
import jwin.reconfigure.ConfigProcessor;
import jwin.rs.CacheConfig;
import jwin.service.ExpensiveService;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.openjdk.jol.info.GraphLayout;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;

@ApplicationScoped
public class MyCache {

    private static MyCache ourInstance = new MyCache();

    private volatile Cache<String, SomeInput> cache;

    private final ConfigProcessor configProcessor;

    private final ExpensiveService expensiveService;
    private String sizeOf;


    public static MyCache getInstance() {
        return ourInstance;
    }

    private MyCache() {

        expensiveService = new ExpensiveService();

        configProcessor = new ConfigProcessor();

        cache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .expireAfterAccess(30, SECONDS)
                .softValues()
                .removalListener(new RemovalListener<String, SomeInput>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, SomeInput> removalNotification) {
                        if (removalNotification.wasEvicted()) {
                            if (RemovalCause.COLLECTED.equals(removalNotification.getCause())) {
                                System.out.println("removalNotification.getCause().name() = " + removalNotification.getCause().name());
                            }
                        }
                    }
                })
                .recordStats()
//                .maximumWeight(10000)
//                .weigher(new Weigher<SomeInput, SomeValue>() {
//                    public int weigh(SomeInput k, SomeValue v) {
////                        long estimate = SizeEstimator.estimate(v);
//                        long estimate = RamUsageEstimator.sizeOf(k) + RamUsageEstimator.sizeOf(v);
//                        System.out.println("estimate = " + estimate);
//                        return (int) estimate;
//                    }
//                })

                .build();
    }

    public SomeInput get(final SomeInput input) throws ExecutionException {

        ObjectMapper mapper = JsonFactory.create();
        final String json = mapper.toJson(input);

        return cache.get(json, new Callable<SomeInput>() {
            @Override
            public SomeInput call() throws InterruptedException {
                System.out.println("Cache write, key: " + json);
                return expensiveService.expensiveComputation(input);
            }
        });
    }

    public String getStats() {
        return cache.stats().toString() + " size: " + cache.size();
    }

    public void cleanUp() {
        cache.invalidateAll();
        cache.cleanUp();
    }

    public String configure(CacheConfig config) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        List<List<String>> configurations = config.getConfigurations();
        CacheBuilder newBuilder = configProcessor.configure(configurations, cacheBuilder);
        Cache newCache = newBuilder.build();
        cache = newCache;
        return "Success";
    }

    public String getSizeOf() {
        return GraphLayout.parseInstance(cache.asMap().entrySet()).toFootprint();
    }
}
