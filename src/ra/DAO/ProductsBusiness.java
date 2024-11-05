package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Products;

import java.sql.*;
import java.time.LocalDate;
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
            Date currentDate = Date.valueOf(LocalDate.now());
            callSt.setDate(6, currentDate);
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

    public List<Products> getProductsByCreatedAtDesc() {
        List<Products> products = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_products_by_created_at_desc()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    public List<Products> searchProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            System.out.println("Invalid price range");
            return null;
        }
        List<Products> products = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call search_products_by_price_range(?,?)}");
            callSt.setDouble(1, minPrice);
            callSt.setDouble(2, maxPrice);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    public List<Products> getTop3ProfitableProducts() {
        List<Products> products = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_top_3_profitable_products()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }


}
