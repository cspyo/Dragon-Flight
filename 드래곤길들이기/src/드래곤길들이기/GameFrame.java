package 드래곤길들이기;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame{

	//프레임 크기
	private final int F_WIDTH = 1200;  
	private final int F_HEIGHT = 1000;
	int MAPWIDTH = 2800;
	int MAPHEIGHT = 2200;
	
	//프레임 위치
	int f_xpos,f_ypos;
	
	//캐릭터 위치
	int CHA_XPos;
	int CHA_YPos;
	
	//캐릭터에 관한 변수들
	int chaSize = 150;
	int usAtkCnt;
	int atkSize = 70;
	int isAttack=50;
	int dmg;
	int hp;
	int atkCoolTime =0;
	boolean atkStop =false;
	
	//적 관련 내용
	int enLoSize = 100;
	int enWidth = 100;
	int enHeight = 100;
	int enemy_Num = 10;
	int enAtkSize = 50;
	boolean enMove = false;
	
	//캐릭터 이동에 따른 맵 전체 이동
	int leftMove,rightMove,downMove,upMove;
	int chaMoveStep = 3;
	
	//게임 스피드
	int G_SPEED = 10;
	
	//키보드 감청자
	boolean up, down, left, right,esc;
	
	//메인프레임 콤보박스에서 가져온 캐릭터
	String name;
	
	//점수
	int score;
	
	//이긴 여부
	boolean win;

	//컴포넌트들
	JButton restartButton, endButton, resumeButton,reButton,exitButton;
	JFrame gameFrame;
	GamePanel gamePanel;
	EndPanel endPanel;
	OptionPanel optionPanel;
	JLabel timeLabel;
	JLabel scoreLabel;
	JLayeredPane lp;

	//맵
	ArrayList<Rectangle> mapRecList = new ArrayList<>();

	//음악
	AudioClip attackSound, attackNoAmmoSound, buttonSound, dieSound, enemyHitSound, inGameBgm, reloadSound, userHitSound, winBgm;
	String atk = "/sound/attack.wav";
	String atkNA = "/sound/attackNoAmmo.wav";
	String bS = "/sound/button.wav";
	String dS = "/sound/die.wav";
	String eH = "/sound/enemyHit.wav";
	String iGB = "/sound/inGameBgm.wav";
	String re = "/sound/reload.wav";
	String uH = "/sound/userHit.wav";
	String wB = "/sound/win.wav";

	//맵 빌드
	String MAP = "src/res/맵.png";
	String METEO1 = "src/res/meteo1.png";
	String METEO2 = "src/res/meteo2.png";
	String METEO3 = "src/res/meteo3.png";
	String METEO4 = "src/res/meteo4.png";
	String REC = "src/res/투명.png";
	//적
	String ENLOCATE = "src/res/적생성.png";
	String YELLOWDRAGON = "노란";
	String PINKDRAGON = "분홍";
	String BLUEDRAGON = "파란";
	String FIREDRAGON = "화염";


	//게임 타이머
	Timer gTimer = new Timer(G_SPEED, new GTimerListener());
	Timer enT = new Timer(300, new enRemoveTimer());
	int time = 0;
	int gTime = 0;

	public GameFrame(String name, int atkCnt, int dmg, int hp, int moveStep) {
		this.name = name;
		this.usAtkCnt =atkCnt;
		this.dmg = dmg;
		this.hp = hp;
		this.chaMoveStep = moveStep;
	}

	public void go() {
		gameFrame = new JFrame();
		gameFrame.setTitle("드래곤 길들이기");
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lp = new JLayeredPane();


		//프레임을 화면 가운데에 맞춤
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f_xpos = (int)(screen.getWidth() / 2 - F_WIDTH/ 2);
		f_ypos = (int)(screen.getHeight() / 2 - F_HEIGHT / 2);
		gameFrame.setLocation(f_xpos-100,f_ypos-30);


		//옵션 버튼 생성
		resumeButton = new JButton(new ImageIcon("src/res/돌아가기버튼.png"));
		restartButton = new JButton(new ImageIcon("src/res/다시하기버튼.png"));
		endButton = new JButton(new ImageIcon("src/res/게임종료버튼.png"));
		reButton = new JButton(new ImageIcon("src/res/다시하기2버튼.png"));
		exitButton = new JButton(new ImageIcon("src/res/종료버튼.png"));

		//이미지버튼을 사용하기 위한 설정
		resumeButton.setBorderPainted(false);
		resumeButton.setFocusPainted(false);
		resumeButton.setContentAreaFilled(false);
		restartButton.setBorderPainted(false);
		restartButton.setFocusPainted(false);
		restartButton.setContentAreaFilled(false);
		endButton.setBorderPainted(false);
		endButton.setFocusPainted(false);
		endButton.setContentAreaFilled(false); 
		reButton.setBorderPainted(false);
		reButton.setFocusPainted(false);
		reButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.setContentAreaFilled(false);

		//버튼 위치와 사이즈
		resumeButton.setBounds(500,380,200,75);
		restartButton.setBounds(500,470,200,75);
		endButton.setBounds(500,560,200,75);
		reButton.setBounds(900,750,200,75);
		exitButton.setBounds(900,850,200,75);

		//버튼에 리스너 추가
		ButtonListener bl = new ButtonListener();
		resumeButton.addActionListener(bl);
		restartButton.addActionListener(bl);
		endButton.addActionListener(bl);
		reButton.addActionListener(bl);
		exitButton.addActionListener(bl);

		//게임 패널 생성
		gamePanel = new GamePanel();
		optionPanel = new OptionPanel();
		endPanel = new EndPanel();

		//시간
		timeLabel = new JLabel("시간 : 0초");
		timeLabel.setFont(new Font("고딕",Font.BOLD,30));
		timeLabel.setBounds(35, 47, 200, 100);
		//점수
		scoreLabel = new JLabel(""+score);
		scoreLabel.setFont(new Font("고딕",Font.BOLD,50));
		scoreLabel.setBounds(500,550,500,400);


		//컴포넌트 add
		gamePanel.add(timeLabel);
		optionPanel.add(resumeButton);
		optionPanel.add(restartButton);
		optionPanel.add(endButton);
		endPanel.add(scoreLabel);
		endPanel.add(reButton);
		endPanel.add(exitButton);

		//맵 만들기
		int mapXPos = (MAPWIDTH-F_WIDTH)/2*-1;
		int mapYPos = (MAPHEIGHT-F_HEIGHT)/2*-1;
		gamePanel.map = new PosImageIcon(MAP,mapXPos,mapYPos,MAPWIDTH,MAPHEIGHT);
		gamePanel.buildList.add(new PosImageIcon(METEO1, 500, 700, 200, 200));
		gamePanel.buildList.add(new PosImageIcon(METEO2, 100, 100, 200, 200));
		gamePanel.buildList.add(new PosImageIcon(METEO3, 900, 100, 200, 200));
		gamePanel.buildList.add(new PosImageIcon(REC, -275, -215, 1670, 100));
		gamePanel.buildList.add(new PosImageIcon(REC, -275, 1070, 1670, 100));
		gamePanel.buildList.add(new PosImageIcon(REC, -350, -225, 100, 1440));
		gamePanel.buildList.add(new PosImageIcon(REC, 1410, -225, 100, 1440));


		//캐릭터 생성
		CHA_XPos = (F_WIDTH/2)-(chaSize/2);
		CHA_YPos = (F_HEIGHT/2)-(chaSize/2);
		gamePanel.user = new User("src/res/"+name+" 하.png", CHA_XPos, CHA_YPos, chaSize, chaSize,chaMoveStep,hp,dmg,usAtkCnt,name);
		
		//리스너 추가
		gamePanel.addKeyListener(new MoveListener());
		gamePanel.addMouseListener(new mouseAtkListener());

		//lp에 패널들 추가
		lp.add(gamePanel, new Integer(2));
		lp.add(optionPanel, new Integer(1));
		lp.add(endPanel, new Integer(0));
		gameFrame.getContentPane().add(lp);

		//음악파일 생성
		try {
			attackSound = JApplet.newAudioClip(getClass().getResource(atk));
			attackNoAmmoSound = JApplet.newAudioClip(getClass().getResource(atkNA));
			buttonSound = JApplet.newAudioClip(getClass().getResource(bS));
			dieSound = JApplet.newAudioClip(getClass().getResource(dS));
			enemyHitSound = JApplet.newAudioClip(getClass().getResource(eH));
			inGameBgm = JApplet.newAudioClip(getClass().getResource(iGB));
			reloadSound = JApplet.newAudioClip(getClass().getResource(re));
			userHitSound = JApplet.newAudioClip(getClass().getResource(uH));
			winBgm = JApplet.newAudioClip(getClass().getResource(wB));

		}catch(Exception e) {
			System.out.println("음악파일 생성에 실패했습니다.");
		}


		gamePanel.setFocusable(true);

		gameFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		gameFrame.pack();

		gameFrame.setResizable(false);
		gameFrame.setVisible(true);

		gTimer.start();
		inGameBgm.play();

	}

/////////////////////////////////////////////////////////////패널////////////////////////////////////////////////////////////////////////	
	
	//esc 누르면 나오는 옵션패널
	class OptionPanel extends JPanel{
		public OptionPanel() {
			setLayout(null);
			setBounds(0,0,F_WIDTH,F_HEIGHT);
			setOpaque(false);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0,0,0,120));
			g.fillRect(0, 0,F_WIDTH, F_HEIGHT);
		}
	}
	
	//게임이 end일때 나오는 end패널
	class EndPanel extends JPanel{
		PosImageIcon winPic;
		PosImageIcon losePic;
		public EndPanel() {
			setLayout(null);
			setBounds(0,0,F_WIDTH,F_HEIGHT);
			winPic = new PosImageIcon("src/res/승리사진.png",0,0,1200,1000);
			losePic = new PosImageIcon("src/res/패배사진.png",0,0,1200,1000);

		}
		@Override
		public void paintComponent(Graphics g) {
			if(win) {
				winPic.draw(g);
			}
			else
				losePic.draw(g);
		}
	}
	////////////////////////////////////////////////////////////유저에 관한 내용들 /////////////////////////////////////////////////////////////////////////


	//유저 공격 메소드
	public void attack(Point mouse) {
		double atX = 
				(double)(mouse.x-gamePanel.user.center().x-atkSize/2);
		double atY = 
				(double)(mouse.y-gamePanel.user.center().y-atkSize/2);
		double under=Math.sqrt((atX*atX)+(atY*atY));
		atX=atX/under;
		atY=atY/under;

		if(!gamePanel.user.ammoList.isEmpty()) {
			if(isAttack<0) {
				gamePanel.usAtkList.add(new Bullet(gamePanel.user.atkPath, gamePanel.user.center().x, gamePanel.user.center().y, atkSize, atkSize, 4.8*atX, 4.8*atY));
				//gamePanel.user.atkCnt --;
				gamePanel.user.attack();
				attackSound.play();
				isAttack = 50;
			}
		}
		if(gamePanel.user.ammoList.isEmpty()) {

			atkStop = true;
		}
	}

	//유저 공격 쿨타임 메소드
	public void atkCool() {
		if(atkCoolTime ==200) {
			gamePanel.user.atkCnt = usAtkCnt;
			gamePanel.user.setAmmo();
			reloadSound.play();
			atkCoolTime =0;
			atkStop = false;
		}

		if(atkStop) {
			atkCoolTime++;
		}
	}

	//유저 피격 메소드
	public void usHit(User us) {
		//피격시 hp--;
		//피격시 약2초동안 무적
		if(us.isHit<0) {
			userHitSound.play();
			us.isHit =200;
			if(!us.hpList.isEmpty()) {
				PosImageIcon hpX =us.hpList.remove(us.hp-1);
				us.hpXList.add(new PosImageIcon("src/res/하트x.png",hpX.x,hpX.y,hpX.width,hpX.height));
				us.hp--;
			}
			
		}
	}

	//캐릭터 움직임에 따른 모든 객체 이동
	public void userMove() {
		//상하좌우 이동
		if(left) {
			leftMove = gamePanel.user.userLeftMove();
			//맵 이동
			gamePanel.map.x += leftMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.x=bu.x+leftMove;
			//내 총알 이동
			for(Bullet b : gamePanel.usAtkList)
				b.xx = b.xx+leftMove;
			//적 총알 이동
			for(Bullet enB : gamePanel.enAtkList)
				enB.xx = enB.xx+leftMove;
			//적 생성 이미지 이동
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.x += leftMove;
			//적 이동
			for(Enemy en : gamePanel.enList)
				en.x += leftMove;
			//적 죽은 이미지 이동
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.x += leftMove;

		}
		if(right) {
			rightMove =gamePanel.user.userRightMove();
			//맵 이동
			gamePanel.map.x += rightMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.x=bu.x+rightMove;
			//내 총알 이동
			for(Bullet b : gamePanel.usAtkList)
				b.xx+=(double)rightMove;
			//적 총알 이동
			for(Bullet enB : gamePanel.enAtkList)
				enB.xx = enB.xx+rightMove;
			//적 생성 이미지 이동
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.x += rightMove;
			//적 이동
			for(Enemy en : gamePanel.enList)
				en.x += rightMove;
			//적 죽은 이미지 이동
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.x += rightMove;
		}
		if(up) {
			upMove = gamePanel.user.userUpMove();
			//맵 이동
			gamePanel.map.y += upMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.y=bu.y+upMove;
			//내 총알 이동
			for(Bullet b : gamePanel.usAtkList)
				b.yy+=(double)upMove;
			//적 총알 이동
			for(Bullet enB : gamePanel.enAtkList)
				enB.yy = enB.yy+upMove;
			//적 생성 이미지 이동
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.y += upMove;
			//적 이동
			for(Enemy en : gamePanel.enList)
				en.y += upMove;
			//적 죽은 이미지 이동
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.y += upMove;
		}
		if(down) {
			downMove = gamePanel.user.userDownMove();
			//맵 이동
			gamePanel.map.y += downMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.y=bu.y+downMove;
			//내 총알 이동
			for(Bullet b : gamePanel.usAtkList)
				b.yy+=(double)downMove;
			//적 총알 이동
			for(Bullet enB : gamePanel.enAtkList)
				enB.yy = enB.yy+downMove;
			//적 생성 이미지 이동
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.y += downMove;
			//적 이동
			for(Enemy en : gamePanel.enList)
				en.y += downMove;
			//적 죽은 이미지 이동
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.y += downMove;
		}
		//사선 움직임
		if(up && left)
			gamePanel.user.userULMove();
		if(up && right)
			gamePanel.user.userURMove();
		if(down && left)
			gamePanel.user.userDLMove();
		if(down && right)
			gamePanel.user.userDRMove();
	}


	///////////////////////////////////////////////////////////////////적에 관한 내용들///////////////////////////////////////////////////////////////////////////

	//적 위치 랜덤 생성 메소드
	public void randomEnLocate(int fX, int lX, int fY, int lY) {
		int enX =0;
		int enY =0;
		enX = new Random().nextInt(lX+1)+fX;
		enY = new Random().nextInt(lY+1)+fY;
		gamePanel.enLocateList.add(new PosImageIcon(ENLOCATE,enX+gamePanel.user.mapX,enY+gamePanel.user.mapY,enLoSize,enLoSize));
	}

	//적 위치 생성 메소드
	public void genEnLocate() {
		//최대한 골고루 벽이나 장애물 피해서 적 위치 생성
		randomEnLocate(-275, 1630, -125, 100);
		randomEnLocate(-275, 250, -125, 1110);
		randomEnLocate(-275, 650, 300, 690);
		randomEnLocate(-275, 655, 300, 690);
		randomEnLocate(-275, 1540, 900, 100);
		randomEnLocate(700, 575, 300, 700);
		randomEnLocate(700, 565, 300, 700);
		randomEnLocate(1100, 175, -125, 1120);
		randomEnLocate(300, 480, -125, 700);
		randomEnLocate(300, 480, -125, 700);
	}

	//생성된 위치에 적 생성 메소드
	public void genEn() {
		for(int i=0; i<gamePanel.enLocateList.size();i++) {
			PosImageIcon enLo = gamePanel.enLocateList.get(i);

			if(i<4)
				gamePanel.enList.add(new Enemy(YELLOWDRAGON,enLo.x,enLo.y,enWidth,enHeight,1));
			else if(i<7)
				gamePanel.enList.add(new Enemy(BLUEDRAGON,enLo.x,enLo.y,enWidth,enHeight,1));
			else if(i<9)
				gamePanel.enList.add(new Enemy(PINKDRAGON,enLo.x,enLo.y,enWidth,enHeight,2));
			else
				gamePanel.enList.add(new Enemy(FIREDRAGON,enLo.x,enLo.y,enWidth,enHeight,3));
		}
		gamePanel.enExist = true;

	}

	//적 이동 메소드
	public void enemyMove() {
		if(enMove) {
			for(Enemy en : gamePanel.enList)
				if(en.hp <= 0) {
					en.moveX =0;
					en.moveY =0;
				}
				else {
					en.moveX=2*((int)(Math.random()*3)-1);
					en.moveY=2*((int)(Math.random()*3)-1);
				}
		}
		else
			for(Enemy en : gamePanel.enList) {
				en.moveX =0;
				en.moveY =0;
			}
	}

	//적 공격 메소드
	public void EnAtk() {
		double atX;
		double atY;
		double under;
		Point p = new Point(gamePanel.user.center().x,gamePanel.user.center().y);
		for(Enemy en : gamePanel.enList) {
			atX = (double)(p.x-en.x-enAtkSize/2);
			atY = (double)(p.y-en.y-enAtkSize/2);
			under = Math.sqrt((atX*atX)+(atY*atY));
			atX=atX/under;
			atY=atY/under;
			gamePanel.enAtkList.add(new Bullet("src/res/"+en.name+"용 공격.png",en.center().x,en.center().y,enAtkSize,enAtkSize,2*atX,2*atY));
		}
	}

	//적 피격 메소드
	public void enHit(Enemy en) {
		if(en.isHit<0) {
			en.isHit =100;
			en.hp = en.hp-gamePanel.user.dmg;
		}
		if(en.hp<=0) {
			gamePanel.enDeadList.add(new PosImageIcon("src/res/"+en.name+"용 피격.png",en.x,en.y,en.width,en.height));
			gamePanel.enList.remove(en);
			score += 500;
			enT.start();
		}
	}


	////////////////////////////////////////////////////////////구조물, 개체끼리의 충돌 체크////////////////////////////////////////////////////////////////////////////

	public void crash() {
		//내 총알이랑 구조물 충돌 체크
		for(int i=0;i<gamePanel.usAtkList.size();i++) {
			Bullet b = gamePanel.usAtkList.get(i);

			for(int j=0;j<gamePanel.buildList.size();j++) {
				PosImageIcon buil = gamePanel.buildList.get(j);
				int spare = 50;
				if(b.collide(new Rectangle(buil.x+spare,buil.y+spare,buil.width-spare-20,buil.height-spare-20)))
					gamePanel.usAtkList.remove(b);
			}
		}

		//적 총알이랑 구조물 충돌체크
		for(int i=0;i<gamePanel.enAtkList.size();i++) {
			Bullet b = gamePanel.enAtkList.get(i);

			for(int j=0;j<gamePanel.buildList.size();j++) {
				PosImageIcon buil = gamePanel.buildList.get(j);
				int spare = 50;
				if(b.collide(new Rectangle(buil.x+spare,buil.y+spare,buil.width-spare-20,buil.height-spare-20)))
					gamePanel.enAtkList.remove(b);
			}
		}

		//적과 구조물 충돌체크
		for(int i=0;i<gamePanel.enList.size();i++) {
			Enemy en = gamePanel.enList.get(i);

			for(int j=0;j<gamePanel.buildList.size();j++) {
				PosImageIcon buil = gamePanel.buildList.get(j);
				int spare = 20;
				if(en.collide(new Rectangle(buil.x+spare,buil.y+spare,buil.width-spare-20,buil.height-spare-20))) {
					en.moveX =en.moveX * -1;
					en.moveY =en.moveY * -1;
				}
			}
		}


		//내 총알이랑 적 충돌체크
		for(int i=0; i<gamePanel.enList.size(); i++) {
			Enemy en = gamePanel.enList.get(i);

			for(int j=0; j<gamePanel.usAtkList.size();j++) {
				Bullet usB = gamePanel.usAtkList.get(j);

				int spare = 30;
				if(en.collide(new Rectangle(usB.x+spare, usB.y+spare, usB.width-spare, usB.height-spare))) {
					gamePanel.usAtkList.remove(usB);
					enHit(en);
					enemyHitSound.play();
					end();
				}
			}
		}

		//유저와 적 총알 충돌체크
		for(int i=0; i<gamePanel.enAtkList.size(); i++) {
			Bullet enB = gamePanel.enAtkList.get(i);
			User us = gamePanel.user;

			int spare =30;
			if(us.collide(new Rectangle(enB.x+spare, enB.y+spare, enB.width-spare, enB.height-spare))) {
				gamePanel.enAtkList.remove(enB);
				usHit(us);
				end();
			}
		}

		//유저와 적 충돌체크
		for(int i=0; i<gamePanel.enList.size(); i++) {
			Enemy en = gamePanel.enList.get(i);
			User us = gamePanel.user;

			int spare =20;
			if(us.collide(new Rectangle(en.x+spare, en.y+spare, en.width-spare, en.height-spare))) {
				usHit(us);
				end();
			}
		}

	}

	///////////////////////////////////////////////게임스코어, 게임엔드 판정///////////////////////////////////////////////////////


	public void end() {
		//유저 hp가 0이면 fail
		if(gamePanel.user.hpList.isEmpty()) {
			inGameBgm.stop();
			dieSound.play();
			win =false;
			scoreLabel.setText(""+score);
			lp.setLayer(endPanel, 5);
			endPanel.setFocusable(true);
			endPanel.requestFocus();
			gTimer.stop();
		}
		//적이 다 죽으면 win
		if(gamePanel.enList.isEmpty()) {
			inGameBgm.stop();
			winBgm.play();

			win = true;
			
			//빨리 끝내면 점수 +
			if(time <= 30)
				score = score + 5000;
			else if(time <= 40)
				score = score + 3000;
			else if(time <= 60)
				score = score+ 2000;
			else
				score = score+1000;
			scoreLabel.setText(""+score);
			lp.setLayer(endPanel, 5);
			endPanel.setFocusable(true);
			endPanel.requestFocus();
			gTimer.stop();
		}
	}

	/////////////////////////////////////////////                          타이머                                          //////////////////////////////////////////

	//게임 속도
	class GTimerListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			gTime++;
			isAttack--;

			if(gTime%80 ==0) {
				time++;
				timeLabel.setText("시간 : "+time+"초");
				//적 위치 생성
				if(time == 3)
					genEnLocate();

				//적 위치에 적 생성
				if(time == 5)
					genEn();

				//8초에 한번 적 공격
				if(time%8 ==0)
					EnAtk();

				//적 이동제어
				if(time%3 ==0)
					enMove = true;
				else
					enMove = false;


				if(time%(80*125)==0)
					inGameBgm.play();
				//적 이동
				enemyMove();


			}
			//장전 쿨타임
			atkCool();


			//적과 유저 피격시 일정시간동안 무적판정
			if(gamePanel.user.isHit >=0)
				gamePanel.user.isHit--;
			for(Enemy en : gamePanel.enList)
				if(en.isHit>=0) 
					en.isHit--;
			//유저 이동
			userMove();
			//충동판정
			crash();



			gameFrame.repaint();
		}
	}

	//적 죽을시 이미지를 지연시키기 위한 타이머
	class enRemoveTimer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!gamePanel.enDeadList.isEmpty())
				gamePanel.enDeadList.remove(0);
			enT.stop();
		}
	}



	///////////////////////////////////////////////////////////////////마우스, 키보드, 키 리스너///////////////////////////////////////////////////////
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == resumeButton) {
				lp.setLayer(optionPanel, 1);
				gamePanel.requestFocus();
				gTimer.restart();
				esc = false;
			}
			if(e.getSource() == restartButton) {
				gTimer.stop();
				gameFrame.setVisible(false);
				GameFrame game = new GameFrame(name, usAtkCnt, dmg, hp, chaMoveStep);
				game.go();
			}
			if(e.getSource() == endButton) {
				gTimer.stop();
				System.exit(1);
				gameFrame.setVisible(false);
			}
			if(e.getSource() == reButton) {
				gameFrame.setVisible(false);
				GameFrame game = new GameFrame(name, usAtkCnt, dmg, hp, chaMoveStep);
				game.go();
			}
			if(e.getSource() == exitButton) {
				gTimer.stop();
				System.exit(1);
				gameFrame.setVisible(false);
			}
		}
	}

	//키보드 감청
	class MoveListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_W) {
				up=true;
			}
			if(e.getKeyCode()==KeyEvent.VK_A) {
				left=true;
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				down=true;
			}
			if(e.getKeyCode()==KeyEvent.VK_D) {
				right=true;
			}
			//esc버튼 눌리면 옵션패널 뜸
			//한번 더 누르면 다시 게임으로 돌아감
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if(!esc) {
					gTimer.stop();
					lp.setLayer(optionPanel, 3);
					optionPanel.setFocusable(true);
					optionPanel.requestFocus();
					esc =true;
					gamePanel.requestFocus();
				}
				else {
					lp.setLayer(optionPanel, 1);
					gamePanel.requestFocus();
					gTimer.restart();
					esc = false;
				}
			}

		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_W) {
				up=false;
			}
			if(e.getKeyCode()==KeyEvent.VK_A) {
				left=false;
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				down=false;
			}
			if(e.getKeyCode()==KeyEvent.VK_D) {
				right=false;
			}
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	}

	//마우스로 탄환 던짐
	class mouseAtkListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if(!atkStop)
				attack(e.getPoint());
			else
				attackNoAmmoSound.play();
			gamePanel.requestFocus();
		}
	}



}
