package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Halper;
import entity.Brand;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends LeyoutView {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JComboBox<ComboItem> cmb_s_model_brand;
    private JComboBox<Model.Type> cmb_s_model_type;  //Burayı daha sonra kontor et
    private JComboBox<Model.Fuel> cmb_s_model_fuel;   //Burayı daha sonra kontor et
    private JComboBox<Model.Gear> cmb_s_model_gear;   //Burayı daha sonra kontor et
    private JButton btn_search_model;
    private JButton btn_cncl_model;
    private JTable table1;
    private JPanel pnl_car;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_Menu;
    private JPopupMenu model_Menu;
    private Object[] col_model;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;

        if (this.user == null) {
            dispose();
        }

        this.lbl_welcome.setText("Hoşgeldiniz: " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

    }


    // Modellerin Componentlerini yükle
    public void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_Menu = new JPopupMenu();
        this.model_Menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });

        });

        this.model_Menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });

        this.model_Menu.add("Sil").addActionListener(e -> {
            if (Halper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Halper.showMessage("done");
                    loadModelTable(null);
                } else {
                    Halper.showMessage("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(model_Menu);
        popMenuExCode(tbl_model, model_Menu); //benim yazdığım ekstra fonk.


        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if(selectedBrand != null){
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem()
            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_cncl_model.addActionListener(e ->{
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }


    //Modelleri yükle
    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Model Markası", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }
        createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }


    //Brandleri yükle
    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }


    public void loadModelFilter() {
        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }

    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }


    // Markaların Componentlerini yükle
    public void loadBrandComponent() {
        tableRowSelect(this.tbl_brand);
        this.brand_Menu = new JPopupMenu();

        this.brand_Menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });

        this.brand_Menu.add("Güncelle").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });

        this.brand_Menu.add("Sil").addActionListener(e -> {
            if (Halper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Halper.showMessage("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                } else {
                    Halper.showMessage("error");
                }
            }
        });

        this.tbl_brand.setComponentPopupMenu(brand_Menu);
        popMenuExCode(tbl_brand, brand_Menu); ////benim yazdığım ekstra metod
    }


}
