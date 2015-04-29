package lotteryInputView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import lotteryInput.LoInputDao;
import lotteryInput.LoInputDto;

public class LotteryInputView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel dday,titleLb, num, ball1, ball2, ball3, ball4, ball5, ball6, bonus;
	private JTextField ddayTf,numTf, ball1Tf, ball2Tf, ball3Tf, ball4Tf, ball5Tf, ball6Tf, bonusTf;
	private JButton makeBtn, searchBtn;
	private DefaultTableModel model;
	private JTable jTable;
	
	public LotteryInputView(){
		super("로또 번호입력 및 검색");
		this.viewInit();
		this.viewEvent();
	}
	
	public void viewInit(){
		super.setSize(1600, 1600);
		super.setLocationRelativeTo(null);
		
		Container con = super.getContentPane();
		con.setLayout(new BorderLayout());
		
		// north -------------------------------
		JPanel north = new JPanel(new BorderLayout());
		north.setPreferredSize(new Dimension(500,100));
		
		JPanel fl = new JPanel(new FlowLayout());
		north.add(fl, BorderLayout.NORTH);
		titleLb = new JLabel("로또 번호입력 및 검색", JLabel.CENTER);
		fl.add(titleLb);
		
		JPanel gl = new JPanel(new GridLayout(1,9,3,3));
		north.add(gl, BorderLayout.CENTER);
		
		num = new JLabel("회차", JLabel.CENTER);
		dday = new JLabel("추첨일", JLabel.CENTER);
		ball1 = new JLabel("1 번공", JLabel.CENTER);
		ball2 = new JLabel("2 번공", JLabel.CENTER);
		ball3 = new JLabel("3 번공", JLabel.CENTER);
		ball4 = new JLabel("4 번공", JLabel.CENTER);
		ball5 = new JLabel("5 번공", JLabel.CENTER);
		ball6 = new JLabel("6 번공", JLabel.CENTER);
		bonus = new JLabel("Bonus", JLabel.CENTER);
		
		gl.add(num);
		gl.add(dday);
		gl.add(ball1);
		gl.add(ball2);
		gl.add(ball3);
		gl.add(ball4);
		gl.add(ball5);
		gl.add(ball6);
		gl.add(bonus);
		
		JPanel gl2 = new JPanel(new GridLayout(1,9,3,3));
		north.add(gl2, BorderLayout.SOUTH);
		numTf = new JTextField();
		ddayTf = new JTextField();
		ball1Tf = new JTextField();
		ball2Tf = new JTextField();
		ball3Tf = new JTextField();
		ball4Tf = new JTextField();
		ball5Tf = new JTextField();
		ball6Tf = new JTextField();
		bonusTf = new JTextField();
		editableF(false);
		
		gl2.add(numTf);
		gl2.add(ddayTf);
		gl2.add(ball1Tf);
		gl2.add(ball2Tf);
		gl2.add(ball3Tf);
		gl2.add(ball4Tf);
		gl2.add(ball5Tf);
		gl2.add(ball6Tf);
		gl2.add(bonusTf);
		
		//Center-----------------------------------
		String [] titleName = new String[]{"회차","추첨일", "1번공", "2번공", "3번공", 
				"4번공", "5번공", "6번공", "보너스"};
		model = new DefaultTableModel(titleName, 0);
		jTable = new JTable(model);
		
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		jTable.getTableHeader().setDefaultRenderer(headerRenderer);
		
		jTable.setRowHeight(30);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		for(int i =2; i <=8; i++){
			jTable.getColumnModel().getColumn(i).setPreferredWidth(80);
		}
		for(int j =0; j <=8; j++){
			jTable.getColumnModel().getColumn(j).setCellRenderer(rightRenderer);
		}
		JScrollPane tabScroll = new JScrollPane(jTable);
		jTableSet();
		
		//South -----------------------------------------
		JPanel south = new JPanel(new GridLayout(1,3, 3,3));
		south.setPreferredSize(new Dimension(100,50));
		
		makeBtn = new JButton("당첨번호 입력");
		searchBtn = new JButton("회차 당첨번호 조회");
		
		south.add(makeBtn);
		south.add(searchBtn);
		
		// On main Panel --------------------------
		con.add(north, BorderLayout.NORTH);
		con.add(south, BorderLayout.SOUTH);
		con.add(tabScroll, BorderLayout.CENTER);
		
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void jTableSet(){
		LoInputDao inputDao = new LoInputDao();
		ArrayList<LoInputDto>list = inputDao.printAll();
		
		if(list.size()!=0){
			for(int i = 0; i < list.size(); i++){
				LoInputDto inputDto = list.get(i);
				Vector<Object> rowData = new Vector<Object>();
				rowData.add(inputDto.getNumb());
				rowData.add(inputDto.getDday());
				rowData.add(inputDto.getBall1());
				rowData.add(inputDto.getBall2());
				rowData.add(inputDto.getBall3());
				rowData.add(inputDto.getBall4());
				rowData.add(inputDto.getBall5());
				rowData.add(inputDto.getBall6());
				rowData.add(inputDto.getBonus());
				model.addRow(rowData);
			}
		}
	}
	
	//TextField 초기화 -----------------------------------
	public void textFieldClear(){
		numTf.setText("");
		ddayTf.setText("");
		ball1Tf.setText("");
		ball2Tf.setText("");
		ball3Tf.setText("");
		ball4Tf.setText("");
		ball5Tf.setText("");
		ball6Tf.setText("");
		bonusTf.setText("");
		
		editableF(false);
	}
	
	public void editableF(boolean a){
		numTf.setEditable(a);
		ddayTf.setEditable(a);
		ball1Tf.setEditable(a);
		ball2Tf.setEditable(a);
		ball3Tf.setEditable(a);
		ball4Tf.setEditable(a);
		ball5Tf.setEditable(a);
		ball6Tf.setEditable(a);
		bonusTf.setEditable(a);
	}
	
	public void viewEvent(){
		makeBtn.addActionListener(this);
		searchBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		LoInputDto inputDto = new LoInputDto();
		LoInputDao inputDao = new LoInputDao();
		
		if(event.getSource() == makeBtn){
			editableF(true);
			
			if(numTf.getText().isEmpty()||
				ddayTf.getText().isEmpty()||
				ball1Tf.getText().isEmpty()||
				ball2Tf.getText().isEmpty()||
				ball3Tf.getText().isEmpty()||
				ball4Tf.getText().isEmpty()||
				ball5Tf.getText().isEmpty()||
				ball6Tf.getText().isEmpty()||
				bonusTf.getText().isEmpty()
				){
				JOptionPane.showMessageDialog(this,  "데이터를 입력하세요.", "당첨 번호입력", JOptionPane.INFORMATION_MESSAGE);
			}else{
				setNumber(inputDto);
				int updateValue = inputDao.inputNumb(inputDto);
				if(updateValue >0){
					JOptionPane.showMessageDialog(this, "번호가 입력되었습니다.", "당첨 번호입력", JOptionPane.INFORMATION_MESSAGE);
					model.setRowCount(0);
					jTableSet();
					textFieldClear();
				}else{
					JOptionPane.showMessageDialog(this, "번호가 입력되지 않았습니다.", "당첨 번호입력", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if(event.getSource() == searchBtn){
			String number = JOptionPane.showInputDialog("몇 회차 당첨번호를 보고 싶나?");
			inputDto = inputDao.printData(number);
			editableF(false);
			
			if(inputDto!= null){
				setTextField(inputDto);
			}else{
				JOptionPane.showMessageDialog(this, "번호가 존재하지 않습니다", "당첨번호 조",
						JOptionPane.ERROR_MESSAGE);
				textFieldClear();
			}
		}
		
	}
	public void setNumber(LoInputDto inputDto){
		inputDto.setNumb(Integer.parseInt(numTf.getText()));
		inputDto.setDday(ddayTf.getText());
		inputDto.setBall1(Integer.parseInt(ball1Tf.getText()));
		inputDto.setBall2(Integer.parseInt(ball2Tf.getText()));
		inputDto.setBall3(Integer.parseInt(ball3Tf.getText()));
		inputDto.setBall4(Integer.parseInt(ball4Tf.getText()));
		inputDto.setBall5(Integer.parseInt(ball5Tf.getText()));
		inputDto.setBall6(Integer.parseInt(ball6Tf.getText()));
		inputDto.setBonus(Integer.parseInt(bonusTf.getText()));
	}
	
	public void setTextField(LoInputDto inputDto){
		numTf.setText(""+inputDto.getNumb());
		ddayTf.setText(inputDto.getDday());
		ball1Tf.setText(""+inputDto.getBall1());
		ball2Tf.setText(""+inputDto.getBall2());
		ball3Tf.setText(""+inputDto.getBall3());
		ball4Tf.setText(""+inputDto.getBall4());
		ball5Tf.setText(""+inputDto.getBall5());
		ball6Tf.setText(""+inputDto.getBall6());
		bonusTf.setText(""+inputDto.getBonus());
	}

}
