package lotteryInput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbUnit.ConnectionProvider;
import dbUnit.JdbcUtil;

public class LoInputDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
//	private LoInputDto inputDto;
	
	
	//새로운 당첨번호를 업데이트 시킨다.
	public int inputNumb(LoInputDto inputDto){
		int value = 0, updateValue = 0;
		
		try{
			// 새로운 회차 당첨번호를 입력한후 데이터베이 Lottery 테이블에
			//회차, 추첨일, 볼1, 볼2, 볼3, 볼4, 볼5, 볼6, 보너스 값을 넘겨준다.
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			sql = "insert into lottery values (?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, inputDto.getNumb());
			pstmt.setString(2, inputDto.getDday());
			pstmt.setInt(3, inputDto.getBall1());
			pstmt.setInt(4, inputDto.getBall2());
			pstmt.setInt(5, inputDto.getBall3());
			pstmt.setInt(6, inputDto.getBall4());
			pstmt.setInt(7, inputDto.getBall5());
			pstmt.setInt(8, inputDto.getBall6());
			pstmt.setInt(9, inputDto.getBonus());
			value = pstmt.executeUpdate();
			System.out.println("value" +value);
			if(value >0){
				String sql = "update freq set count = count+1 "
						+ "where num = ? or num = ? or num = ? or "
						+ "num = ? or num = ? or num = ? or num = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, inputDto.getBall1());
				pstmt.setInt(2, inputDto.getBall2());
				pstmt.setInt(3, inputDto.getBall3());
				pstmt.setInt(4, inputDto.getBall4());
				pstmt.setInt(5, inputDto.getBall5());
				pstmt.setInt(6, inputDto.getBall6());
				pstmt.setInt(7, inputDto.getBonus());
				updateValue = pstmt.executeUpdate();
				System.out.println("updateValue" + updateValue);
			}
			conn.commit();
			
		}catch(SQLException e){
			System.out.println("LoInputDao >> Input Error");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("LoInputDao >> inputNumb >> Rollback Error");
				e1.printStackTrace();
			}
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return updateValue;
	}
	

	
	//Print selected data on the textField
	public LoInputDto printData(String number){ 
		conn = ConnectionProvider.getConnection();
		LoInputDto inputDto = null;
		
		try{
			sql = "select * from lottery where num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(number));
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				inputDto = new LoInputDto();
				setAll(inputDto, rs);
			}
			
		}catch(SQLException e){
			System.out.println("LoInputDao Print Data Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
			JdbcUtil.close(conn);
		}
		return inputDto;
	}
	
	
	public ArrayList<LoInputDto> printAll(){
			conn = ConnectionProvider.getConnection();
			ArrayList<LoInputDto> list = null;
			LoInputDto inputDto = null;
		try{
			sql = "select * from lottery order by num desc";
			pstmt= conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<LoInputDto>();
			
			while(rs.next()){
				inputDto = new LoInputDto();
				setAll(inputDto, rs);
				list.add(inputDto);
			}
			
		}catch(SQLException e){
			System.out.println("LoInputDao Print All Error");
			e.printStackTrace();
			
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
			JdbcUtil.close(conn);
		}
		return list;
	}
	
	public void setAll(LoInputDto inputDto, ResultSet rs) throws SQLException{
		inputDto.setNumb(rs.getInt("num"));
		inputDto.setDday(rs.getString("dat"));
		inputDto.setBall1(rs.getInt("ball1"));
		inputDto.setBall2(rs.getInt("ball2"));
		inputDto.setBall3(rs.getInt("ball3"));
		inputDto.setBall4(rs.getInt("ball4"));
		inputDto.setBall5(rs.getInt("ball5"));
		inputDto.setBall6(rs.getInt("ball6"));
		inputDto.setBonus(rs.getInt("bonus"));
	}
}
