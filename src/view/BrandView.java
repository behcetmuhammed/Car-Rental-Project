package view;

import business.BrandManager;
import core.Halper;
import entity.Brand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrandView extends LeyoutView {
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel llb_brand_name;
    private JTextField fld_brand_name;
    private JButton btn_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BrandView(Brand brand) {
        this.brandManager = new BrandManager();
        this.brand = brand;

        this.add(container);
        this.guiInitialize(300, 350);

        if (brand != null) {
            fld_brand_name.setText(brand.getName());
        }

        btn_brand_save.addActionListener(e -> {
            if (Halper.isFieldEmpty(this.fld_brand_name)) {
                Halper.showMessage("fill");
            } else {
                boolean result;
                if (this.brand == null) {
                    Brand brand1 = new Brand(fld_brand_name.getText());
                    result = brandManager.save(brand1);
                }else{
                    this.brand.setName(fld_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }
                if (result) {
                    Halper.showMessage("done");
                    dispose();
                } else {
                    Halper.showMessage("error");
                }
            }
        });
    }
}
