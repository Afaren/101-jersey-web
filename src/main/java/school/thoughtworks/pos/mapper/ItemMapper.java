package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface ItemMapper {
    List<Item> findAll();

    Item find(int id);

    void delete(int id);

    void insert(Item item);

    void update(Item item);
}