package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerBusiness implements DAOInterface<Customer> {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Customer mapCustomer(ResultSet rs) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customerId"));
        customer.setCustomerName(rs.getString("customerName"));
        customer.setPhoneNumber(rs.getString("phoneNumber"));
        customer.setIdCard(rs.getString("idCard"));
        customer.setAddress(rs.getString("address"));
        customer.setCustomerType(rs.getString("customerType"));
        return customer;
    }

    @Override
    public void insert(Customer customer) {
        if (customer == null || !isValidCustomer(customer)) {
            System.out.println("Invalid customer information");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call addCustomer(?, ?, ?, ?, ?)}");
            callSt.setString(1, customer.getCustomerName());
            callSt.setString(2, customer.getPhoneNumber());
            callSt.setString(3, customer.getIdCard());
            callSt.setString(4, customer.getAddress());
            callSt.setString(5, customer.getCustomerType());
            callSt.executeUpdate();
            System.out.println("Customer added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Customer customer) {
        if (customer == null || !isValidCustomer(customer)) {
            System.out.println("Invalid customer information");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call updateCustomer(?, ?, ?, ?, ?, ?)}");
            callSt.setInt(1, customer.getCustomerId());
            callSt.setString(2, customer.getCustomerName());
            callSt.setString(3, customer.getPhoneNumber());
            callSt.setString(4, customer.getIdCard());
            callSt.setString(5, customer.getAddress());
            callSt.setString(6, customer.getCustomerType());
            callSt.executeUpdate();
            System.out.println("Customer updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Customer customer) {
        if (customer == null || customer.getCustomerId() <= 0) {
            System.out.println("Invalid customer information");
            return;
        }

        try {
            if (get(customer.getCustomerId()) == null) {
                System.out.println("Customer not found");
                return;
            }
            openConnection();

            callSt = conn.prepareCall("{call deleteCustomer(?)}");
            callSt.setInt(1, customer.getCustomerId());
            callSt.executeUpdate();
            System.out.println("Customer deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Customer get(int id) {
        if (id <= 0) {
            System.out.println("Invalid customer ID");
            return null;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call getCustomerById(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapCustomer(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Customer get(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Invalid customer name");
            return null;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call searchCustomerByName(?)}");
            callSt.setString(1, name);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapCustomer(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Customer get(Customer customer) {
        return null;
    }

    @Override
    public Customer[] getAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call getAllCustomers()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return customers.toArray(new Customer[0]);
    }


    public Customer[] searchCustomerByCondition(String condition) {
        List<Customer> customers = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call searchCustomer(?)}");
            callSt.setString(1, condition);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return customers.toArray(new Customer[0]);
    }

    private boolean isValidCustomer(Customer customer) {
        return customer.getCustomerName() != null && !customer.getCustomerName().trim().isEmpty() &&
                customer.getPhoneNumber() != null && !customer.getPhoneNumber().trim().isEmpty() &&
                customer.getIdCard() != null && !customer.getIdCard().trim().isEmpty() &&
                customer.getAddress() != null && !customer.getAddress().trim().isEmpty() &&
                customer.getCustomerType() != null && !customer.getCustomerType().trim().isEmpty();
    }
}
