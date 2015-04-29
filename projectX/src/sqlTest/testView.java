package sqlTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import dbUnit.DriverProvider;

public class testView {

	public static void main(String[] args) {
		DriverProvider.getDriver();
		testDao dao = new testDao();
		ArrayList<testDto> list = dao.selected();
		
		int countLines = 0;

		System.out.println("count = " +countLines);

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
		System.out.println(count);
		System.out.println(Arrays.toString(highest));
		
		
		for(int i =0; i< count.size(); i++){
			for(int j = i+1; j< count.size(); j++){
				if (count.get(i) > highest[i]){
					highest[1] = j;
					count.set(i, 0);
				}
			}
		}
	}

}
