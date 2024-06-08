package core;

import javax.swing.*;
import java.awt.*;

public class Halper {
    // Tema metodu
    public static void setTheme() {
        String theme = "Nimbus"; //tema
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (theme.equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    //Ekrana mesaj gönderme (gösterme)
    public static void showMessage(String str) {
        optionPaneTR();
        String message;
        String title;

        switch (str) {
            case "fill":
                message = "Lütfen Tüm Alanları doldurunuz.";
                title = "Hata!";
                break;
            case "done":
                message = "İşlem Başarılı";
                title = "Sonuç";
                break;
            case "notFound":
                message = "Kayıt Bulunamadı!";
                title = "Bulunamadı";
                break;
            case "error":
                message = "Hatalı İşlem Yaptınız!";
                title = "Hata";
                break;
            default:
                message = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        //JOptionPane --> kullanıcıya mesajlar göstermek, kullanıcıdan girdi almak veya bir onay almak için kullanılan bir Swing bileşenidir.
    }


    //seçilen alanın boş olup olmadığını kontrol eder
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    //seçilen listedki alanların boş olup olmadığını tek tek kontrol eder
    public static boolean isfieldListEmpty(JTextField[] fieldsList) {
        for (JTextField field : fieldsList) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }


    //Pencerenin ekranın ortasına yerleştirilmesini sağlar.
    public static int getLocationPoint(String type, Dimension size) {
        switch (type) {
            case "x":
                return (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y":
                return (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default:
                return 0;
        }
    }

    public static boolean confirm(String str) {
        optionPaneTR();
        String msg;
        if (str.equals("sure")) {
            msg = "Bu işlemi yapmak istediğine emin misin?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    //İngilizce bazı butonları TÜRKÇE diline çeviren metod
    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

}
