 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.responsi.model;

/**
 *
 * @author Lab Informatika
 */

import com.pbo.responsi.db.DatabaseConnection;
import com.pbo.responsi.dto.CartItemDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepositoryMySQL implements CartRepository {
    private final DatabaseConnection conn;
    
    public  CartRepositoryMySQL() {
        this.conn = DatabaseConnection.getInstance();
    }
    
    @Override
    public List<CartItemDTO> findAll() {
        List<CartItemDTO> items = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM cart ORDER BY name";
            
            Connection connection = this.conn.getConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CartItemDTO item = new CartItemDTO(
                        rs.getString("name"), 
                        rs.getDouble("price"), 
                        rs.getInt("quantity"));
                items.add(item);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return items;
    }
    
    @Override
    public void save(CartItemDTO item) {
        try {
            String sql = "INSERT INTO cart (name, price, quantity) VALUES (?, ?, ?)";
            
            Connection connection = this.conn.getConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    
    @Override
    public void updateQuantity(String name, int newQty) {
        try {
            String sql = "UPDATE cart SET quantity = ? WHERE name = ?";
            
            Connection connection = this.conn.getConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, newQty);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    
    @Override
    public void delete(String name) {
        try {
            String sql = "DELETE FROM cart WHERE name = ?";
            
            Connection connection = this.conn.getConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
