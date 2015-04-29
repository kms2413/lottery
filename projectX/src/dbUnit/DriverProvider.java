package dbUnit;

public class DriverProvider {
	public static void getDriver(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			System.err.println("Driver Error");
			e.printStackTrace();
		}
	}
}
