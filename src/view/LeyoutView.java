package view;

import core.Halper;
import entity.Brand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LeyoutView extends JFrame {
    public JPanel container;
    public LeyoutView(){}

    //Hazır bir arayüz (View) tasarımı
    public void guiInitialize(int width, int height){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Kapatma düğmesine basıldığında pencerenin kapatılmasını sağlar.
        this.setTitle("Araç Kiralama"); // Pencerenin başlığını "Rent a Car" olarak ayarlar.
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


    public void tableRowSelect(JTable table){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    //Overloading
    public void tableRowSelected(JTable table,JPopupMenu popupMenu) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
    }


    //Bu metod hocadan bağımsız yazdım çünkü kod çalışmıyordu
    public void popMenuExCode(JTable table, JPopupMenu menu){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}

