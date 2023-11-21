package mtc.dbs.rollback.repository;

import lombok.extern.slf4j.Slf4j;
import mtc.dbs.rollback.db.DBConnectionUtil;
import mtc.dbs.rollback.dto.LogDto;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Slf4j
@Repository
public class LogRepository {
    public int getMaxSno(String gid) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select coalesce(max(sno), 0) max from sdm_log_mas where gid = ?";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, gid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                return rs.getInt("max");
            }
            return 0;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public LogDto insertLogMas(LogDto dto) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        String sql1 = "insert into sdm_log_mas values(?, ?, ?, ?, ?);";
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, dto.getGid());
            pstmt.setInt(2, dto.getSno());
            pstmt.setString(3, dto.getSvcid());
            pstmt.setString(4, dto.getInputJson());
            pstmt.setString(5, dto.getOutputJson());
            pstmt.executeUpdate();

            return dto;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }


    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
