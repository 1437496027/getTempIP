package com.menkj.gettempip.client.compon;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menkj.gettempip.client.QuartzJob;
import com.menkj.gettempip.client.init.InitParameter;
import com.menkj.gettempip.constant.Constant;
import com.menkj.utils.PropertiesUtil;
import com.menkj.utils.QuartzManager;

public class LeftPane extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(LeftPane.class);
	private static final long serialVersionUID = -1556339765918030953L;
	private LeftJTextField clientID , serverURL ;
	private LeftTimeJTextField weekStart , weekEnd , hourStart , hourEnd , minuteInterval; 
	private JPasswordField clientPass;
	private JButton saveBtn , startBtn ,endBtn;
	private boolean isStart = false;
	private RenderContentInterface renderContent ; 
	
	public LeftPane(RightPane rightPane){
		super();
		this.renderContent = rightPane;
		Constant.renderContent = rightPane;
		this.setLayout(null);
		this.setBackground(Constant.Left_JSPLISTPANE_PANE_BACKGROUND);
		this.setBorder(new RoundBorder());//new LineBorder(Constant.Left_JSPLISTPANE_PANE_BORDER_BACKGROUND, 5));
		initContainer();
		initValue();
		initListener();
		startJob();
		
	}
	
	private void initListener() {
		
		//�������ò���
		saveBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				int n=JOptionPane.showConfirmDialog(null, "ȷ��Ҫ���������","ѯ��",JOptionPane.YES_NO_OPTION);
				if(n == 1) return;
				Map<String,String> map = new HashMap<String,String>();
				map.put("clientPass",clientPass.getText());
				map.put("clientID", clientID.getText());
				map.put("weekStart", weekStart.getText());
				map.put("weekEnd",weekEnd.getText());
				map.put("hourStart",hourStart.getText());
				map.put("hourEnd",hourEnd.getText());
				map.put("minuteInterval",minuteInterval.getText());
				map.put("serverURL",serverURL.getText());
				
				Constant.clientPass = clientPass.getText();
				Constant.clientID=  clientID.getText();
				Constant.weekStart =  weekStart.getText();
				Constant.weekEnd = weekEnd.getText();
				Constant.hourStart = hourStart.getText();
				Constant.hourEnd = hourEnd.getText();
				Constant.minuteInterval = minuteInterval.getText();
				Constant.serverURL = serverURL.getText();
				
				PropertiesUtil.createProperties(InitParameter.filename, map);
				if(isStart)
					startJob();
			}
		});
		
		/**
		 * ��������
		 */
		startBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int n=JOptionPane.showConfirmDialog(null, "ȷ��Ҫ����������","ѯ��",JOptionPane.YES_NO_OPTION);
					if(n == 1) return ;
					startJob();					
				}
		});
		
		endBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int n=JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�ر�������","ѯ��",JOptionPane.YES_NO_OPTION);
				if(n == 1) return ;
				log.info("�رն�ʱ������");
				QuartzManager.shutdownJobs();
				startBtn.setEnabled(true);
				endBtn.setEnabled(false);
				isStart = false;
			}
		});
	}
	private void startJob() {
		int n = 0;
		StringBuffer cronStr = new StringBuffer("0 */");
		if(Constant.minuteInterval!=null && StringUtils.isNumeric(Constant.minuteInterval)){
			cronStr.append(Integer.parseInt(Constant.minuteInterval));
		}else{
			n = JOptionPane.showConfirmDialog(null, "���ӵĸ�ʽ�������޸ĺ���������������","����",JOptionPane.YES_OPTION);
			return ;
		}
		
		if(Constant.hourStart !=null && StringUtils.isNumeric( Constant.hourStart )){
			cronStr.append(" "+Integer.parseInt(Constant.hourStart));
		}else{
			n = JOptionPane.showConfirmDialog(null, "Сʱ�ĸ�ʽ�������޸ĺ���������������","����",JOptionPane.YES_OPTION);
			return ;
		}
		
		if(Constant.hourEnd != null && StringUtils.isNumeric(Constant.hourEnd)){
			cronStr.append("-").append(Integer.parseInt(Constant.hourEnd));
		}else{
			n = JOptionPane.showConfirmDialog(null, "Сʱ�ĸ�ʽ�������޸ĺ���������������","����",JOptionPane.YES_OPTION);
			return ;
		}
		cronStr.append(" ? *");
		if(Constant.weekStart != null && StringUtils.isNumeric(Constant.weekStart)){
			cronStr.append(" ").append(Integer.parseInt(Constant.weekStart));
		}else{
			n = JOptionPane.showConfirmDialog(null, "���ڸ�ʽ�������޸ĺ���������������","����",JOptionPane.YES_OPTION);
			return ;
		}
		if(Constant.weekEnd != null && StringUtils.isNumeric(Constant.weekEnd)){
			cronStr.append("-").append(Integer.parseInt(Constant.weekEnd));
		}else{
			n = JOptionPane.showConfirmDialog(null, "���ڸ�ʽ�������޸ĺ���������������","����",JOptionPane.YES_OPTION);
			return ;
		}
		
		log.info("��ʱ�����������¹������У�"+cronStr.toString());
		renderContent.render("��ʱ�����������¹������У�"+cronStr.toString());
		
		try {
			QuartzManager.removeJob(Constant.jobName);
			QuartzJob qj = new QuartzJob();
			QuartzManager.addJob(Constant.jobName, qj , cronStr.toString());
			startBtn.setEnabled(false);
			endBtn.setEnabled(true);
			isStart = true;
		} catch (SchedulerException e1) {
			e1.printStackTrace();
			isStart = false;
		} catch (ParseException e1) {
			isStart = false;
			e1.printStackTrace();
		}
	}
	/**
	 * ��ʼ�����ò�����ֵ
	 */
	private void initValue() {
		clientID.setText(Constant.clientID); 
		serverURL.setText(Constant.serverURL) ;
		weekStart.setText(Constant.weekStart);
		weekEnd.setText(Constant.weekEnd); 
		hourStart.setText(Constant.hourStart); 
		hourEnd.setText(Constant.hourEnd);
		minuteInterval.setText(Constant.minuteInterval); 
		clientPass.setText(Constant.clientPass);
	}

	/**
	 * 
	 */
	private void initContainer() {
		this.add( new LeftTitleJLabel("�ͻ���ID��" , new Rectangle(10, 20, 180, 30)));	
		clientID   = new LeftJTextField(new Rectangle(15,50 , 180,30));   this.add(clientID);
		
		this.add( new LeftTitleJLabel("�������룺" , new Rectangle(10, 90, 180, 30)));		
		clientPass = new JPasswordField(); 
		clientPass.setBounds(15, 120, 180, 30); 
		clientPass.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Constant.LEFT_PANEL_JTEXTFIELD_COLOR, 1), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.add(clientPass);
		
		this.add(new LeftTitleJLabel("���ʵ�ַ��",new Rectangle(10,158 , 180,30)));
		serverURL = new LeftJTextField(new Rectangle(15,188,180,30)); this.add(serverURL);
		
		initTimePanel();
		
	}

	private void initTimePanel() {
		JPanel timePanel = new JPanel(null);
		timePanel.setBorder(BorderFactory.createTitledBorder("ִ��ʱ��"));
		timePanel.setBounds(10, 242, 200, 130);
		timePanel.setBackground(Color.white);
		this.add(timePanel);
		
		timePanel.add(new LeftTitleJLabel("ÿ��" , new Rectangle(26, 30, 30, 25),12));
		timePanel.add(new LeftTitleJLabel("��" , new Rectangle(75, 30, 30, 25) , 12));
		weekStart = new LeftTimeJTextField(new Rectangle(52,30,20,20));	timePanel.add(weekStart);
		weekEnd = new LeftTimeJTextField(new Rectangle(90,30,20,20));	timePanel.add(weekEnd);
		
		timePanel.add(new LeftTitleJLabel("ÿ��" , new Rectangle(26, 58, 30, 25) , 12));
		timePanel.add(new LeftTitleJLabel("�㵽" , new Rectangle(85, 58, 50, 25) , 12));
		hourStart = new LeftTimeJTextField(new Rectangle(52,58,30,20));	timePanel.add(hourStart);
		hourEnd = new LeftTimeJTextField(new Rectangle(110,58,30,20));	timePanel.add(hourEnd);
		timePanel.add(new LeftTitleJLabel("��" , new Rectangle(140, 58, 50, 25) , 12));
		
		timePanel.add(new LeftTitleJLabel("ÿ��" , new Rectangle(26, 86, 30, 25) , 12));
		minuteInterval = new LeftTimeJTextField(new Rectangle(52,86,30,20));	timePanel.add(minuteInterval);
		timePanel.add(new LeftTitleJLabel("����ִ��һ��" , new Rectangle(85, 86, 80, 25) , 12));
		
		saveBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_save_default.png")
				,Constant.getImageIcon("men_btn_save_hover.png")
				,Constant.getImageIcon("men_btn_save_pre.png"));
		saveBtn.setToolTipText("��������");
		saveBtn.setBounds(new Rectangle(50, 380, 100, 30) );
		this.add(saveBtn);
		
		startBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_start_default.png")
				,Constant.getImageIcon("men_btn_start_hover.png")
				,Constant.getImageIcon("men_btn_start_pre.png"));
		startBtn.setToolTipText("��������");
		startBtn.setBounds(new Rectangle(10, 440, 100, 100) );
		this.add(startBtn);
		
		endBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_stop_default.png")
				,Constant.getImageIcon("men_btn_stop_hover.png")
				,Constant.getImageIcon("men_btn_stop_pre.png"));
		endBtn.setToolTipText("��������");
		endBtn.setEnabled(false);
		endBtn.setBounds(new Rectangle(120, 440, 100, 100) );
		this.add(endBtn);
	}
	

}
