package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	public static Connection getConnection() {
		
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "1234";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println("연결완료");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("연결실패");
		return null;
	}
}