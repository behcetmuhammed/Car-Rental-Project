import business.UserManager;
import core.DbConnect;
import core.Halper;
import entity.User;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Halper.setTheme(); //tema seçimi

        UserManager userManager = new UserManager();
        //LoginView loginView = new LoginView();
        AdminView adminView = new AdminView(userManager.findByLogin("emir", "1234"));

    }
}
