package view;

import core.Halper;
import entity.Brand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LeyoutView extends JFrame {
    public JPanel container;
    public LeyoutView(){}

    //Hazır bir arayüz (View) tasarımı
    public void guiInitialize(int width, int height){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Kapatma düğmesine basıldığında pencerenin kapatılmasını sağlar.
        this.setTitle("Rent A Car"); // Pencerenin başlığını "Rent a Car" olarak ayarlar.
        this.setSize(width, height); // Pencerenin boyutunu 600x500 piksel olarak ayarlar.
        this.setLocation(Halper.getLocationPoint("x", this.getSize()), Halper.getLocationPoint("y", this.getSize())); //Pencerenin ekranın ortasına yerleştirilmesini sağlar (Halper sınıfından getLocationPoint metodunu kullanır)
        this.setVisible(true); //LoginView sınıfından bir nesne oluşturulduğunda pencerenin görünür olmasını sağlar.
    }

    //tablo oluşturan metod
    public void createTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows){
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false); //tablolarda sütunların yerlerini/alanlarını menual olarak değişimini kapatmak
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.setEnabled(false); //tabloların çift tıklanıldığı zaman düzenlenmesini kapatmak

        DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
        clearModel.setRowCount(0);

        if(rows == null){
            rows = new ArrayList<>();
        }

        for(Object[] row : rows){
            model.addRow(row);
        }
    }

    public int getTableSelectedRow(JTable table, int index){
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(), index).toString());
    }
}

