package com.menkj.gettempip.client;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menkj.gettempip.client.compon.SubJSplitPane;
import com.menkj.gettempip.client.init.InitParameter;
import com.menkj.gettempip.constant.Constant;

/**
 * �ͻ������
 * 
 * @author zen64
 * 
 */
public class MainWindow extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);
	private static final long serialVersionUID = -5824223763256719037L;
	private static String productName = "��ʱ����IP��ȡ�ͻ���";
	private static String version = "0.2";
	private static JFrame _frame;
	private static TrayIcon trayIcon = null;
	static SystemTray tray = SystemTray.getSystemTray();//ϵͳ����
	private static SubJSplitPane subJSplitPane = null;
	public MainWindow() {
		_frame = this;
		initParam();
		initWindowAttibues();
		initSystemTray();
		initWindowListener();
		initJSplitPane();
	}
	
	/**
	 * ��ʼ�����ò���
	 */
	private void initParam() {
		InitParameter.init();
	}

	/**
	 * ��ʼ��splitPane
	 */
	private void initJSplitPane() {
		subJSplitPane = new SubJSplitPane();
		
		subJSplitPane.setDividerLocation(237); 
		_frame.add(subJSplitPane);
	}

	/**
	 * ��ʼ�������¼�
	 */
	private void initWindowListener() {
		this.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) { // ������С���¼�
				_frame.setVisible(false);
			}
		});
	}

	/**
	 * ��ʼ��ϵͳ����
	 */
	private void initSystemTray() {

		if (SystemTray.isSupported()) // �ж�ϵͳ�Ƿ�֧��ϵͳ����
		{
			Image trayImg = Constant.getWindowIcon();// ����ͼ��
			PopupMenu pop = new PopupMenu(); // ���������һ��˵�
			MenuItem showMenuItem = new MenuItem("  �� ԭ  ");
			MenuItem exitMenuItem = new MenuItem("  �� ��  ");
			
			showMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //���»�ԭ��
					//tray.remove(trayIcon);
					displayWin();
				}
			});
			
			exitMenuItem.addActionListener(new ActionListener() { // �����˳���
				public void actionPerformed(ActionEvent e) {
					tray.remove(trayIcon);
					System.exit(0);
				}
			});

			pop.add(showMenuItem);
			pop.add(exitMenuItem);

			trayIcon = new TrayIcon(trayImg, productName + " v"+version+"", pop);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) { // �����˫���¼�
					if (e.getClickCount() == 2) {
						displayWin();
					}
				}
			});

			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ʾ������
	 */
	void displayWin(){
		if(_frame.isVisible()) return;
		_frame.setVisible(true);
		_frame.setExtendedState(JFrame.NORMAL); // ��ԭ����
		_frame.toFront();
	}
	
	/**
	 * ��ʼ�����������Ϣ
	 */
	private void initWindowAttibues() {

		// set customized window border ���ڱ߿�͸��
		// this.setUndecorated(true);
		// this.setLocationRelativeTo(null);//���ھ��в������ã���

		// ����ϵͳ����
		setLookAndFeel();

		this.setTitle(productName + " v"+version+"");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(924, 602);
		this.setIconImage(Constant.getWindowIcon());
		this.setLocationRelativeTo(null);// Ҫ�ŵ�setSize���棬��Ȼ����λ�ò���

	}

	private void setLookAndFeel() {
		try {
			String lookAndFeel = "default";// Constant.getLookAndFeel();
			if ("default".compareTo(lookAndFeel) == 0)
				lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (UnsupportedLookAndFeelException ex) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		if(args!=null){
			for (int i=0;i<args.length;i++){
				String startStr = args[i];
				if(startStr!=null && startStr.compareTo("hidden") == 0){
					//�Զ�����
					Constant.isHidden = true;
				}
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final MainWindow mWin = new MainWindow();
				mWin.setVisible(true);
				if(Constant.isHidden){
					_frame.setVisible(false);
				}
				log.debug("��ȡ��ʱ���� IP�ͻ��������ɹ���");
				
			}
		});
	}

}
