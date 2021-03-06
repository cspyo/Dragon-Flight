package 드래곤길들이기;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class User extends PosImageIcon{
	String atkPath;
	ImageIcon upImage,downImage,rightImage,leftImage,uRImage,uLImage,dRImage,dLImage,currentImage, hitImage;
	String name;
	int hp;
	int hpSize =50;
	int dmg;
	int leftMove, rightMove, upMove, downMove;
	int moveStep=3;
	int mapX =0, mapY =0;
	int atkCnt;
	int ammoSize=70;
	int isHit =0;

	ArrayList<PosImageIcon> hpList = new ArrayList<>();
	ArrayList<PosImageIcon> hpXList = new ArrayList<>();
	ArrayList<PosImageIcon> ammoList = new ArrayList<>();
	ArrayList<PosImageIcon> ammoXList = new ArrayList<>();

	public User(String img,int x,int y, int width, int height, int moveStep, int hp, int dmg, int atkCnt, String name) {
		super(img,x,y,width,height);
		this.moveStep = moveStep;
		this.hp = hp;
		this.dmg = dmg;
		this.atkCnt = atkCnt;
		this.name = name;
		atkPath ="src/res/"+name+" 공격.png";
		upImage = new ImageIcon("src/res/"+name+" 상.png");
		downImage = new ImageIcon("src/res/"+name+" 하.png");
		rightImage = new ImageIcon("src/res/"+name+" 우.png");
		leftImage = new ImageIcon("src/res/"+name+" 좌.png");
		uRImage = new ImageIcon("src/res/"+name+" 상우.png");
		uLImage = new ImageIcon("src/res/"+name+" 상좌.png");
		dRImage = new ImageIcon("src/res/"+name+" 하우.png");
		dLImage = new ImageIcon("src/res/"+name+" 하좌.png");

		hitImage = new ImageIcon("src/res/hit.png");

		currentImage = downImage;

		setHp();
		setAmmo();
	}

	//체력 셋팅
	public void setHp() {
		int hpX=0,hpY=0;
		for(int i=0;i<hp;i++) {
			hpList.add(new PosImageIcon("src/res/하트.png",hpX,hpY,hpSize,hpSize));
			hpX+= hpSize;
		}
	}
	//탄환 셋팅
	public void setAmmo() {
		ammoXList.clear();
		int ammoX=1130;
		int ammoY=0;
		for(int i=0;i<atkCnt; i++) {
			ammoList.add(new PosImageIcon("src/res/"+name+" 공격.png",ammoX,ammoY,ammoSize,ammoSize));
			ammoX -= ammoSize;
		}
	}
	//유저 공격
	public void attack() {
		if(!ammoList.isEmpty()) {
			PosImageIcon ammo = ammoList.remove(ammoList.size()-1);
			ammoXList.add(new PosImageIcon("src/res/"+name+" 공격x.png",ammo.x,ammo.y,ammoSize,ammoSize));
		}
	}

	//캐릭터 현재 방향과 체력 그려줌
	public void draw(Graphics g) {
		if(isHit>0 && isHit <=200)
			g.drawImage(hitImage.getImage(), x-25, y-23, 200, 200, null);

		g.drawImage(currentImage.getImage(), x, y, width, height, null);

		for(PosImageIcon hp : hpList) 
			hp.draw(g);
		for(PosImageIcon hpX : hpXList)
			hpX.draw(g);
		for(PosImageIcon ammo : ammoList)
			ammo.draw(g);
		for(PosImageIcon ammoX : ammoXList)
			ammoX.draw(g);
	}

	//캐릭터 움직임
	public int userLeftMove() {
		currentImage = leftImage;
		//맵 부딪힘
		if(mapX>=786)   leftMove = 0;
		//위 메테오 부딪힘
		else if(mapY>= 140 && mapY<=440) {
			if(mapX>= 235 && mapX<=500) 
				leftMove = 0;
			else if(mapX>=-570 && mapX<=-300) 
				leftMove=0;
			else 
				leftMove = moveStep;
		}
		//아래 메테오 부딪힘
		else if(mapY>=-460 && mapY<=-140) {
			if(mapX>=-160 && mapX<=00)
				leftMove=0;
			else
				leftMove=moveStep;
		}

		else  leftMove = moveStep;

		
		mapX+=leftMove;
		return leftMove;
	}
	public int userRightMove() {
		currentImage = rightImage;
		//맵 부딪힘
		if(mapX<= -774) rightMove = 0;
		//메테오 부딪힘

		else if(mapY>= 140 && mapY<=440) {
			if(mapX>= 300 && mapX<=550) 
				rightMove = 0;
			else if(mapX>=-500 && mapX<=-240) 
				rightMove=0;
			else 
				rightMove = -moveStep;
		}	
		else if(mapY>=-460 && mapY<=-140) {
			if(mapX>=0 && mapX<=160)
				rightMove= 0;
			else
				rightMove= -moveStep;
		}
		else rightMove = -moveStep;

		
		mapX+=rightMove;

		return rightMove;
	}
	public int userUpMove() {
		currentImage = upImage;
		//맵 부딪힘
		if(mapY>=540) upMove = 0;
		//메테오 부딪힘

		else if(mapY>= 135 && mapY<=400) {
			if(mapX>= 250 && mapX<=546) 
				upMove = 0;
			else if(mapX>=-555 && mapX<=-250) 
				upMove=0;
			else 
				upMove = moveStep;
		}	

		else if(mapX>=-150 && mapX<=140) {

			if(mapY>=-465 && mapY<=-350)
				upMove=0;
			else
				upMove=moveStep;
		}

		else upMove = moveStep;

		
		mapY+=upMove;

		return upMove;
	}
	public int userDownMove() {
		currentImage = downImage;
		if(mapY<= -537) downMove = 0;

		else if(mapY>= 241 && mapY<=465) {
			if(mapX>= 250 && mapX<=546) 
				downMove = 0;
			else if(mapX>=-555 && mapX<=-250) 
				downMove=0;
			else 
				downMove = -moveStep;
		}	

		else if(mapX>=-150 && mapX<=140) {
			if(mapY>=-230 && mapY<=-130)
				downMove= 0;
			else
				downMove= -moveStep;
		}

		else downMove = -moveStep;

		
		mapY+=downMove;
		return downMove;
	}
	//사선 움직임
	public void userURMove() {
		currentImage = uRImage;
	}
	public void userULMove() {
		currentImage = uLImage;
	}
	public void userDRMove() {
		currentImage = dRImage;
	}
	public void userDLMove() {
		currentImage = dLImage;
	}
}
