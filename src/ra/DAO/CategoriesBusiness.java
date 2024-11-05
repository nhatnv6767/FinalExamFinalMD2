package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Categories;
import ra.entity.CategoryStatistics;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriesBusiness implements DAOInterface<Categories> {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Categories mapCategory(ResultSet rs) throws Exception {
        Categories category = new Categories();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryStatus(rs.getBoolean("category_status"));
        return category;
    }

    @Override
    public void insert(Categories categories) {
        if (categories == null || !isValidCustomer(categories)) {
            System.out.println("Invalid category information");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call create_category(?)}");
            callSt.setString(1, categories.getCategoryName());
            callSt.executeUpdate();
            System.out.println("Category created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Categories categories) {
        if (categories == null || !isValidCustomer(categories)) {
            System.out.println("Invalid category information");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call update_category(?, ?)}");
            callSt.setInt(1, categories.getCategoryId());
            callSt.setString(2, categories.getCategoryName());
            callSt.executeUpdate();
            System.out.println("Category updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Categories categories) {
        if (categories == null || categories.getCategoryId() <= 0) {
            System.out.println("Invalid category information");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call delete_category(?)}");
            callSt.setInt(1, categories.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Category deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Categories get(int id) {
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_category_by_id(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Categories get(String name) {
        return null;
    }

    @Override
    public Categories get(Categories categories) {
        return null;
    }

    @Override
    public Categories[] getAll() {
        List<Categories> categories = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_all_categories()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories.toArray(new Categories[0]);
    }

    public List<CategoryStatistics> getProductsByCategory() {
        List<CategoryStatistics> statistics = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call count_products_by_category()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                CategoryStatistics stat = new CategoryStatistics();
                stat.setCategoryName(rs.getString("category_name"));
                stat.setProductCount(rs.getInt("number_of_products"));
                statistics.add(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return statistics;
    }


    private boolean isValidCustomer(Categories category) {
        return category.getCategoryName() != null && !category.getCategoryName().trim().isEmpty()
                ;
    }
}
