package dao;

import db.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbcImpl implements UsersDao{
     //language=SQL
     private final String SQL_SELECT_ALL = "select * from fix_user";
     //language=SQL
     private final String SQL_SELECT_BY_ID = "select * from fix_user where id = ?";
     private Connection connection;
     public UsersDaoJdbcImpl(DataSource source){
        try{
            this.connection = source.getConnection();
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }
     }
    @Override
    public List<User> findAllByName(String name) {
        return null;
    }

    @Override
    public Optional<User> find(Integer id) {
        try{
            PreparedStatement st = connection.prepareStatement(SQL_SELECT_BY_ID);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                return Optional.of(new User(id, name, lastName));
            }
        return Optional.empty();
        }catch(SQLException e){
            throw new IllegalStateException(e);
        }

    }
    @Override
    public void save(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
         try{
             List<User> users = new ArrayList<>();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL);
              while(rs.next()){
                  Integer id = rs.getInt("id");
                  String name = rs.getString("name");
                  String lastName = rs.getString("lastName");
                  User user = new User(id, name, lastName);
                  users.add(user);
              }
             return users;
         }catch(SQLException e){
             throw new IllegalStateException(e);
         }
    }
}
