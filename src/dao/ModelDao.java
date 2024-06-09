package dao;

import core.DbConnect;
import entity.Model;

import java.sql.*;
import java.util.ArrayList;

public class ModelDao {
    private Connection connection;
    private final BrandDao brandDao = new BrandDao();

    public ModelDao() {
        this.connection = DbConnect.getInstance();
    }

    //ID yi getir
    public Model getById(int id) {
        Model obj = null;
        String query = "SELECT * FROM public.model WHERE model_id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    //veritabanındaki tüm Model verilerini çekmek
    public ArrayList<Model> findAll() {
        String sql = "SELECT * FROM public.model ORDER BY model_id ASC";
        return this.selectByQuery(sql);
    }

    public ArrayList<Model> getByListBrandId(int brandId) {
        return this.selectByQuery("SELECT * FROM public.model WHERE model_brand_id = " + brandId);
    }

    //Sorgu Seçmek
    public ArrayList<Model> selectByQuery(String query) {
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                modelList.add(this.match(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return modelList;
    }

    //save işlemi yapan metod
    public boolean save(Model model) {
        String query = "INSERT INTO public.model " +
                "(" +
                "model_brand_id," +
                "model_name," +
                "model_type," +
                "model_year," +
                "model_fuel," +
                "model_gear" +
                ")" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, model.getBrand_id());
            pr.setString(2, model.getName());
            pr.setString(3, model.getType().toString());
            pr.setString(4, model.getYear());
            pr.setString(5, model.getFuel().toString());
            pr.setString(6, model.getGear().toString());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    //Güncelle PopUp
    public boolean update(Model model) {
        String query = "UPDATE public.model SET " +
                "model_brand_id = ?, " +
                "model_name = ?, " +
                "model_type = ?, " +
                "model_year = ?, " +
                "model_fuel = ?, " +
                "model_gear = ? " +
                "WHERE mode_id (?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, model.getBrand_id());
            pr.setString(2, model.getName());
            pr.setString(3, model.getType().toString());
            pr.setString(4, model.getYear());
            pr.setString(5, model.getFuel().toString());
            pr.setString(6, model.getGear().toString());
            pr.setInt(7, model.getId());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    //Sil PopUp
    public boolean delete(int model_id) {
        String query = "DELETE FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, model_id);
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    //tüm verileri kaşılaştırır
    public Model match(ResultSet rs) throws SQLException {
        Model obj = new Model();

        obj.setId(rs.getInt("model_id"));
        obj.setName(rs.getString("model_name"));
        obj.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
        obj.setGear(Model.Gear.valueOf(rs.getString("model_gear")));
        obj.setType(Model.Type.valueOf(rs.getString("model_type")));
        obj.setYear(rs.getString("model_year"));
        obj.setBrand(this.brandDao.getById(rs.getInt("model_brand_id")));
        obj.setBrand_id(rs.getInt("model_brand_id"));

        return obj;
    }
}
