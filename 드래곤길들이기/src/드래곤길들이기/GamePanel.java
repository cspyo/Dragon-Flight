package �巡�����̱�;

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
	PosImageIcon timeBack = new PosImageIcon("src/res/�ð� ���.png",10,70,200,50);
	
	
	public GamePanel() {
		setLayout(null);
		setSize(P_WIDTH,P_HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//�� �׸���
		map.draw(g);
		for(PosImageIcon bu : buildList)
			bu.draw(g);
		
		//���� ���� �׸���
		for(Bullet usB : usAtkList) {
			usB.move();
			usB.draw(g);
		}
		
		//�� ��ġ �׸���
		if(!enExist) {
		for(PosImageIcon enLo : enLocateList)
			enLo.draw(g);
		}
		else {
			//�� �׸���
			for(int i=0; i<enList.size(); i++) {
				Enemy en = enList.get(i);
				en.move();
				en.draw(g);
				
			}
			//�� ���� �׸���
			for(Bullet enB : enAtkList) {
				enB.move();
				enB.draw(g);
			}
			//�� ���� �̹��� �׸���
			for(PosImageIcon enD : enDeadList) {
				enD.draw(g);
			}
		}
		//���� �׸���
		user.draw(g);
		timeBack.draw(g);
		
	}
}
