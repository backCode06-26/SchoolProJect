package org.planpal.repository;

import org.planpal.domain.DailyEvent;
import org.planpal.dto.DailyEventDTO;
import org.planpal.utils.DataBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialClob;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class DailyEventRepository {
    private final DataSource dataSource;

    @Autowired
    public DailyEventRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int saveDailyEvent(DailyEvent dailyEvent) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int eventId = 0;

        try {
            conn = dataSource.getConnection();

            // 시퀀스 값 가져오기
            String seqSql = "SELECT daily_events_seq.nextval FROM dual";
            ps = conn.prepareStatement(seqSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                eventId = rs.getInt(1);
            }
            rs.close();  // ResultSet 닫기
            ps.close();  // PreparedStatement 닫기

            // 이벤트 삽입
            String insertSql = "INSERT INTO daily_events (event_id, user_id, group_id, title, description, category, event_date, location) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, eventId);
            ps.setInt(2, dailyEvent.getUserId());
            ps.setInt(3, dailyEvent.getGroupId());
            ps.setString(4, dailyEvent.getTitle());
            ps.setString(5, dailyEvent.getDescription());
            ps.setString(6, dailyEvent.getCategory());

            // eventDate가 DATE 타입일 경우 적절히 처리
            ps.setDate(7, new java.sql.Date(dailyEvent.getEventDate().getTime()));

            ps.setString(8, dailyEvent.getLocation());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving daily event", e);
        } finally {
            // 자원 해제
            DataBaseUtils.close(conn, ps, rs);
        }
        return eventId;
    }

    public void deleteDailyEvent(int groupId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "DELETE FROM daily_events WHERE group_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, groupId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting daily event", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    public ArrayList<DailyEventDTO> getDailyEventsByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DailyEventDTO> dailyEvents = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM daily_events WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            dailyEvents = extractDailyEvents(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving daily events by user ID", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return dailyEvents;
    }

    public ArrayList<DailyEventDTO> getDailyEventsByGroupId(int groupId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DailyEventDTO> dailyEvents = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM daily_events WHERE group_id = ? ORDER BY EVENT_DATE ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, groupId);

            rs = ps.executeQuery();
            dailyEvents = extractDailyEvents(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving daily events by group ID", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return dailyEvents;
    }

    public ArrayList<DailyEventDTO> getAllDailyEventsWithPagination(int page) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DailyEventDTO> dailyEvents = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            int startRow = (page - 1) * 10 + 1;
            int endRow = page * 10;

            String sql = "SELECT * FROM ( " +
                    "    SELECT ROWNUM NUM, D.* FROM ( " +
                    "       SELECT * FROM daily_events ORDER BY event_id DESC" +
                    "   ) D " +
                    ") WHERE NUM BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, startRow);
            ps.setInt(2, endRow);

            rs = ps.executeQuery();
            dailyEvents = extractDailyEvents(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all daily events with pagination", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return dailyEvents;
    }

    private ArrayList<DailyEventDTO> extractDailyEvents(ResultSet rs) throws SQLException {
        ArrayList<DailyEventDTO> dailyEvents = new ArrayList<>();
        while (rs.next()) {
            DailyEventDTO dailyEvent = new DailyEventDTO();
            dailyEvent.setEventId(rs.getInt("event_id"));
            dailyEvent.setUserId(rs.getInt("user_id"));
            dailyEvent.setGroupId(rs.getInt("group_id"));
            dailyEvent.setTitle(rs.getString("title"));
            dailyEvent.setDescription(rs.getString("description"));
            dailyEvent.setCategory(rs.getString("category"));
            dailyEvent.setEventDate(rs.getDate("event_date"));
            dailyEvent.setLocation(rs.getString("location"));
            dailyEvent.setCreatedAt(rs.getTimestamp("created_at"));
            dailyEvent.setUpdatedAt(rs.getTimestamp("updated_at"));
            dailyEvents.add(dailyEvent);
        }
        return dailyEvents;
    }
}
