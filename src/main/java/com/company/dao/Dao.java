package com.company.dao;

import java.util.List;

public interface Dao <Entity, Id>{
    void save(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
    Entity returnById(Id id);
    List<Entity> returnAll();
}
