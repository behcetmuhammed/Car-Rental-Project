package dao;

import core.DbConnect;
import core.Halper;
import entity.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = DbConnect.getInstance();
    }

    //veritabanındaki tüm User verilerini çekmek
    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            Statement stateM = this.connection.createStatement(); //Burda sql srogumuzda ? ihtiyacımız olmadığı için PreparedStatement yerine Statement bize yeterli.
            ResultSet resultSet = stateM.executeQuery(sql);
            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement prState = this.connection.prepareStatement(query);
            prState.setString(1, username);
            prState.setString(2, password);
            ResultSet resultSet = prState.executeQuery();
            if (resultSet.next()) {
                obj = this.match(resultSet);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    //tüm verileri kaşılaştırır
    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_password"));
        obj.setRole(rs.getString("user_role"));
        return obj;
    }
}
