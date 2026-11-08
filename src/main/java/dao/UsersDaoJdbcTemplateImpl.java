package dao;

import db.models.Car;
import db.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.sql.Array;
import java.util.*;

public class UsersDaoJdbcTemplateImpl implements UsersDao{
    private JdbcTemplate jdbcTemplate;
    //language=SQL
    private final String SQL_SELECT_ALL = "select * from fix_user";
    //language=SQL
    private final String SQl_SELECT_ALL_BY_NAME = "select * from fix_user where name = ?";
    //language=SQL
    private final String SQL_SELEC_ALL_USER_WITH_CAR = "select fix_user.*, fix_car.*, fix_car.id as car_id, fix_car.model from fix_user left join fix_car on fix_user.id = fix_car.owner_id where fix_user.id = ?";
    private Map<Integer, User> usersMap = new HashMap<>();
    private RowMapper<User> userRowMapper = (resultSet, i)-> {
        Integer id = resultSet.getInt("id");
        if(!usersMap.containsKey(id)){
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            User user = new User(id, name, lastName,new ArrayList<>());
            usersMap.put(id,user);
        }
        Car car = new Car(resultSet.getInt("car_id"), resultSet.getString("model"), usersMap.get(id));
        usersMap.get(id).getCars().add(car);
        return usersMap.get(id);
    };
    public UsersDaoJdbcTemplateImpl(DataSource source){
        this.jdbcTemplate = new JdbcTemplate(source);
    }

    @Override
    public List<User> findAllByName(String name) {
        return jdbcTemplate.query(SQl_SELECT_ALL_BY_NAME,userRowMapper,name);
    }
    @Override
    public Optional<User> find(Integer id) {
        jdbcTemplate.query(SQL_SELEC_ALL_USER_WITH_CAR,userRowMapper,id);
        if(usersMap.containsKey(id)){
            return Optional.of(usersMap.get(id));
        }
            return Optional.empty();

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
        return jdbcTemplate.query(SQL_SELECT_ALL,userRowMapper);
    }
}
