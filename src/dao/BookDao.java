package dao;

import core.DbConnect;
import entity.Car;
import entity.Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import entity.Book;

public class BookDao {
    private Connection connection;
    private final CarDao carDao;

    public BookDao() {
        this.connection = DbConnect.getInstance();
        this.carDao = new CarDao();
    }

    public boolean delete(int book_id) {
        String query = "DELETE FROM public.book WHERE book_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, book_id);
            return pr.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //veritabanındaki tüm araba verilerini çekmek
    public ArrayList<Book> findAll() {
        String sql = "SELECT * FROM public.book ORDER BY book_id ASC";
        return this.selectByQuery(sql);
    }

    //Sorgu Seçmek
    public ArrayList<Book> selectByQuery(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                books.add(this.match(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    //save işlemi yapan metod
    public boolean save(Book book) {
        String query = "INSERT INTO public.book " +
                "(" +
                "book_car_id," +
                "book_name," +
                "book_idno," +
                "book_mpno," +
                "book_mail," +
                "book_strt_date," +
                "book_fnsh_date," +
                "book_prc," +
                "book_case," +
                "book_note" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, book.getCar_id());
            pr.setString(2, book.getName());
            pr.setString(3, book.getIdno());
            pr.setString(4, book.getMpno());
            pr.setString(5, book.getMail());
            pr.setDate(6, Date.valueOf(book.getStrt_date()));
            pr.setDate(7, Date.valueOf(book.getFnsh_date()));
            pr.setInt(8, book.getPrc());
            pr.setString(9, book.getbCase());
            pr.setString(10, book.getNote());
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    //tüm verileri kaşılaştırır
    public Book match(ResultSet rs) throws SQLException {
        Book obj = new Book();

        obj.setId(rs.getInt("book_id"));
        obj.setbCase(rs.getString("book_case"));
        obj.setCar_id(rs.getInt("book_car_id"));
        obj.setName(rs.getString("book_name"));
        obj.setStrt_date(LocalDate.parse(rs.getString("book_strt_date")));
        obj.setFnsh_date(LocalDate.parse(rs.getString("book_fnsh_date")));
        obj.setCar(this.carDao.getById(rs.getInt("book_car_id")));
        obj.setIdno(rs.getString("book_idno"));
        obj.setMpno(rs.getString("book_mpno"));
        obj.setMail(rs.getString("book_mail"));
        obj.setNote(rs.getString("book_note"));
        obj.setPrc(rs.getInt("book_prc"));
        return obj;
    }

    public Book getById(int id) {
        Book book = null;
        String query = "SELECT * FROM public.book WHERE book_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                book = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

}
