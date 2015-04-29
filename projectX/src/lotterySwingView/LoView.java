package lotterySwingView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lotterySwing.LoDao;
import lotterySwing.LoDto;

public class LoView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField [][] array;
	private JLabel titleLb, dday, b1, b2, b3, b4, b5, b6;
	private JButton allRand, halfRand, uChoose, best, cancel,worst;
	private LoDao dao;
	private LoDto dto;
	
	public LoView(){
		super("로또 번호 생성");
		this.viewInit();
		this.viewEvent();
	}
	
	public void viewInit(){
		super.setSize(800, 400);
		super.setLocationRelativeTo(null);
		
		Container con = super.getContentPane();
		con.setLayout(new BorderLayout());
		
		//north -------------------------
		JPanel north = new JPanel(new BorderLayout());
		
		JPanel fl = new JPanel(new FlowLayout());
		
		//Title Label 
		north.add(fl, BorderLayout.NORTH);
		titleLb = new JLabel("로또 번호를 생성해주는 프로그램 입니다.", JLabel.CENTER);
		fl.add(titleLb);
		
		//South part of north 
		JPanel south = new JPanel(new BorderLayout());
		
		// Ball1 ~ Bonus Label
		JPanel grid1= new JPanel(new GridLayout(1,6,3,3));
		
		b1 = new JLabel("Ball 1", JLabel.CENTER);
		b2 = new JLabel("Ball 2", JLabel.CENTER);
		b3 = new JLabel("Ball 3", JLabel.CENTER);
		b4 = new JLabel("Ball 4", JLabel.CENTER);
		b5 = new JLabel("Ball 5", JLabel.CENTER);
		b6 = new JLabel("Ball 6", JLabel.CENTER);
		
		grid1.add(b1);
		grid1.add(b2);
		grid1.add(b3);
		grid1.add(b4);
		grid1.add(b5);
		grid1.add(b6);
		
		// Ball1 ~ Bonus textField
		JPanel grid2 = new JPanel(new GridLayout(5,6,3,3));
		
		array = new JTextField[5][6];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j <6; j++){
				array[i][j] = new JTextField();
				grid2.add(array[i][j]);
			}
		}
//		setTextField(array);
		
		south.add(grid1, BorderLayout.NORTH);
		south.add(grid2);
		north.add(south, BorderLayout.SOUTH);
		
		//Center ------------------
		JPanel center = new JPanel(new BorderLayout());
		
		// Showing next Drawing Date
		JPanel fl2 = new JPanel(new FlowLayout());
		dday = new JLabel(dday(), JLabel.CENTER);
		fl2.add(dday);

		center.add(fl2, BorderLayout.CENTER);

		//Showing option Buttons
