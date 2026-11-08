package dao;

import db.models.User;

import java.util.List;

public interface UsersDao extends crudDao<User> {
    List<User> findAllByName (String name);
}
