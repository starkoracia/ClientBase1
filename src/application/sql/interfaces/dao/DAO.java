package application.sql.interfaces.dao;

import java.util.List;

public interface DAO<Entity, Key> {
    void add(Entity model);
    Entity getByKey(Key key);
    List<Entity> getAll();
    void update(Entity model);
    void delete(Entity model);
}