//		allRand, halfRand, uChoose, best, cancel; 
		JPanel gridBot = new JPanel(new GridLayout(2,3,3,3));
		best = new JButton("역대 당첨번호로 생성");
		halfRand = new JButton("반반 섞어서 생성");
		uChoose = new JButton("너 맘대로 생성");
		allRand = new JButton("완전 랜덤으로 생성");
		worst = new JButton("역대 안당첨번호로 생성");
		cancel = new JButton("취소");
		
		gridBot.add(best);
		gridBot.add(worst);
		gridBot.add(uChoose);
		gridBot.add(allRand);
		gridBot.add(halfRand);
		gridBot.add(cancel);
		
		con.add(center, BorderLayout.SOUTH);
		con.add(north, BorderLayout.NORTH);
		con.add(gridBot, BorderLayout.CENTER);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//Setting textField to default
	public void setTextField(JTextField[][] array){
		for(int i = 1; i < 5; i++){
			for(int j = 0; j <6; j++){
				array[i][j].setEditable(false);
				array[i][j].setText("");
			}
		}
	}
	
	//Find Drawing Date
	public String dday(){
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		int num = cal.get(Calendar.DAY_OF_WEEK);
		int sat = 7-num + date;
		String days = "다음 발표일 "+cal.get(Calendar.YEAR) +"-" 
						+(cal.get(Calendar.MONTH)+1)+"-"
						+sat
						+" 토요일";
		return days;
	}
	
	// Event Handeling
	public void viewEvent(){
		best.addActionListener(this);
		uChoose.addActionListener(this);
		allRand.addActionListener(this);
		worst.addActionListener(this);
		cancel.addActionListener(this);
		halfRand.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		dto = new LoDto();
		dao = new LoDao();
		
		if(event.getSource() == best){
			setRand(dao, array,"best");
			JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "역대 당첨번호로 생성", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(event.getSource() == halfRand){
			setRand(dao, array, "half");
			JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "반반 섞어서 생성", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(event.getSource() == uChoose){
			setTextField(array);
			if(!array[0][0].getText().isEmpty()&&
				array[0][1].getText().isEmpty()&&
				array[0][2].getText().isEmpty()&&
				array[0][3].getText().isEmpty()&&
				array[0][4].getText().isEmpty()&&
				array[0][5].getText().isEmpty()
				){
				dto.setBall1(Integer.parseInt(array[0][0].getText()));
				ArrayList<LoDto> list = dao.selected(dto);
				int[] highest = dao.highestFreq(list);
				setText(dto, highest);
				JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "니맘대로 입력", JOptionPane.INFORMATION_MESSAGE);
			
			}else if(array[0][0].getText().isEmpty()||
				array[0][1].getText().isEmpty()||
				array[0][2].getText().isEmpty()||
				array[0][3].getText().isEmpty()||
				array[0][4].getText().isEmpty()||
				array[0][5].getText().isEmpty()
				){
					JOptionPane.showMessageDialog(this,  "데이터를 입력하세요.", "니맘대로 입력", JOptionPane.INFORMATION_MESSAGE);
			}else if(checkNum() == false){
				JOptionPane.showMessageDialog(this,  "중복된 숫자가 있습니다.", "니맘대로 입력", JOptionPane.INFORMATION_MESSAGE);
				for(int i= 0; i < 6; i++){
					array[0][i].setText("");
				}
			}else{
				setNumber(dto);
				int value = dao.uChoose(dto);
				if(value >0){
					JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "니맘대로 입력", JOptionPane.INFORMATION_MESSAGE);
					setTextField(array);
					
				}else{
					JOptionPane.showMessageDialog(this, "번호가 입력되지 않았습니다.", "니맘대로 입력", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if(event.getSource() == allRand){
				setRand(dao, array,"allRand");
				JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "완전랜덤으로 생성", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(event.getSource() == worst){
			setRand(dao, array, "worst");
			JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "역대 안당첨번호로 생성", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(event.getSource() == cancel){
			for(int i =0; i<6; i++){
				array[0][i].setText("");
			}
			setTextField(array);
		}
		
	}

	// Set numbers and date
	public void setNumber(LoDto dto){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String inputDate = sdf.format(date);
		
			dto.setDat(inputDate);
			dto.setBall1(Integer.parseInt(array[0][0].getText()));
			dto.setBall2(Integer.parseInt(array[0][1].getText()));
			dto.setBall3(Integer.parseInt(array[0][2].getText()));
			dto.setBall4(Integer.parseInt(array[0][3].getText()));
			dto.setBall5(Integer.parseInt(array[0][4].getText()));
			dto.setBall6(Integer.parseInt(array[0][5].getText()));
	}
	// setting Text
	public void setText(LoDto dto, int[] highest){
		dto.setBall1(highest[0]);
		dto.setBall2(highest[1]);
		dto.setBall3(highest[2]);
		dto.setBall4(highest[3]);
		dto.setBall5(highest[4]);
		dto.setBall6(highest[5]);
		
		array[0][0].setText(""+dto.getBall1());
		array[0][1].setText(""+dto.getBall2());
		array[0][2].setText(""+dto.getBall3());
		array[0][3].setText(""+dto.getBall4());
		array[0][4].setText(""+dto.getBall5());
		array[0][5].setText(""+dto.getBall6());
		
		
	}
	
	//반복된 숫자가 입력됬는지 체크
	public boolean checkNum(){
		boolean result = true;

		int [] array1 = new int[6];
		for(int i = 0 ; i <6; i++){
			array1[i] = Integer.parseInt((array[0][i].getText()));
		}
		
		for(int i = 0; i <array1.length; i++){
			for(int j = i+1; j< array1.length; j++){
				if(array1[i] == array1[j]){
					result = false;
				}else if(array1[i]>45 || array1[i] ==0){
					result = false;
				}
			}
		}
		return result;
	}
	
	// 랜덤넘버 5*6 으로 생성해서 dto로 셋팅 하기.
	public void setRand(LoDao dao, JTextField [][]array, String choice){
		dto = new LoDto();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String inputDate = sdf.format(date);
		int [] num = null;
		for(int i = 0; i < 5; i++){
			if(choice.equals("allRand")){
			num = dao.genRand();
			}else if(choice.equals("best")){
				num = dao.bestList("best");
			}else if(choice.equals("worst")){
				num = dao.bestList("worst");
			}else if(choice.equals("half")){
				num = dao.bestList("half");
			}
			for(int j = 0; j <6; j++){
				array[i][j].setText(""+num[j]);
				array[i][j].setEditable(false);
			}
			dto.setDat(inputDate);
			dto.setBall1(Integer.parseInt(array[i][0].getText()));
			dto.setBall2(Integer.parseInt(array[i][1].getText()));
			dto.setBall3(Integer.parseInt(array[i][2].getText()));
			dto.setBall4(Integer.parseInt(array[i][3].getText()));
			dto.setBall5(Integer.parseInt(array[i][4].getText()));
			dto.setBall6(Integer.parseInt(array[i][5].getText()));
			dao.randAll(dto);
		}
	}
}
