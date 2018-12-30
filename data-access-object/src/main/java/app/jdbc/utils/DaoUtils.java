package app.jdbc.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtils {

    public static int returnId(int result, PreparedStatement statement) {
        int id = 0;
        if (result == 1) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
