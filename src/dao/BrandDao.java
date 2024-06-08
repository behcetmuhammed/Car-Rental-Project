package dao;


import core.DbConnect;
import entity.Brand;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class BrandDao {
    private Connection connection;

    public BrandDao() {
        this.connection = DbConnect.getInstance();
    }

    //veritabanındaki tüm Brand verilerini çekmek
    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand ORDER BY brand_id ASC";
        try {
            Statement stateM = this.connection.createStatement(); //Burda sql srogumuzda ? ihtiyacımız olmadığı için PreparedStatement yerine Statement bize yeterli.
            ResultSet rS = stateM.executeQuery(sql);

            while (rS.next()) {
                brandList.add(this.match(rS));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return brandList;
    }

    //tüm verileri kaşılaştırır
    public Brand match(ResultSet rs) throws SQLException {
        Brand obj = new Brand();
        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));

        return obj;
    }

    //save işlemi yapan metod
    public boolean save(Brand brand) {
        String query = "INSERT INTO public.brand (brand_name) VALUES (?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, brand.getName());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Brand getById(int id) {
        Brand obj = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ?";

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

    //Güncelle PopUp
    public boolean update(Brand brand) {
        String query = "UPDATE public.brand SET brand_name = ? WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, brand.getName());
            pr.setInt(2, brand.getId());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    //Sil PopUp
    public boolean delete(int id) {
        String query = "DELETE FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


}
