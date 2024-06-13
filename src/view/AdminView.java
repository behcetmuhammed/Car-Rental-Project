package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Halper;
import dao.CarDao;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
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

    private JComboBox<Model.Gear> cmb_booking_gear;
    private JComboBox<Model.Fuel> cmb_booking_fuel;
    private JComboBox<Model.Type> cmb_booking_type;
    private JComboBox cmb_rentals_plate;

    private JButton btn_search_model;
    private JButton btn_cncl_model;
    private JTable tbl_car;
    private JPanel pnl_car;
    private JScrollPane srcl_car;
    private JPanel pnl_booking;
    private JScrollPane srcl_booking;
    private JTable tbl_booking;
    private JTextField fld_fnsh_date;
    private JTextField fld_strt_date;
    private JButton btn_booking_search;
    private JPanel pnl_booking_search;
    private JButton btn_cncl_booking;
    private JPanel pnl_rentals;
    private JScrollPane scrl_rentals;
    private JTable tbl_rentals;
    private JButton btn_srch_rentals_model;
    private JButton btn_cncl_rentals;
    private User user;

    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private DefaultTableModel tmdl_rentals = new DefaultTableModel();

    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private BookManager bookManager;

    private JPopupMenu brand_Menu;
    private JPopupMenu model_Menu;
    private JPopupMenu car_Menu;
    private JPopupMenu book_Menu;
    private JPopupMenu rentals_Menu;

    private Object[] col_model;
    private Object[] col_car;
    private Object[] col_rentals;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz: " + this.user.getUsername());


        //Admin View daki ÇIKIŞ YAP butonu
        loadLogoutComponent();


        //Brand Tab Menu
        loadBrandTable();
        loadBrandComponent();

        //Model Tab Menu
        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        //Car Tab Menu
        loadCarTable();
        loadCarComponent();

        //Book Tab Menu
        loadBookingTable(null);
        loadBookingComponent();
        loadBookingFilter();

        //Rentals Tab Menu
        loadRentalsTable(null);
        loadRentalsComponent();
        loadRentalsFilter();

    }

    //Kiralamaları yükle
    private void loadRentalsTable(ArrayList<Object[]> bookList) {
        col_rentals = new Object[]{"ID", "Plaka", "Araç Marka", "Araç Model", "Müşteri", "Telefon", "Mail", "T.C.", "Başlangıç Tarihi", "Bitiş tarihi", "Fiyat"};
        if (bookList == null) {
            bookList = this.bookManager.getForTable(col_rentals.length, this.bookManager.findAll());
        }
        createTable(this.tmdl_rentals, this.tbl_rentals, col_rentals, bookList);
    }

    // Kiralama Componentlerini yükle
    public void loadRentalsComponent() {
        this.rentals_Menu = new JPopupMenu();
        this.rentals_Menu.add("İptal Et").addActionListener(e -> {
            if (Halper.confirm("sure")) {
                int selectBookId = this.getTableSelectedRow(this.tbl_rentals, 0);

                if (this.bookManager.delete(selectBookId)) {
                    Halper.showMessage("done");
                    loadRentalsTable(null);
                } else {
                    Halper.showMessage("error");
                }
            }
        });

        this.rentals_Menu.setComponentPopupMenu(rentals_Menu);
        //tableRowSelect();
        tableRowSelected(this.tbl_rentals, rentals_Menu);

        btn_srch_rentals_model.addActionListener(e -> {
            ComboItem selectedCar = (ComboItem) this.cmb_rentals_plate.getSelectedItem();
            int carId = 0;
            if (selectedCar != null) {
                carId = selectedCar.getKey();
            }

            ArrayList<Book> bookListBySearch = this.bookManager.searchForTable(carId);
            ArrayList<Object[]> bookRowListBySearch = this.bookManager.getForTable(this.col_rentals.length, bookListBySearch);
            loadRentalsTable(bookRowListBySearch);
        });
        this.btn_cncl_rentals.addActionListener(e -> {
            loadRentalsFilter();
        });
        popMenuExCode(tbl_rentals, rentals_Menu); //benim yazdığım ekstra fonk.
    }


    //Kiralamaları filtreleme işlemi
    public void loadRentalsFilter() {
        this.cmb_rentals_plate.removeAllItems();
        for (Car obj : this.carManager.findAll()) {
            this.cmb_rentals_plate.addItem(new ComboItem(obj.getId(), obj.getPlate()));
        }
        this.cmb_rentals_plate.setSelectedItem(null);
    }


    ///////////////////    ///////////////////    ///////////////////    ///////////////////    ///////////////////


    // Kiralama Componentlerini yükle
    public void loadBookingComponent() {
        tableRowSelect(this.tbl_booking);
        this.book_Menu = new JPopupMenu();
        this.book_Menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(this.tbl_booking, 0);
            BookingView bookingView = new BookingView(
                    this.carManager.getById(selectCarId),
                    this.fld_strt_date.getText(),
                    this.fld_fnsh_date.getText()
            );
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                }
            });
        });

        this.tbl_booking.setComponentPopupMenu(book_Menu);
        popMenuExCode(tbl_booking, book_Menu); //benim yazdığım ekstra fonk.

        btn_booking_search.addActionListener(e -> {
            ArrayList<Car> carList = this.carManager.searchForBooking(
                    fld_strt_date.getText(),
                    fld_fnsh_date.getText(),
                    (Model.Type) cmb_booking_type.getSelectedItem(),
                    (Model.Gear) cmb_booking_gear.getSelectedItem(),
                    (Model.Fuel) cmb_booking_fuel.getSelectedItem()
            );

            ArrayList<Object[]> carBookingRow = this.carManager.getForTable(this.col_car.length, carList);
            loadBookingTable(carBookingRow);
        });
        btn_cncl_booking.addActionListener(e -> {
            loadBookingFilter();
        });


        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
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

        this.btn_cncl_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }


    //Kiralamaları yükle
    public void loadBookingTable(ArrayList<Object[]> carList) {
        Object[] col_booking_list = {"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        createTable(this.tmdl_booking, this.tbl_booking, col_booking_list, carList);
    }


    //Kiralamaları filtreleme işlemi
    public void loadBookingFilter() {
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);
    }


    // Arabaların Componentlerini yükle
    public void loadCarComponent() {
        tableRowSelect(this.tbl_car);
        this.car_Menu = new JPopupMenu();
        this.car_Menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });

        });

        this.car_Menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectModelId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });

        this.car_Menu.add("Sil").addActionListener(e -> {
            if (Halper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(tbl_car, 0);
                if (this.carManager.delete(selectCarId)) {
                    Halper.showMessage("done");
                    loadCarTable();
                } else {
                    Halper.showMessage("error");
                }
            }
        });
        this.tbl_car.setComponentPopupMenu(car_Menu);
        popMenuExCode(tbl_car, car_Menu); //benim yazdığım ekstra fonk.


        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
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

        this.btn_cncl_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    //Arabaları yükle
    public void loadCarTable() {
        this.col_car = new Object[]{"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(this.tmdl_car, this.tbl_car, col_car, carList);
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
                    loadBookingTable(null);
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
            if (selectedBrand != null) {
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

        this.btn_cncl_model.addActionListener(e -> {
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


    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
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
                    loadBookingTable(null);
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
                    loadCarTable();
                    loadBookingTable(null);
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
                    loadCarTable();
                } else {
                    Halper.showMessage("error");
                }
            }
        });

        this.tbl_brand.setComponentPopupMenu(brand_Menu);
        popMenuExCode(tbl_brand, brand_Menu); ////benim yazdığım ekstra metod
    }


    private void createUIComponents() throws ParseException {
        this.fld_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_strt_date.setText("10/06/2023");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("11/06/2024");

    }


    public void loadLogoutComponent() {
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

}
