package sqlTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import dbUnit.ConnectionProvider;
import dbUnit.JdbcUtil;

public class testDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	public ArrayList<testDto> selected(){
		conn = ConnectionProvider.getConnection();
		ArrayList<testDto> list = null;
		testDto dto = null;
		try{
			sql = "select ball1, ball2, ball3, ball4, ball5, ball6, bonus "
					+ "from lottery where "
					+ "BALL1 = 2 or ball2 = 2 or ball3 = 2 or "
					+ "ball4 = 2 or ball5 = 2 or ball6 = 2 or bonus = 2";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			list = new ArrayList<testDto>();
			
			while(rs.next()){
				dto = new testDto();
				setAll(dto, rs);
				list.add(dto);
			}
			
		}catch(SQLException e){
			System.out.println("testDao Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return list;
	}
	
	public void setAll(testDto dto, ResultSet rs) throws SQLException{
		dto.setBall1(rs.getInt("ball1"));
		dto.setBall2(rs.getInt("ball2"));
		dto.setBall3(rs.getInt("ball3"));
		dto.setBall4(rs.getInt("ball4"));
		dto.setBall5(rs.getInt("ball5"));
		dto.setBall6(rs.getInt("ball6"));
		dto.setBonus(rs.getInt("bonus"));
	}
	
	public int[] highestFreq(ArrayList<testDto> list){
		ArrayList<Integer> count = new ArrayList<Integer>();
		for(int i =1; i <= 45; i++){
			count.add(0);
		}
		for(int i= 0; i < list.size(); i++){
			testDto dto = list.get(i);
			count.set(dto.getBall1()-1, count.get(dto.getBall1()-1)+1);
			count.set(dto.getBall2()-1, count.get(dto.getBall2()-1)+1);
			count.set(dto.getBall3()-1, count.get(dto.getBall3()-1)+1);
			count.set(dto.getBall4()-1, count.get(dto.getBall4()-1)+1);
			count.set(dto.getBall5()-1, count.get(dto.getBall5()-1)+1);
			count.set(dto.getBall6()-1, count.get(dto.getBall6()-1)+1);
			count.set(dto.getBonus()-1, count.get(dto.getBonus()-1)+1);
		}
		System.out.println(count);
		
		int[] highest = new int[6];
		int max =0;
		int number = 0;
		for (int j = 0; j < highest.length; j++) {
			for (int i = 0; i < count.size(); i++) {
				
				if(count.get(i)>max){
					max = count.get(i);
					number = i+1;
				}else if(count.get(i)==max){
					Random rand = new Random();
					if(rand.nextInt(10)>5){
						max = count.get(i);
					}
				}
			}
			max=0;
			count.set(number-1, 0);
			highest[j] = number;
		}
		return highest;
//		System.out.println(count);
//		System.out.println(Arrays.toString(highest));
		
	}
}
