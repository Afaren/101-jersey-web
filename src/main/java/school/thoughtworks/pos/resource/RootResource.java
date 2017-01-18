package school.thoughtworks.pos.resource;

import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/")
public class RootResource {

    @Inject
    private ItemMapper itemMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("items", "/items");
        result.put("item", "/items/:id");   // 超媒体的格式是怎么写的,参考 github? 还是其他的地方有规范

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        Map<String, Object> result = new HashMap<>();

        List<Item> originItems = itemMapper.findAll();

        List<Map> items = originItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("items", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") int id) {
        Item item = itemMapper.find(id);
        if (null == item) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Map result = new HashMap();
        result.put("item", item.toMap());
        return Response.status(Response.Status.OK).entity(result).build();


    }
}

