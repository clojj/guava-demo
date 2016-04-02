package jwin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jwin.pojo.SomeInput;
import jwin.service.ExpensiveService;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;

@ApplicationScoped
public class MyCache {

    private static MyCache ourInstance = new MyCache();

    private final Cache<String, SomeInput> cache;
    private final ExpensiveService expensiveService;

    public static MyCache getInstance() {
        return ourInstance;
    }

    private MyCache() {
        expensiveService = new ExpensiveService();
        cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(20, SECONDS)
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
}
