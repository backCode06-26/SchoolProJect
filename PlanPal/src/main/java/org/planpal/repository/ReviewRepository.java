package org.planpal.repository;

import org.planpal.domain.Review;
import org.planpal.dto.ReviewDTO;
import org.planpal.utils.DataBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
    private final DataSource dataSource;

    @Autowired
    public ReviewRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 댓글 저장
    public void saveReview(Review review) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            // 리뷰 삽입
            String sql = "INSERT INTO REVIEWS (REVIEW_ID, GROUP_ID, USER_ID, RATING, REVIEW, CREATED_AT) " +
                    "VALUES (reviews_seq.NEXTVAL, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getGroupId());
            ps.setInt(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReview());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving review", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
    }

    // 리뷰 수정
    public void updateReview(Review review) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            String sql = "UPDATE REVIEWS SET RATING = ?, REVIEW = ? WHERE REVIEW_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getReview());
            ps.setInt(3, review.getReviewId());

            System.out.println(review.getReviewId());
            System.out.println(review.getReview());
            System.out.println(review.getRating());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating review", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    // 리뷰 삭제
    public void deleteReview(int reviewId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting review", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    // 그룹 ID로 리뷰 가져오기
    public List<ReviewDTO> getReviewsByGroupId(int groupId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReviewDTO> reviews = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM reviews WHERE group_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, groupId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setReviewId(rs.getInt("REVIEW_ID"));
                reviewDTO.setGroupId(rs.getInt("GROUP_ID"));
                reviewDTO.setUserId(rs.getInt("USER_ID"));
                reviewDTO.setRating(rs.getInt("RATING"));
                reviewDTO.setReview(rs.getString("REVIEW"));
                reviewDTO.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                reviews.add(reviewDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting reviews by groupId", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return reviews;
    }

    // 상위 1개의 그룹 ID를 평균 평점 기준으로 가져오기
    public int findTopGroupIdByAverageRating() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int topGroupId = -1;

        try {
            conn = dataSource.getConnection();

            // 상위 1개의 그룹 ID를 가져오는 쿼리
            String sql = "SELECT * FROM ( " +
                    "    SELECT GROUP_ID " +
                    "    FROM REVIEWS " +
                    "    GROUP BY GROUP_ID " +
                    "    ORDER BY AVG(RATING) DESC " +
                    ") " +
                    "WHERE ROWNUM = 1";


            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                topGroupId = rs.getInt("GROUP_ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting top group ID by average rating", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return topGroupId;
    }
}
