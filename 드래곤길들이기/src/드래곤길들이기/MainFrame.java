package �巡�����̱�;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;


public class MainFrame{

	private final int F_WIDTH = 1200;  //������ ũ��
	private final int F_HEIGHT = 900;
	int f_xpos;
	int f_ypos;

	//������Ʈ��
	JButton startButton, helpButton, exitButton;
	StartPanel sp;
	JComboBox chaBox;
	//������ ������ �ٽ� ��������ֱ� ���� Ÿ�̸�
	Timer t = new Timer(1000 , new TimerListener());

	//�ʱ�ȭ�� �̹�����
	PosImageIcon fBackground,enBackground,chaBackground,keyBackground;

	//������
	JFrame firstFrame;
	JFrame helpFrame;
	GameFrame gameF;

	//���
	String F_BACK = "src/res/�ʱ�ȭ����.png";
	String CHA_BACK = "src/res/ĳ���ͼ���.png";
	String EN_BACK = "src/res/������.png";
	String KEY_BACK = "src/res/���Ӽ���.png";

	//����
	AudioClip mainFrameBgm, buttonSound;
	String mFB = "/sound/mainFrameBgm.wav";
	String bS = "/sound/button.wav";

	//ĳ���� ������ ���� �޺��ڽ�
	ImageIcon character = new ImageIcon("src/res/���� ũ���� ��.png"); //�ʱ� ĳ����
	String[] characters = {"���� ũ����", "�Ķ� ũ����", "ȭ�� ũ����"};
	String name = "���� ũ����";
	//ĳ���� info��
	int blackAtkCnt =5, blackHp = 7, blackDmg = 1, blackStep = 3;
	int blueAtkCnt = 7, blueHp = 5, blueDmg = 1, blueStep = 4;
	int redAtkCnt = 2, redHp = 2, redDmg = 30, redStep = 5;

	int time =0;

	public static void main(String[] args) {
		try {
		MainFrame ff = new MainFrame();
		ff.go();
		}catch(Exception e) {
			System.out.println("���� �߻�");
		}
	}

	//����(�ʱ�)ȭ�� ������ ����
	public void go() {
		firstFrame = new JFrame();
		firstFrame.setTitle("�巡�� ����̱�");
		firstFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//�������� ȭ�� ����� ����
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f_xpos = (int)(screen.getWidth() / 2 - F_WIDTH/ 2);
		f_ypos = (int)(screen.getHeight() / 2 - F_HEIGHT / 2);
		firstFrame.setLocation(f_xpos-150,f_ypos-50);

		sp = new StartPanel();

		startButton = new JButton(new ImageIcon("src/res/���ӽ��۹�ư.png"));
		helpButton = new JButton(new ImageIcon("src/res/���Ӽ����ư.png"));

		//�̹��� ��ư�� ���� ������
		startButton.setBorderPainted(false);startButton.setFocusPainted(false);startButton.setContentAreaFilled(false);
		helpButton.setBorderPainted(false);helpButton.setFocusPainted(false);helpButton.setContentAreaFilled(false);


		chaBox = new JComboBox(characters);
		chaBox.setSelectedIndex(0);


		startButton.setBounds(880, 630, 200, 75);
		helpButton.setBounds(880, 750, 200, 75);
		chaBox.setBounds(885, 420, 200, 25);

		ButtonListener bl = new ButtonListener();
		startButton.addActionListener(bl);
		helpButton.addActionListener(bl);
		chaBox.addActionListener(bl);

		sp.add(startButton);
		sp.add(helpButton);
		sp.add(chaBox);
		firstFrame.getContentPane().add(sp);

		//�������� ����
		try {
			mainFrameBgm = JApplet.newAudioClip(getClass().getResource(mFB));
			buttonSound = JApplet.newAudioClip(getClass().getResource(bS));
		}catch(Exception e) {
			System.out.println("���� ������ �ҷ����� ���߽��ϴ�.");
		}

		firstFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		firstFrame.pack();

		firstFrame.setResizable(false);
		firstFrame.setVisible(true);
		mainFrameBgm.play();

	}

