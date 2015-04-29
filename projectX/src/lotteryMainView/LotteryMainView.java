package lotteryMainView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lotteryInputView.LotteryInputView;
import lotterySwingView.LoView;
import prizeSwingView.PrizeView;

public class LotteryMainView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel title, today;
	private JButton leftBtn, midBtn, rightBtn;
	
	public LotteryMainView(){
		super("로또 프로그램");
		this.viewInit();
		this.viewEvent();
	}
	
	public void viewInit(){
		super.setSize(400,170);
		super.setLocationRelativeTo(null);
		
		Container con = super.getContentPane();
		con.setLayout(new BorderLayout());
		
		// North -------------------------------------
		JPanel north = new JPanel(new BorderLayout());
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EE");
		
		JPanel fl = new JPanel(new FlowLayout());
		north.add(fl, BorderLayout.NORTH);
		title = new JLabel("로또 프로그램을 이용해주셔서 감사합니다.", JLabel.CENTER);
		fl.add(title);

		JPanel fl2 = new JPanel(new FlowLayout());
		north.add(fl2, BorderLayout.CENTER);
		today = new JLabel(sdf.format(date), JLabel.CENTER);
		fl2.add(today);

		// Center -------
		JPanel center = new JPanel(new FlowLayout());
		
		leftBtn = new JButton("당첨 번호입력 및 검색");
		midBtn = new JButton("번호 추천");
		rightBtn = new JButton("역대 당첨금");
		leftBtn.setPreferredSize(new Dimension(150,60));
		midBtn.setPreferredSize(new Dimension(100,60));
		rightBtn.setPreferredSize(new Dimension(100,60));

		center.add(midBtn);
		center.add(leftBtn);
		center.add(rightBtn);
		
		con.add(north, BorderLayout.NORTH);
		con.add(center);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		
	}
	
	public void viewEvent(){
		midBtn.addActionListener(this);
		leftBtn.addActionListener(this);
		rightBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
//		mainDto = new LoMainDto();
//		mainDao = new LoMainDao();
		
		if(event.getSource() == leftBtn){
			new LotteryInputView();
		}
		
		if(event.getSource() == midBtn){
			new LoView();
		}
		if(event.getSource() == rightBtn){
			new PrizeView();
		}
		
	}
	

}
