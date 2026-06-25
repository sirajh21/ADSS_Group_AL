package Backend.DataAccessLayer;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


import Backend.BusinessLayer.Stock.controllers.*;



public class ReportDAO {
    private static ReportDAO instance = null;
    private Connection connection;

    private ReportDAO() {
        this.connection = connect();
    }

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    private Connection connect() {
        String path = Paths.get("").toAbsolutePath().toString();
        String _connectionString = "jdbc:sqlite:" + path + "/dev/superli.db";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(_connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(ReportDTO reportDTO)  {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO Report(ID,PID,AMOUNT) VALUES (?, ?, ?)";
                try {
                    pstmt = connection.prepareStatement(sql);
               
                pstmt.setInt(1, reportDTO.getId());
                pstmt.setInt(2, reportDTO.getPid());
                pstmt.setInt(3, reportDTO.getAmount());
                pstmt.executeUpdate();
            }
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
    public void LoadData() throws SQLException{
        for (ReportDTO ReportDTO : getReports()) {
            ReportController.getInstance().ADDdtoreport(ReportDTO);
            
        }
    }
    public List<ReportDTO> getReports() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReportDTO> Reports = new LinkedList<ReportDTO>() { };

        try {
            pstmt = connection.prepareStatement("SELECT * FROM Report");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Reports.add(mapRowToReport(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        return Reports;
    }
    public ReportDTO mapRowToReport(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        int PID = rs.getInt("PID");
        int amount = rs.getInt("AMOUNT");
       

        return new ReportDTO(id, PID, amount);
    }
    public void deleteData() throws SQLException{
        PreparedStatement pstmt = null;
    try {
        pstmt = connection.prepareStatement("DELETE FROM Report");
        pstmt.executeUpdate();
    } finally {
        if (pstmt != null) {
            pstmt.close();
        }
    }
}
public void delete(int id) throws SQLException {
    PreparedStatement pstmt = null;
    try {
        pstmt = connection.prepareStatement("DELETE FROM Report WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    } finally {
        if (pstmt != null) {
            pstmt.close();
        }
    }
}}
