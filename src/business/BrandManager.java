package business;

import core.Halper;
import dao.BrandDao;
import dao.UserDao;
import entity.Brand;
import entity.User;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);
        }
        return brandRowList;
    }

    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Halper.showMessage("error");
        }
        return this.brandDao.save(brand);
    }

    public Brand getById(int id) {
        return brandDao.getById(id);
    }

    public boolean update(Brand brand) {
        if (this.getById(brand.getId()) == null) {
            Halper.showMessage("notFound");
        }
        return this.brandDao.update(brand);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Halper.showMessage(id + " ID Kayıtlı Marka Bulunamadı!");
            return false;
        }
        return this.brandDao.delete(id);
    }

}
