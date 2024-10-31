package org.planpal.repository;

import org.planpal.domain.EventGroup;
import org.planpal.dto.EventGroupDTO;
import org.planpal.utils.DataBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventGroupRepository {
    private final DataSource dataSource;
    private static final int PAGE_SIZE = 16;

    @Autowired
    public EventGroupRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int saveEventGroup(EventGroup eventGroup) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int groupId = -1;

        try {
            conn = dataSource.getConnection();

            String seqSql = "SELECT event_groups_seq.NEXTVAL AS group_id FROM dual";
            ps = conn.prepareStatement(seqSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                groupId = rs.getInt("group_id");
            }

            String sql = "INSERT INTO event_groups (group_id, user_id, group_name, description, category, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, NULL)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, groupId);
            ps.setLong(2, eventGroup.getUserId());
            ps.setString(3, eventGroup.getGroupName());
            ps.setString(4, eventGroup.getDescription());
            ps.setString(5, eventGroup.getCategory());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving event group", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return groupId;
    }

    public void updateEventGroup(EventGroup eventGroup) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            String sql = "UPDATE event_groups SET group_name = ?, description = ?, updated_at = CURRENT_TIMESTAMP " +
                    "WHERE group_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, eventGroup.getGroupName());
            ps.setString(2, eventGroup.getDescription());
            ps.setLong(3, eventGroup.getGroupId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating event group", e);
        } finally {
            DataBaseUtils.close(conn, ps, null);
        }
    }

    public void deleteEventGroup(int groupId) {
        Connection conn = null;
        PreparedStatement event = null;
        PreparedStatement group = null;

        try {
            conn = dataSource.getConnection();

            String deleteGroupEventsSql = "DELETE FROM daily_events WHERE group_id = ?";
            event = conn.prepareStatement(deleteGroupEventsSql);
            event.setInt(1, groupId);
            event.executeUpdate();

            String deleteGroupSql = "DELETE FROM event_groups WHERE group_id = ?";
            group = conn.prepareStatement(deleteGroupSql);
            group.setInt(1, groupId);
            group.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting event group", e);
        } finally {
            DataBaseUtils.close(conn, event, null);
            DataBaseUtils.close(conn, group, null);
        }
    }

    public int getUserIdByGroupId(int groupId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int userId = -1;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT user_id FROM event_groups WHERE group_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, groupId);
            rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user ID by group ID", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }
        return userId;
    }

    public List<EventGroupDTO> getEventGroupsByEventIdDesc(int userId, int page) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EventGroupDTO> eventGroups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            int startRow = (page - 1) * PAGE_SIZE + 1;
            int endRow = page * PAGE_SIZE;

            String sql = "SELECT * FROM ( " +
                    "    SELECT ROWNUM AS num, eg.* FROM ( " +
                    "        SELECT * FROM event_groups WHERE user_id = ? " +
                    "        ORDER BY group_id DESC" +
                    "    ) eg " +
                    ") WHERE num BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, startRow);
            ps.setInt(3, endRow);
            rs = ps.executeQuery();

            eventGroups = extractEventGroup(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event groups sorted by eventId desc with pagination", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return eventGroups;
    }

    public List<EventGroupDTO> getEventGroupsByCreatedAtDesc(int page) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EventGroupDTO> eventGroups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            int startRow = (page - 1) * PAGE_SIZE + 1;
            int endRow = page * PAGE_SIZE;

            String sql = "SELECT * FROM ( " +
                    "    SELECT ROWNUM AS num, eg.* FROM ( " +
                    "        SELECT * FROM event_groups " +
                    "        ORDER BY created_at DESC" +
                    "    ) eg " +
                    ") WHERE num BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, startRow);
            ps.setInt(2, endRow);
            rs = ps.executeQuery();

            eventGroups = extractEventGroup(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event groups sorted by created_at desc with pagination", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return eventGroups;
    }

    public List<EventGroupDTO> getEventGroupsByCategoryAsc(int page) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EventGroupDTO> eventGroups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            int startRow = (page - 1) * PAGE_SIZE + 1;
            int endRow = page * PAGE_SIZE;

            String sql = "SELECT * FROM ( " +
                    "    SELECT ROWNUM AS num, eg.* FROM ( " +
                    "        SELECT * FROM event_groups " +
                    "        ORDER BY category ASC" +
                    "    ) eg " +
                    ") WHERE num BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, startRow);
            ps.setInt(2, endRow);
            rs = ps.executeQuery();

            eventGroups = extractEventGroup(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event groups sorted by category asc with pagination", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return eventGroups;
    }

    public List<EventGroupDTO> getEventGroupsByRatingDesc(int page) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EventGroupDTO> eventGroups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            int startRow = (page - 1) * PAGE_SIZE + 1;
            int endRow = page * PAGE_SIZE;

            // SQL 쿼리에서 평균 평점을 계산하여 이벤트 그룹 정보를 내림차순으로 가져오고, 페이지네이션 추가
            String sql = "SELECT group_id, group_name, description, average_rating "
                    + "FROM ( "
                    + "     SELECT eg.group_id, "
                    + "            eg.group_name, "
                    + "            TO_CHAR(eg.description) AS description, "
                    + "            AVG(r.rating) AS average_rating, "
                    + "            ROW_NUMBER() OVER (ORDER BY AVG(r.rating) DESC) AS row_num "
                    + "     FROM event_groups eg, reviews r "
                    + "     WHERE eg.group_id = r.group_id "
                    + "     GROUP BY eg.group_id, eg.group_name, TO_CHAR(eg.description) "
                    + "     ) "
                    + "WHERE row_num BETWEEN ? AND ?";


            ps = conn.prepareStatement(sql);
            ps.setInt(1, startRow);
            ps.setInt(2, endRow);
            rs = ps.executeQuery();

            while (rs.next()) {
                EventGroupDTO eventGroup = new EventGroupDTO();
                eventGroup.setGroupId(rs.getInt("group_id"));
                eventGroup.setGroupName(rs.getString("group_name"));
                eventGroup.setDescription(rs.getString("description"));
                eventGroups.add(eventGroup);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event groups sorted by rating desc with pagination", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return eventGroups;
    }

    public int getTotalEventGroupsCount(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalCount = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM event_groups WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving total event groups count", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return totalCount;
    }

    public int getTotalEventGroupsCount() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalCount = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM event_groups";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving total event groups count", e);
        } finally {
            DataBaseUtils.close(conn, ps, rs);
        }

        return totalCount;
    }

    public int getTotalPageCount(int userId) {
        int totalCount = getTotalEventGroupsCount(userId);
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

    public int getTotalPageCount() {
        int totalCount = getTotalEventGroupsCount();
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

    private List<EventGroupDTO> extractEventGroup(ResultSet rs) throws SQLException {
        List<EventGroupDTO> eventGroups = new ArrayList<>();
        while (rs.next()) {
            EventGroupDTO eventGroup = new EventGroupDTO();
            eventGroup.setGroupId(rs.getInt("group_id"));
            eventGroup.setUserId(rs.getInt("user_id"));
            eventGroup.setGroupName(rs.getString("group_name"));
            eventGroup.setDescription(rs.getString("description"));
            eventGroup.setCategory(rs.getString("category"));
            eventGroup.setCreatedAt(rs.getTimestamp("created_at"));
            eventGroup.setUpdatedAt(rs.getTimestamp("updated_at"));
            eventGroups.add(eventGroup);
        }
        return eventGroups;
    }
}