	//���� ������ ���� �޼ҵ�
	public void helpFrameInit() {
		helpFrame = new JFrame("���� ����");
		helpFrame.setLocation(f_xpos, f_ypos);
		helpFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		helpFrame.setResizable(false);

		JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
		ChaHelpPanel chaP = new ChaHelpPanel();
		EnHelpPanel enP = new EnHelpPanel();
		KeyHelpPanel keyP = new KeyHelpPanel();

		exitButton = new JButton(new ImageIcon("src/res/�ݱ��ư.png"));
		exitButton.setBorderPainted(false);exitButton.setFocusPainted(false);exitButton.setContentAreaFilled(false);
		exitButton.setBounds(910, 785, 200, 70);
		exitButton.addActionListener(new ButtonListener());

		tp.addTab("ĳ���� ����",chaP);
		tp.addTab("�� ����",enP);
		tp.addTab("���� ����",keyP);
		helpFrame.add(exitButton);

		// ������������ x��ư ������ ���Ӽ����ư ��ۼ���
		helpFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				helpButton.setEnabled(true);
				startButton.setEnabled(true);
			}
		});

		helpFrame.getContentPane().add(tp, BorderLayout.CENTER);
		helpFrame.pack();
		helpFrame.setVisible(true);
	}

	//���� �г�
	class StartPanel extends JPanel{
		public StartPanel() {
			setLayout(null);
			fBackground = new PosImageIcon(F_BACK,0,0,F_WIDTH,F_HEIGHT);

		}

		public void paintComponent(Graphics g) {
			fBackground.draw(g);
			g.drawImage(character.getImage(), 905, 465, 150, 150, null);
		}
	}

	//���� �гε�
	//ĳ�� ���� �г�
	class ChaHelpPanel extends JPanel{
		ChaHelpPanel(){
			chaBackground = new PosImageIcon(CHA_BACK, 0, 0, F_WIDTH, F_HEIGHT);

		}
		@Override
		public void paintComponent (Graphics g) {
			chaBackground.draw(g);
		}
	}
	//�� ���� �г�
	class EnHelpPanel extends JPanel{
		EnHelpPanel(){
			enBackground = new PosImageIcon(EN_BACK, 0, 0, F_WIDTH, F_HEIGHT);
		}

		@Override
		public void paintComponent (Graphics g) {
			enBackground.draw(g);
		}
	}
	//Ű ���� �г�
	class KeyHelpPanel extends JPanel{
		KeyHelpPanel(){
			keyBackground = new PosImageIcon(KEY_BACK, 0, 0, F_WIDTH, F_HEIGHT-70);
		}
		@Override
		public void paintComponent (Graphics g) {
			keyBackground.draw(g);
		}
	}

	//�ʱ�ȭ�� ��ư ������
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == chaBox) {
				buttonSound.play();
				JComboBox cb = (JComboBox)e.getSource();
				name = (String)cb.getSelectedItem();
				try {
					character = new ImageIcon("src/res/"+name+" ��.png");
				}catch(Exception ex) {
					System.out.println("�̹����� �����ϴ�.");
				}
				firstFrame.repaint();
			}

			//���ӽ��� ��ư ������ ���� ������ ���ְ� ���� ������ ����
			if(e.getSource() == startButton) {
				buttonSound.play();
				if(name == "���� ũ����")
					gameF = new GameFrame(name, blackAtkCnt, blackDmg, blackHp, blackStep);
				if(name == "�Ķ� ũ����")
					gameF = new GameFrame(name, blueAtkCnt, blueDmg, blueHp, blueStep);
				if(name  == "ȭ�� ũ����")
					gameF = new GameFrame(name, redAtkCnt, redDmg, redHp, redStep);
				gameF.go();
				mainFrameBgm.stop();
				firstFrame.setVisible(false);

			}
			//���Ӽ��� ��ư ������ ���� ������ ���� ���������� ����
			if(e.getSource() == helpButton) {
				buttonSound.play();
				helpFrameInit();
				helpFrame.repaint();
				helpButton.setEnabled(false);
				startButton.setEnabled(false);
			}
			//���� ������ exit
			if(e.getSource() == exitButton) {
				buttonSound.play();
				helpFrame.setVisible(false);
				helpButton.setEnabled(true);
				startButton.setEnabled(true);
			}
		}

	}
	
	class TimerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			time++;

			if(time%67 ==0)
				mainFrameBgm.play();

		}

	}
}
