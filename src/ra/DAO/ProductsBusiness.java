package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Products;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsBusiness implements DAOInterface<Products> {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Products mapProduct(ResultSet rs) throws SQLException {
        Products product = new Products();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setStock(rs.getInt("stock"));
        product.setCostPrice(rs.getDouble("cost_price"));
        product.setSellingPrice(rs.getDouble("selling_price"));
        product.setCreatedAt(rs.getDate("created_at").toLocalDate());
        product.setCategoryId(rs.getInt("category_id"));

        return product;
    }

    @Override
    public void insert(Products products) {
        if (products == null) {
            System.out.println("Invalid product information");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call create_product(?,?,?,?,?,?)}");
            callSt.setString(1, products.getProductName());
            callSt.setInt(2, products.getStock());
            callSt.setDouble(3, products.getCostPrice());
            callSt.setDouble(4, products.getSellingPrice());
            callSt.setDate(5, java.sql.Date.valueOf(products.getCreatedAt()));
            callSt.setInt(6, products.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Product created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Products products) {
        if (products == null) {
            System.out.println("Invalid product information");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call update_product(?,?,?,?,?,?,?)}");
            callSt.setInt(1, products.getProductId());
            callSt.setString(2, products.getProductName());
            callSt.setInt(3, products.getStock());
            callSt.setDouble(4, products.getCostPrice());
            callSt.setDouble(5, products.getSellingPrice());
            callSt.setDate(6, java.sql.Date.valueOf(products.getCreatedAt()));
            callSt.setInt(7, products.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Product updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Products products) {
        if (products == null || products.getProductId() <= 0) {
            System.out.println("Invalid product information");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call delete_product(?)}");
            callSt.setInt(1, products.getProductId());
            callSt.executeUpdate();
            System.out.println("Product deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Products get(int id) {
        if (id <= 0) {
            System.out.println("Invalid product ID");
            return null;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_product_by_id(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Products get(String name) {
        return null;
    }

    @Override
    public Products get(Products products) {
        return null;
    }

    @Override
    public Products[] getAll() {
        List<Products> products = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_all_products()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
            return products.toArray(new Products[0]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return new Products[0];
    }


}