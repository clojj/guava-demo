package jwin.rs;

import jwin.MyCache;
import jwin.pojo.SomeInput;
import jwin.service.ExpensiveService;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * @author airhacks.com
 */
@Path("test")
public class Resource {

    @Inject
    ExpensiveService expensiveService;

    private ObjectMapper mapper = JsonFactory.create();

    @POST
    @Path("input")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SomeInput test(SomeInput input) throws InterruptedException, ExecutionException {
        MyCache cache = MyCache.getInstance();
        SomeInput output = cache.get(input);
        if (!output.equals(input)) {
            throw new RuntimeException("invalid value");
        }
        return output;
    }

    @POST
    @Path("configure")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String config(CacheConfig config) throws InterruptedException, ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MyCache.getInstance().configure(config);
    }

    @GET
    @Path("stats")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return MyCache.getInstance().getStats();
    }

    @GET
    @Path("sizeof")
    @Produces(MediaType.TEXT_PLAIN)
    public String sizeOf() {
        return MyCache.getInstance().getSizeOf();
    }

    @GET
    @Path("cleanup")
    @Produces(MediaType.TEXT_PLAIN)
    public void cleanUp() {
        MyCache.getInstance().cleanUp();
    }

}