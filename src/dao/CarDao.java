package dao;

import core.DbConnect;
import entity.Car;
import entity.Model;

import java.sql.*;
import java.util.ArrayList;

public class CarDao {
    private Connection connection;
    private final BrandDao brandDao;
    private final ModelDao modelDao;

    public CarDao() {
        this.connection = DbConnect.getInstance();
        this.brandDao = new BrandDao();
        this.modelDao = new ModelDao();
    }

    //ID yi getir
    public Car getById(int id) {
        Car obj = null;
        String query = "SELECT * FROM public.car WHERE car_id = ?";

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

    //veritabanındaki tüm araba verilerini çekmek
    public ArrayList<Car> findAll() {
        String sql = "SELECT * FROM public.car ORDER BY car_id ASC";
        return this.selectByQuery(sql);
    }


    //Sorgu Seçmek
    public ArrayList<Car> selectByQuery(String query) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                cars.add(this.match(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    //tüm verileri kaşılaştırır
    public Car match(ResultSet rs) throws SQLException {
        Car obj = new Car();

        obj.setId(rs.getInt("car_id"));
        obj.setModel_id(rs.getInt("car_model_id"));
        obj.setColor(Car.Color.valueOf(rs.getString("car_color")));
        obj.setKm(rs.getInt("car_km"));
        obj.setPlate(rs.getString("car_plate"));
        //obj.setModel(this.modelDao.getById(obj.getModel_id()));
        // Model nesnesini yüklemek için
        Model model = modelDao.getById(obj.getModel_id());
        obj.setModel(model);
        return obj;
    }

    //Güncelle PopUp
    public boolean update(Car car) {
        String query = "UPDATE public.car SET " +
                "car_model_id = ?, " +
                "car_color = ?, " +
                "car_km = ?, " +
                "car_plate = ? " +
                "WHERE car_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, car.getModel_id());
            pr.setString(2, car.getColor().toString());
            pr.setInt(3, car.getKm());
            pr.setString(4, car.getPlate());
            pr.setInt(5, car.getId());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    //save işlemi yapan metod
    public boolean save(Car car) {
        String query = "INSERT INTO public.car " +
                "(" +
                "car_model_id," +
                "car_color," +
                "car_km," +
                "car_plate" +
                ")" +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, car.getModel_id());
            pr.setString(2, car.getColor().toString());
            pr.setInt(3, car.getKm());
            pr.setString(4, car.getPlate());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    //Sil PopUp
    public boolean delete(int carId) {
        String query = "DELETE FROM public.car WHERE car_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, carId);
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }



}
