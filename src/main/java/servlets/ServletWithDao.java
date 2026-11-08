package servlets;

import db.models.User;
import dao.UsersDao;
import dao.UsersDaoJdbcImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/users")
public class ServletWithDao extends HttpServlet {
    private UsersDao usersDao;
    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
            Class.forName("org.springframework.jdbc.datasource.DriverManagerDataSource");
            properties.load(new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String DriverClassName =properties.getProperty("db.driverClassName");
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName(DriverClassName);
            dataSource.setUrl(dbUrl);
            usersDao = new UsersDaoJdbcImpl(dataSource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> user = usersDao.find(2);
        resp.getWriter().write(String.valueOf(user));
        List<User> users = null;
        if(req.getParameter("name") != null){
         String name = req.getParameter("name");
         users = usersDao.findAllByName(name);
        }else{
            users = usersDao.findAll();
        }
        req.setAttribute("usersFromServer", users);
        resp.getWriter().write(String.valueOf(users));
    }
}
