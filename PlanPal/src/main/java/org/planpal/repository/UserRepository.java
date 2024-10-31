package org.planpal.repository;

import org.planpal.domain.User;
import org.planpal.dto.UserDTO;
import org.planpal.eunm.UserType;
import org.planpal.utils.DataBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    private final DataSource dataSource;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDTO findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "select * from users where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            return getUserDTO(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
    }

    public UserDTO findUserByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM users WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            return getUserDTO(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by userId", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
    }


    public void saveUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            String sql = "insert into users (user_id, username, email, password) " +
                    "values (users_seq.nextval, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    public void deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            String sql = "delete from users where user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    public void changePassword(User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            String sql = "update users set password = ? where user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error changing user password", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    private UserDTO getUserDTO(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(rs.getInt("user_id"));
            userDTO.setUsername(rs.getString("username"));
            userDTO.setEmail(rs.getString("email"));
            userDTO.setPassword(rs.getString("password"));
            userDTO.setCreatedAt(rs.getTimestamp("created_at"));
            userDTO.setUserType(UserType.valueOf(rs.getString("user_type")));
            return userDTO;
        } else {
            return null;
        }
    }
}
