package view;

import business.UserManager;
import core.Halper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends LeyoutView {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;

    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();

        this.add(container); // LoginView nesnesi oluşturulduğunda, bir JPanel olan container bileşenini JFrame'e ekler.
        this.guiInitialize(600, 400);

        btn_login.addActionListener(e -> { //login ekranındaki butona basıldığı zaman
            JTextField[] checkFieldList = {this.fld_username, this.fld_password};
            if (Halper.isfieldListEmpty(checkFieldList)) { //eğer login ekranındaki username (kullanıcı adı) veya password boşsa, ekranda showMessage göster
                Halper.showMessage("fill");
            } else { //eğer login ekranındaki username (kullanıcı adı) ve password alanları doluysa
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_password.getText());
                if(loginUser == null){
                    Halper.showMessage("notFound");
                }else{
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }
            }
        });
    }
}
