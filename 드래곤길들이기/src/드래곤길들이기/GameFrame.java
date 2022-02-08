package �巡�����̱�;

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

	//������ ũ��
	private final int F_WIDTH = 1200;  
	private final int F_HEIGHT = 1000;
	int MAPWIDTH = 2800;
	int MAPHEIGHT = 2200;
	
	//������ ��ġ
	int f_xpos,f_ypos;
	
	//ĳ���� ��ġ
	int CHA_XPos;
	int CHA_YPos;
	
	//ĳ���Ϳ� ���� ������
	int chaSize = 150;
	int usAtkCnt;
	int atkSize = 70;
	int isAttack=50;
	int dmg;
	int hp;
	int atkCoolTime =0;
	boolean atkStop =false;
	
	//�� ���� ����
	int enLoSize = 100;
	int enWidth = 100;
	int enHeight = 100;
	int enemy_Num = 10;
	int enAtkSize = 50;
	boolean enMove = false;
	
	//ĳ���� �̵��� ���� �� ��ü �̵�
	int leftMove,rightMove,downMove,upMove;
	int chaMoveStep = 3;
	
	//���� ���ǵ�
	int G_SPEED = 10;
	
	//Ű���� ��û��
	boolean up, down, left, right,esc;
	
	//���������� �޺��ڽ����� ������ ĳ����
	String name;
	
	//����
	int score;
	
	//�̱� ����
	boolean win;

	//������Ʈ��
	JButton restartButton, endButton, resumeButton,reButton,exitButton;
	JFrame gameFrame;
	GamePanel gamePanel;
	EndPanel endPanel;
	OptionPanel optionPanel;
	JLabel timeLabel;
	JLabel scoreLabel;
	JLayeredPane lp;

	//��
	ArrayList<Rectangle> mapRecList = new ArrayList<>();

	//����
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

	//�� ����
	String MAP = "src/res/��.png";
	String METEO1 = "src/res/meteo1.png";
	String METEO2 = "src/res/meteo2.png";
	String METEO3 = "src/res/meteo3.png";
	String METEO4 = "src/res/meteo4.png";
	String REC = "src/res/����.png";
	//��
	String ENLOCATE = "src/res/������.png";
	String YELLOWDRAGON = "���";
	String PINKDRAGON = "��ȫ";
	String BLUEDRAGON = "�Ķ�";
	String FIREDRAGON = "ȭ��";


	//���� Ÿ�̸�
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
		gameFrame.setTitle("�巡�� ����̱�");
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lp = new JLayeredPane();


		//�������� ȭ�� ����� ����
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f_xpos = (int)(screen.getWidth() / 2 - F_WIDTH/ 2);
		f_ypos = (int)(screen.getHeight() / 2 - F_HEIGHT / 2);
		gameFrame.setLocation(f_xpos-100,f_ypos-30);


		//�ɼ� ��ư ����
		resumeButton = new JButton(new ImageIcon("src/res/���ư����ư.png"));
		restartButton = new JButton(new ImageIcon("src/res/�ٽ��ϱ��ư.png"));
		endButton = new JButton(new ImageIcon("src/res/���������ư.png"));
		reButton = new JButton(new ImageIcon("src/res/�ٽ��ϱ�2��ư.png"));
		exitButton = new JButton(new ImageIcon("src/res/�����ư.png"));

		//�̹�����ư�� ����ϱ� ���� ����
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

		//��ư ��ġ�� ������
		resumeButton.setBounds(500,380,200,75);
		restartButton.setBounds(500,470,200,75);
		endButton.setBounds(500,560,200,75);
		reButton.setBounds(900,750,200,75);
		exitButton.setBounds(900,850,200,75);

		//��ư�� ������ �߰�
		ButtonListener bl = new ButtonListener();
		resumeButton.addActionListener(bl);
		restartButton.addActionListener(bl);
		endButton.addActionListener(bl);
		reButton.addActionListener(bl);
		exitButton.addActionListener(bl);

		//���� �г� ����
		gamePanel = new GamePanel();
		optionPanel = new OptionPanel();
		endPanel = new EndPanel();

		//�ð�
		timeLabel = new JLabel("�ð� : 0��");
		timeLabel.setFont(new Font("���",Font.BOLD,30));
		timeLabel.setBounds(35, 47, 200, 100);
		//����
		scoreLabel = new JLabel(""+score);
		scoreLabel.setFont(new Font("���",Font.BOLD,50));
		scoreLabel.setBounds(500,550,500,400);


		//������Ʈ add
		gamePanel.add(timeLabel);
		optionPanel.add(resumeButton);
		optionPanel.add(restartButton);
		optionPanel.add(endButton);
		endPanel.add(scoreLabel);
		endPanel.add(reButton);
		endPanel.add(exitButton);

		//�� �����
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


		//ĳ���� ����
		CHA_XPos = (F_WIDTH/2)-(chaSize/2);
		CHA_YPos = (F_HEIGHT/2)-(chaSize/2);
		gamePanel.user = new User("src/res/"+name+" ��.png", CHA_XPos, CHA_YPos, chaSize, chaSize,chaMoveStep,hp,dmg,usAtkCnt,name);
		
		//������ �߰�
		gamePanel.addKeyListener(new MoveListener());
		gamePanel.addMouseListener(new mouseAtkListener());

		//lp�� �гε� �߰�
		lp.add(gamePanel, new Integer(2));
		lp.add(optionPanel, new Integer(1));
		lp.add(endPanel, new Integer(0));
		gameFrame.getContentPane().add(lp);

		//�������� ����
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
			System.out.println("�������� ������ �����߽��ϴ�.");
		}


		gamePanel.setFocusable(true);

		gameFrame.setPreferredSize(new Dimension(F_WIDTH , F_HEIGHT));
		gameFrame.pack();

		gameFrame.setResizable(false);
		gameFrame.setVisible(true);

		gTimer.start();
		inGameBgm.play();

	}

