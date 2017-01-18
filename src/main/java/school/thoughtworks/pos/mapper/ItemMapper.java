package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface ItemMapper {
    List<Item> findAll();

    Item find(int id);
}