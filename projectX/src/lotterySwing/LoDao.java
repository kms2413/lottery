package lotterySwing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import dbUnit.ConnectionProvider;
import dbUnit.JdbcUtil;

public class LoDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	private Random ran;
	
	public int uChoose(LoDto dto){
		int value = 0;
		
		try{
			conn = ConnectionProvider.getConnection();// date, b1, b2, b3, b4, b5, b6
			sql = "insert into recNum values(recNum_num_seq.nextval,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			setPstmt(pstmt, dto);
			
			value = pstmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("LoDao >> uChoose Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return value;
	}
	
	//Setting RandNum
	public int randAll(LoDto dto){
		int value = 0;
		
		try{
			conn = ConnectionProvider.getConnection();
			sql = "insert into recNum values(recNum_num_seq.nextval,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getDat());
			pstmt.setInt(2,dto.getBall1());
			pstmt.setInt(3,dto.getBall2());
			pstmt.setInt(4,dto.getBall3());
			pstmt.setInt(5,dto.getBall4());
			pstmt.setInt(6,dto.getBall5());
			pstmt.setInt(7,dto.getBall6());
			
			value = pstmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("LoDao >> randAll Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return value;
	}
	
	//Provide best or worst number list 
	public int[] bestList(String choice){
		conn = ConnectionProvider.getConnection();
		ArrayList<Integer> list = null;
//		LoDto dto = null;
		ran = new Random();
		int [] numbList = new int[6];
		try{
			sql = "select num from freq order by count desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Integer>();
			
			while(rs.next()){
//				dto = new LoDto();
				list.add(rs.getInt("num"));
			}
			if(choice.equals("best")){
				numbList[0] = list.remove(ran.nextInt(4));
				numbList[1] = list.remove(ran.nextInt(10)+3);
				numbList[2] = list.remove(ran.nextInt(9)+3);
				numbList[3] = list.remove(ran.nextInt(18)+12);
				numbList[4] = list.remove(ran.nextInt(17)+12);
				numbList[5] = list.remove(ran.nextInt(40));
			}else if(choice.equals("worst")){
				int y = 14;
				for(int i = 0; i <6; i++){
					numbList[i] = list.remove(ran.nextInt(y)+31);
					y--;
				}	
			}else if(choice.equals("half")){
				numbList[0] = list.remove(ran.nextInt(4));
				numbList[1] = list.remove(ran.nextInt(10)+3);
				numbList[2] = list.remove(ran.nextInt(9)+3);
				numbList[3] = list.remove(ran.nextInt(13)+29);
				numbList[4] = list.remove(ran.nextInt(12)+29);
				numbList[5] = list.remove(ran.nextInt(11)+29);
			}
		}catch(SQLException e){
			System.out.println("LoDao >> BestList Error");
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
			JdbcUtil.close(conn);
		}
		sort(numbList);
		return numbList;
	}
	
	// Setting pstmt.setInt and setString
	public void setPstmt(PreparedStatement pstmt, LoDto dto) throws SQLException{
		int[] array = new int [6];
		array[0] = dto.getBall1();
		array[1] = dto.getBall2();
		array[2] = dto.getBall3();
		array[3] = dto.getBall4();
		array[4] = dto.getBall5();
		array[5] = dto.getBall6();
		sort(array);
		
		pstmt.setString(1, dto.getDat());

		for(int i = 0; i < 6; i++){
			pstmt.setInt(i+2, array[i]);
		}
	}
	
	//해당 번호와 제일 매치 잘되는 번호들 추첨
	public ArrayList<LoDto> selected(LoDto dto){
		conn = ConnectionProvider.getConnection();
		ArrayList<LoDto> list = null;
		try{
			sql = "select ball1, ball2, ball3, ball4, ball5, ball6, bonus "
					+ "from lottery where "
					+ "BALL1 = ? or ball2 = ? or ball3 = ? or "
					+ "ball4 = ? or ball5 = ? or ball6 = ? or bonus = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dto.getBall1());
			pstmt.setInt(2,dto.getBall1());
			pstmt.setInt(3,dto.getBall1());
			pstmt.setInt(4,dto.getBall1());
			pstmt.setInt(5,dto.getBall1());
			pstmt.setInt(6,dto.getBall1());
			pstmt.setInt(7,dto.getBall1());
			pstmt.executeUpdate();
			
			rs=pstmt.executeQuery();
			list = new ArrayList<LoDto>();
			
			while(rs.next()){
				dto = new LoDto();
				dto.setBall1(rs.getInt("ball1"));
				dto.setBall2(rs.getInt("ball2"));
				dto.setBall3(rs.getInt("ball3"));
				dto.setBall4(rs.getInt("ball4"));
				dto.setBall5(rs.getInt("ball5"));
				dto.setBall6(rs.getInt("ball6"));
				dto.setBonus(rs.getInt("bonus"));
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
	
	public int [] highestFreq(ArrayList<LoDto> list){
		ArrayList<Integer>count = new ArrayList<Integer>();
		//Create ArrayList size of 45.
		for(int i = 1; i <= 45; i++){
			count.add(0);
		}
		//Count each numbers from the table
		for(int i =0; i<list.size(); i++){
			LoDto dto = list.get(i);
			count.set(dto.getBall1()-1, count.get(dto.getBall1()-1)+1);
			count.set(dto.getBall2()-1, count.get(dto.getBall2()-1)+1);
			count.set(dto.getBall3()-1, count.get(dto.getBall3()-1)+1);
			count.set(dto.getBall4()-1, count.get(dto.getBall4()-1)+1);
			count.set(dto.getBall5()-1, count.get(dto.getBall5()-1)+1);
			count.set(dto.getBall6()-1, count.get(dto.getBall6()-1)+1);
			count.set(dto.getBonus()-1, count.get(dto.getBonus()-1)+1);
		}
		//Pick highest 6 numbers.
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
		sort(highest);
		return highest;
	}
	
	//Generating Random Number and put them in the numbList
	public int[] genRand(){
		Random ran = new Random();
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 1; i <=45; i++){
			list.add(i);
		}
		
		int [] numbList = new int[6];
		
		for(int i = 0; i < numbList.length; i++){
			numbList[i] = list.remove(ran.nextInt(list.size()));
		}
		sort(numbList);
		return numbList;
	}
	
	//Sort by ascending
	public static void sort(int [] array){
		for(int i = 0; i <array.length; i++){
			for(int j = i+1; j< array.length; j++){
				if(array[i] > array[j]){
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
	}
}
