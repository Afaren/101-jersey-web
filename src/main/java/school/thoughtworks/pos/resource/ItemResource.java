package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/")
public class ItemResource {

    @Inject
    private SqlSession session;


    @Inject
    private ItemMapper itemMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("items", "/items");
        result.put("item", "/items/:id");   // 超媒体的格式是怎么写的,参考 github? 还是其他的地方有规范

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
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
    public Response findOne(@PathParam("id") int id) {
        Item item = itemMapper.find(id);
        if (null == item) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Map result = new HashMap();
        result.put("item", item.toMap());
        return Response.status(Response.Status.OK).entity(result).build();


    }

// TODO: 1/18/17  what should return when delete a item???
    @DELETE
    @Path("/items/{id}")
    public void deleteOne(@PathParam("id") int id) {
        itemMapper.delete(id);
        session.commit();
    }

// TODO: 1/20/17 if insert fail?
    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Item item) {
        itemMapper.insert(item);
        session.commit();
        Map map = new HashMap<>();
        map.put("url", "items/" + item.getId());
        return Response.status(Response.Status.CREATED).entity(map).build();
    }

// TODO: 1/20/17 if update fail?

    @PUT
    @Path("/items/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Item item) {
        item.setId(id);
        itemMapper.update(item);
        session.commit();
        Map map = new HashMap<>();
        map.put("url", "items/" + item.getId());
        return Response.status(Response.Status.OK).entity(map).build();
    }


}
