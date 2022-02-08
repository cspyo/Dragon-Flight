package 드래곤길들이기;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	private int P_WIDTH = 2800, P_HEIGHT = 2200;
	boolean enExist;
	
	User user;
	PosImageIcon map;
	ArrayList<PosImageIcon> buildList =  new ArrayList<>();
	ArrayList<Bullet> usAtkList = new ArrayList<>();
	ArrayList<PosImageIcon> enLocateList = new ArrayList<>();
	ArrayList<Enemy> enList = new ArrayList<>();
	ArrayList<Bullet> enAtkList = new ArrayList<>();
	ArrayList<PosImageIcon> enDeadList = new ArrayList<>();
	PosImageIcon timeBack = new PosImageIcon("src/res/시간 배경.png",10,70,200,50);
	
	
	public GamePanel() {
		setLayout(null);
		setSize(P_WIDTH,P_HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//맵 그리기
		map.draw(g);
		for(PosImageIcon bu : buildList)
			bu.draw(g);
		
		//유저 공격 그리기
		for(Bullet usB : usAtkList) {
			usB.move();
			usB.draw(g);
		}
		
		//적 위치 그리기
		if(!enExist) {
		for(PosImageIcon enLo : enLocateList)
			enLo.draw(g);
		}
		else {
			//적 그리기
			for(int i=0; i<enList.size(); i++) {
				Enemy en = enList.get(i);
				en.move();
				en.draw(g);
				
			}
			//적 공격 그리기
			for(Bullet enB : enAtkList) {
				enB.move();
				enB.draw(g);
			}
			//적 죽은 이미지 그리기
			for(PosImageIcon enD : enDeadList) {
				enD.draw(g);
			}
		}
		//유저 그리기
		user.draw(g);
		timeBack.draw(g);
		
	}
}
