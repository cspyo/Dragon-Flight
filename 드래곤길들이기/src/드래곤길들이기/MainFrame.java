package 드래곤길들이기;

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

	private final int F_WIDTH = 1200;  //프레임 크기
	private final int F_HEIGHT = 900;
	int f_xpos;
	int f_ypos;

	//컴포넌트들
	JButton startButton, helpButton, exitButton;
	StartPanel sp;
	JComboBox chaBox;
	//음악이 끝나면 다시 실행시켜주기 위한 타이머
	Timer t = new Timer(1000 , new TimerListener());

	//초기화면 이미지들
	PosImageIcon fBackground,enBackground,chaBackground,keyBackground;

	//프레임
	JFrame firstFrame;
	JFrame helpFrame;
	GameFrame gameF;

	//배경
	String F_BACK = "src/res/초기화면배경.png";
	String CHA_BACK = "src/res/캐릭터설명.png";
	String EN_BACK = "src/res/적설명.png";
	String KEY_BACK = "src/res/게임설명.png";

	//음악
	AudioClip mainFrameBgm, buttonSound;
	String mFB = "/sound/mainFrameBgm.wav";
	String bS = "/sound/button.wav";

	//캐릭터 선택을 위한 콤보박스
	ImageIcon character = new ImageIcon("src/res/검은 크래곤 하.png"); //초기 캐릭터
	String[] characters = {"검은 크래곤", "파란 크래곤", "화염 크래곤"};
	String name = "검은 크래곤";
	//캐릭터 info들
	int blackAtkCnt =5, blackHp = 7, blackDmg = 1, blackStep = 3;
	int blueAtkCnt = 7, blueHp = 5, blueDmg = 1, blueStep = 4;
	int redAtkCnt = 2, redHp = 2, redDmg = 30, redStep = 5;

	int time =0;

	public static void main(String[] args) {
		try {
		MainFrame ff = new MainFrame();
		ff.go();
		}catch(Exception e) {
			System.out.println("오류 발생");
		}
	}

	//메인(초기)화면 프레임 생성
	public void go() {
		firstFrame = new JFrame();
		firstFrame.setTitle("드래곤 길들이기");
		firstFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//프레임을 화면 가운데에 맞춤
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f_xpos = (int)(screen.getWidth() / 2 - F_WIDTH/ 2);
		f_ypos = (int)(screen.getHeight() / 2 - F_HEIGHT / 2);
		firstFrame.setLocation(f_xpos-150,f_ypos-50);

		sp = new StartPanel();

		startButton = new JButton(new ImageIcon("src/res/게임시작버튼.png"));
		helpButton = new JButton(new ImageIcon("src/res/게임설명버튼.png"));

		//이미지 버튼을 위한 설정들
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

		//음악파일 생성
		try {
			mainFrameBgm = JApplet.newAudioClip(getClass().getResource(mFB));
			buttonSound = JApplet.newAudioClip(getClass().getResource(bS));
		}catch(Exception e) {
			System.out.println("음악 파일을 불러오지 못했습니다.");
		}

		firstFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		firstFrame.pack();

		firstFrame.setResizable(false);
		firstFrame.setVisible(true);
		mainFrameBgm.play();

	}

	//설명 프레임 생성 메소드
	public void helpFrameInit() {
		helpFrame = new JFrame("게임 설명");
		helpFrame.setLocation(f_xpos, f_ypos);
		helpFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		helpFrame.setResizable(false);

		JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
		ChaHelpPanel chaP = new ChaHelpPanel();
		EnHelpPanel enP = new EnHelpPanel();
		KeyHelpPanel keyP = new KeyHelpPanel();

		exitButton = new JButton(new ImageIcon("src/res/닫기버튼.png"));
		exitButton.setBorderPainted(false);exitButton.setFocusPainted(false);exitButton.setContentAreaFilled(false);
		exitButton.setBounds(910, 785, 200, 70);
		exitButton.addActionListener(new ButtonListener());

		tp.addTab("캐릭터 설명",chaP);
		tp.addTab("적 설명",enP);
		tp.addTab("게임 설명",keyP);
		helpFrame.add(exitButton);

		// 설명프레임의 x버튼 누를때 게임설명버튼 토글설정
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

	//시작 패널
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

	//설명 패널들
	//캐릭 설명 패널
	class ChaHelpPanel extends JPanel{
		ChaHelpPanel(){
			chaBackground = new PosImageIcon(CHA_BACK, 0, 0, F_WIDTH, F_HEIGHT);

		}
		@Override
		public void paintComponent (Graphics g) {
			chaBackground.draw(g);
		}
	}
	//적 설명 패널
	class EnHelpPanel extends JPanel{
		EnHelpPanel(){
			enBackground = new PosImageIcon(EN_BACK, 0, 0, F_WIDTH, F_HEIGHT);
		}

		@Override
		public void paintComponent (Graphics g) {
			enBackground.draw(g);
		}
	}
	//키 설명 패널
	class KeyHelpPanel extends JPanel{
		KeyHelpPanel(){
			keyBackground = new PosImageIcon(KEY_BACK, 0, 0, F_WIDTH, F_HEIGHT-70);
		}
		@Override
		public void paintComponent (Graphics g) {
			keyBackground.draw(g);
		}
	}

	//초기화면 버튼 리스너
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == chaBox) {
				buttonSound.play();
				JComboBox cb = (JComboBox)e.getSource();
				name = (String)cb.getSelectedItem();
				try {
					character = new ImageIcon("src/res/"+name+" 하.png");
				}catch(Exception ex) {
					System.out.println("이미지가 없습니다.");
				}
				firstFrame.repaint();
			}

			//게임시작 버튼 누르면 기존 프레임 없애고 게임 프레임 생성
			if(e.getSource() == startButton) {
				buttonSound.play();
				if(name == "검은 크래곤")
					gameF = new GameFrame(name, blackAtkCnt, blackDmg, blackHp, blackStep);
				if(name == "파란 크래곤")
					gameF = new GameFrame(name, blueAtkCnt, blueDmg, blueHp, blueStep);
				if(name  == "화염 크래곤")
					gameF = new GameFrame(name, redAtkCnt, redDmg, redHp, redStep);
				gameF.go();
				mainFrameBgm.stop();
				firstFrame.setVisible(false);

			}
			//게임설명 버튼 누르면 기존 프레임 옆에 설명프레임 생성
			if(e.getSource() == helpButton) {
				buttonSound.play();
				helpFrameInit();
				helpFrame.repaint();
				helpButton.setEnabled(false);
				startButton.setEnabled(false);
			}
			//설명 프레임 exit
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
