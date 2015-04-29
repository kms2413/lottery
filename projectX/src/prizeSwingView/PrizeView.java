/*
 로또 역대 당첨금 조회 부분 
  
  
 */

package prizeSwingView;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import prizeSwing.PrizeDao;
import prizeSwing.PrizeDto;


public class PrizeView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel titleLb;
	private JButton fMaxPBtn, sMaxPBtn, fMinPBtn, sMinPBtn;
	private DefaultTableModel model;
	private JTable jTable;
	
	public PrizeView(){
		super("역대 당첨금 조회");
		this.viewInit();
		this.viewEvent();
	}
	
	public void viewInit(){
		super.setSize(800,600);
		super.setLocationRelativeTo(null);
		
		Container con = super.getContentPane();
		con.setLayout(new BorderLayout());
		// north -------------------------
		JPanel north = new JPanel(new BorderLayout());
		north.setPreferredSize(new Dimension(500, 100));
		
		JPanel fl = new JPanel(new FlowLayout());
		north.add(fl, BorderLayout.CENTER);
		titleLb = new JLabel ("역대 로또 당첨금 조회", JLabel.CENTER);
		fl.add(titleLb);

		//Center ----------------------------------------
		String [] titleName = new String[]{"회차","추첨일", "1등", "상금", "2등", 
				"상금", "3등", "상금", "4등", "상금", "5등","상금"};
		model = new DefaultTableModel(titleName, 0);
		jTable = new JTable(model);
		
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		jTable.getTableHeader().setDefaultRenderer(headerRenderer);
		
		jTable.setRowHeight(30);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(160);
		jTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		jTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		jTable.getColumnModel().getColumn(6).setPreferredWidth(50);
		jTable.getColumnModel().getColumn(7).setPreferredWidth(120);
		jTable.getColumnModel().getColumn(8).setPreferredWidth(70);
		jTable.getColumnModel().getColumn(9).setPreferredWidth(90);
		jTable.getColumnModel().getColumn(10).setPreferredWidth(80);
		jTable.getColumnModel().getColumn(11).setPreferredWidth(90);
		
		for(int i =0; i <= 11; i++){
		
		jTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);

		}
		
		JScrollPane tabScroll = new JScrollPane(jTable);
		jTableSet("select * from prize order by num desc");
		
		//South -----------------
		JPanel south = new JPanel(new BorderLayout());
		
		JPanel gl = new JPanel(new GridLayout(1,2,3,3));
		JPanel gl2 = new JPanel(new GridLayout(1,2,3,3));
		south.add(gl, BorderLayout.NORTH);
		south.add(gl2, BorderLayout.CENTER);
		
		fMaxPBtn = new JButton("역대 최고 1등 상금");
		sMaxPBtn = new JButton("역대 최고 2등 상금");
		fMinPBtn = new JButton("역대 최저 1등 상금");
		sMinPBtn = new JButton("역대 최저 2등 상금");
		
		gl.add(fMaxPBtn);
		gl.add(sMaxPBtn);
		gl2.add(fMinPBtn);
		gl2.add(sMinPBtn);
		
		con.add(south, BorderLayout.SOUTH);
		con.add(north, BorderLayout.NORTH);
		con.add(tabScroll, BorderLayout.CENTER);
		
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
	
	public void jTableSet(String sql){
		PrizeDao dao = new PrizeDao();
		ArrayList<PrizeDto> list = dao.printAll(sql);
		
		if(list.size()!=0){
			for(int i = 0; i <list.size(); i++){
				PrizeDto dto = list.get(i);
				Vector<Object> rowData = new Vector<Object>();
				rowData.add(dto.getNum());
				rowData.add(dto.getDday());
				rowData.add(dto.getFirst());
				rowData.add(dto.getFirstP());
				rowData.add(dto.getSecond());
				rowData.add(dto.getSecondP());
				rowData.add(dto.getThird());
				rowData.add(dto.getThirdP());
				rowData.add(dto.getFourth());
				rowData.add(dto.getFourthP());
				rowData.add(dto.getFifth());
				rowData.add(dto.getFifthP());
				model.addRow(rowData);
			}
		}
	}
	
	public void viewEvent(){
		fMaxPBtn.addActionListener(this);
		sMaxPBtn.addActionListener(this);
		fMinPBtn.addActionListener(this);
		sMinPBtn.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == fMaxPBtn){
			model.setRowCount(0);
			jTableSet("select * from prize order by firstP desc");
		}
		
		if(event.getSource() == fMinPBtn){
			model.setRowCount(0);
			jTableSet("select * from prize order by firstP asc");
		}
		
		if(event.getSource()== sMaxPBtn){
			model.setRowCount(0);
			jTableSet("select * from prize order by secondP desc");
		}
		
		if(event.getSource() == sMinPBtn){
			model.setRowCount(0);
			jTableSet("select * from prize order by secondP asc");
		}
		
		
	}

}
