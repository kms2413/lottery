package prizeSwing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbUnit.ConnectionProvider;
import dbUnit.JdbcUtil;

public class PrizeDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ArrayList<PrizeDto> printAll(String sql){
		conn = ConnectionProvider.getConnection();
		ArrayList<PrizeDto>list = null;
		PrizeDto dto = null;
		
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<PrizeDto>();
			
			while(rs.next()){
				dto = new PrizeDto();
				setAll(dto, rs);
				list.add(dto);
			}
			
		}catch(SQLException e){
			System.out.println("Print All Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
			JdbcUtil.close(conn);
		}
		return list;
	}
	public void setAll(PrizeDto dto, ResultSet rs) throws SQLException{
		dto.setNum(rs.getInt("num"));
		dto.setDday(rs.getString("dday"));
		dto.setFirst(rs.getInt("first"));
		dto.setSecond(rs.getInt("second"));
		dto.setThird(rs.getInt("third"));
		dto.setFourth(rs.getInt("fourth"));
		dto.setFifth(rs.getInt("fifth"));
		dto.setFirstP(rs.getString("firstP"));
		dto.setSecondP(rs.getString("secondP"));
		dto.setThirdP(rs.getString("thirdP"));
		dto.setFourthP(rs.getString("fourthP"));
		dto.setFifthP(rs.getString("fifthP"));
	}
}