/////////////////////////////////////////////////////////////�г�////////////////////////////////////////////////////////////////////////	
	
	//esc ������ ������ �ɼ��г�
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
	
	//������ end�϶� ������ end�г�
	class EndPanel extends JPanel{
		PosImageIcon winPic;
		PosImageIcon losePic;
		public EndPanel() {
			setLayout(null);
			setBounds(0,0,F_WIDTH,F_HEIGHT);
			winPic = new PosImageIcon("src/res/�¸�����.png",0,0,1200,1000);
			losePic = new PosImageIcon("src/res/�й����.png",0,0,1200,1000);

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
	////////////////////////////////////////////////////////////������ ���� ����� /////////////////////////////////////////////////////////////////////////


	//���� ���� �޼ҵ�
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

	//���� ���� ��Ÿ�� �޼ҵ�
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

	//���� �ǰ� �޼ҵ�
	public void usHit(User us) {
		//�ǰݽ� hp--;
		//�ǰݽ� ��2�ʵ��� ����
		if(us.isHit<0) {
			userHitSound.play();
			us.isHit =200;
			if(!us.hpList.isEmpty()) {
				PosImageIcon hpX =us.hpList.remove(us.hp-1);
				us.hpXList.add(new PosImageIcon("src/res/��Ʈx.png",hpX.x,hpX.y,hpX.width,hpX.height));
				us.hp--;
			}
			
		}
	}

	//ĳ���� �����ӿ� ���� ��� ��ü �̵�
	public void userMove() {
		//�����¿� �̵�
		if(left) {
			leftMove = gamePanel.user.userLeftMove();
			//�� �̵�
			gamePanel.map.x += leftMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.x=bu.x+leftMove;
			//�� �Ѿ� �̵�
			for(Bullet b : gamePanel.usAtkList)
				b.xx = b.xx+leftMove;
			//�� �Ѿ� �̵�
			for(Bullet enB : gamePanel.enAtkList)
				enB.xx = enB.xx+leftMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.x += leftMove;
			//�� �̵�
			for(Enemy en : gamePanel.enList)
				en.x += leftMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.x += leftMove;

		}
		if(right) {
			rightMove =gamePanel.user.userRightMove();
			//�� �̵�
			gamePanel.map.x += rightMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.x=bu.x+rightMove;
			//�� �Ѿ� �̵�
			for(Bullet b : gamePanel.usAtkList)
				b.xx+=(double)rightMove;
			//�� �Ѿ� �̵�
			for(Bullet enB : gamePanel.enAtkList)
				enB.xx = enB.xx+rightMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.x += rightMove;
			//�� �̵�
			for(Enemy en : gamePanel.enList)
				en.x += rightMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.x += rightMove;
		}
		if(up) {
			upMove = gamePanel.user.userUpMove();
			//�� �̵�
			gamePanel.map.y += upMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.y=bu.y+upMove;
			//�� �Ѿ� �̵�
			for(Bullet b : gamePanel.usAtkList)
				b.yy+=(double)upMove;
			//�� �Ѿ� �̵�
			for(Bullet enB : gamePanel.enAtkList)
				enB.yy = enB.yy+upMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.y += upMove;
			//�� �̵�
			for(Enemy en : gamePanel.enList)
				en.y += upMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.y += upMove;
		}
		if(down) {
			downMove = gamePanel.user.userDownMove();
			//�� �̵�
			gamePanel.map.y += downMove;
			for(PosImageIcon bu : gamePanel.buildList) 
				bu.y=bu.y+downMove;
			//�� �Ѿ� �̵�
			for(Bullet b : gamePanel.usAtkList)
				b.yy+=(double)downMove;
			//�� �Ѿ� �̵�
			for(Bullet enB : gamePanel.enAtkList)
				enB.yy = enB.yy+downMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enLo : gamePanel.enLocateList)
				enLo.y += downMove;
			//�� �̵�
			for(Enemy en : gamePanel.enList)
				en.y += downMove;
			//�� ���� �̹��� �̵�
			for(PosImageIcon enD : gamePanel.enDeadList)
				enD.y += downMove;
		}
		//�缱 ������
		if(up && left)
			gamePanel.user.userULMove();
		if(up && right)
			gamePanel.user.userURMove();
		if(down && left)
			gamePanel.user.userDLMove();
		if(down && right)
			gamePanel.user.userDRMove();
	}


	///////////////////////////////////////////////////////////////////���� ���� �����///////////////////////////////////////////////////////////////////////////

	//�� ��ġ ���� ���� �޼ҵ�
	public void randomEnLocate(int fX, int lX, int fY, int lY) {
		int enX =0;
		int enY =0;
		enX = new Random().nextInt(lX+1)+fX;
		enY = new Random().nextInt(lY+1)+fY;
		gamePanel.enLocateList.add(new PosImageIcon(ENLOCATE,enX+gamePanel.user.mapX,enY+gamePanel.user.mapY,enLoSize,enLoSize));
	}

	//�� ��ġ ���� �޼ҵ�
	public void genEnLocate() {
		//�ִ��� ���� ���̳� ��ֹ� ���ؼ� �� ��ġ ����
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

	//������ ��ġ�� �� ���� �޼ҵ�
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

	//�� �̵� �޼ҵ�
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

	//�� ���� �޼ҵ�
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
			gamePanel.enAtkList.add(new Bullet("src/res/"+en.name+"�� ����.png",en.center().x,en.center().y,enAtkSize,enAtkSize,2*atX,2*atY));
		}
	}

	//�� �ǰ� �޼ҵ�
	public void enHit(Enemy en) {
		if(en.isHit<0) {
			en.isHit =100;
			en.hp = en.hp-gamePanel.user.dmg;
		}
		if(en.hp<=0) {
			gamePanel.enDeadList.add(new PosImageIcon("src/res/"+en.name+"�� �ǰ�.png",en.x,en.y,en.width,en.height));
			gamePanel.enList.remove(en);
			score += 500;
			enT.start();
		}
	}


	////////////////////////////////////////////////////////////������, ��ü������ �浹 üũ////////////////////////////////////////////////////////////////////////////

	public void crash() {
		//�� �Ѿ��̶� ������ �浹 üũ
		for(int i=0;i<gamePanel.usAtkList.size();i++) {
			Bullet b = gamePanel.usAtkList.get(i);

			for(int j=0;j<gamePanel.buildList.size();j++) {
				PosImageIcon buil = gamePanel.buildList.get(j);
				int spare = 50;
				if(b.collide(new Rectangle(buil.x+spare,buil.y+spare,buil.width-spare-20,buil.height-spare-20)))
					gamePanel.usAtkList.remove(b);
			}
		}

		//�� �Ѿ��̶� ������ �浹üũ
		for(int i=0;i<gamePanel.enAtkList.size();i++) {
			Bullet b = gamePanel.enAtkList.get(i);

			for(int j=0;j<gamePanel.buildList.size();j++) {
				PosImageIcon buil = gamePanel.buildList.get(j);
				int spare = 50;
				if(b.collide(new Rectangle(buil.x+spare,buil.y+spare,buil.width-spare-20,buil.height-spare-20)))
					gamePanel.enAtkList.remove(b);
			}
		}

		//���� ������ �浹üũ
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


		//�� �Ѿ��̶� �� �浹üũ
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

		//������ �� �Ѿ� �浹üũ
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

		//������ �� �浹üũ
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

	///////////////////////////////////////////////���ӽ��ھ�, ���ӿ��� ����///////////////////////////////////////////////////////


	public void end() {
		//���� hp�� 0�̸� fail
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
		//���� �� ������ win
		if(gamePanel.enList.isEmpty()) {
			inGameBgm.stop();
			winBgm.play();

			win = true;
			
			//���� ������ ���� +
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

	/////////////////////////////////////////////                          Ÿ�̸�                                          //////////////////////////////////////////

	//���� �ӵ�
	class GTimerListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			gTime++;
			isAttack--;

			if(gTime%80 ==0) {
				time++;
				timeLabel.setText("�ð� : "+time+"��");
				//�� ��ġ ����
				if(time == 3)
					genEnLocate();

				//�� ��ġ�� �� ����
				if(time == 5)
					genEn();

				//8�ʿ� �ѹ� �� ����
				if(time%8 ==0)
					EnAtk();

				//�� �̵�����
				if(time%3 ==0)
					enMove = true;
				else
					enMove = false;


				if(time%(80*125)==0)
					inGameBgm.play();
				//�� �̵�
				enemyMove();


			}
			//���� ��Ÿ��
			atkCool();


			//���� ���� �ǰݽ� �����ð����� ��������
			if(gamePanel.user.isHit >=0)
				gamePanel.user.isHit--;
			for(Enemy en : gamePanel.enList)
				if(en.isHit>=0) 
					en.isHit--;
			//���� �̵�
			userMove();
			//�浿����
			crash();



			gameFrame.repaint();
		}
	}

	//�� ������ �̹����� ������Ű�� ���� Ÿ�̸�
	class enRemoveTimer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!gamePanel.enDeadList.isEmpty())
				gamePanel.enDeadList.remove(0);
			enT.stop();
		}
	}



	///////////////////////////////////////////////////////////////////���콺, Ű����, Ű ������///////////////////////////////////////////////////////
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

	//Ű���� ��û
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
			//esc��ư ������ �ɼ��г� ��
			//�ѹ� �� ������ �ٽ� �������� ���ư�
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

	//���콺�� źȯ ����
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
