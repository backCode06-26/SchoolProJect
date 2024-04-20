package com.recycle.repository;

import com.recycle.domain.Member;
import com.recycle.domain.UserDTO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public DataMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 정보 저장하기
    @Override
    public void save(Member member) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결을 위한 DataSource를 사용하여 Connection을 가져옴
            conn = dataSource.getConnection();

            String sql = "INSERT INTO Member (id, email, password, joinDate) VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // 이거는 회원가입에서 받은 정보로 할거임
            pstmt.setInt(1, 1);
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            // 쿼리 실행
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // id 조회
    @Override
    public UserDTO getUserInfo(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT M.email, M.joinDate, G.gameScore " +
                         "FROM member M INNER JOIN game G ON M.id = G.id " +
                         "WHERE M.id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDTO mg = new UserDTO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("joinDate"));
                mg.setGameScore(rs.getInt("gameScore"));
                return mg;
            } else {
                throw new SQLException("조회 실패");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // email으로 조회하기
    @Override
    public Optional<Member> findEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT email, password" +
                         "FROM member M" +
                         "where email= ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 몇개의 정보 조회하기
    @Override
    public List<UserDTO> getAllUserInfo(int page, int size) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<UserDTO> memberGameInfos = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            // paging을 위한 쿼리 수정
            String sql = "SELECT M.email, M.joinDate, G.gameScore " +
                    "FROM member M INNER JOIN game G ON M.id = G.id " +
                    "ORDER BY M.id " +
                    "LIMIT ? OFFSET ?";

            // 페이지 번호와 페이지 크기를 이용하여 offset 계산
            int offset = (page - 1) * size;

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserDTO mg = new UserDTO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("joinDate"));
                mg.setGameScore(rs.getInt("gameScore"));
                memberGameInfos.add(mg);
            }
            return memberGameInfos;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
