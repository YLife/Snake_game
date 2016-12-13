package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;


public class SnakeWin extends JPanel implements ActionListener,KeyListener{
	JButton startGame,stopGame;
	int score=0,speed=0;
	boolean start=false;
	int rx,ry;
	Random r=new Random();
	List<SnakeAct> list =new ArrayList<>();
	public SnakeWin(){
		startGame=new JButton("��ʼ");
		stopGame=new JButton("����");
		//������岼��
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		startGame.addActionListener(this);
		stopGame.addActionListener(this);
		this.addKeyListener(this);
		this.add(startGame);
		this.add(stopGame);
	} 
	public void paintComponent(Graphics g){
		//���ԭ�з��������»�
		super.paintComponent(g);
		g.drawRect(10, 40, 400, 300);
		g.drawString("������"+score,150,25);
		g.drawString("�ٶȣ�"+speed,220,25);
		g.setColor(new Color(0, 255, 0));
		if (start) {
			g.fillRect(10+rx*10, 40+ry*10, 10, 10);
			g.setColor(new Color(255, 0, 0));
			for (int i = 0; i < list.size(); i++) {
				g.fillRect(10+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==startGame) {
//			startGame.setEnabled(false);
			start=true;
			//����x��y�������
			rx=r.nextInt(40);
			ry=r.nextInt(30);
			SnakeAct tempAct = new SnakeAct();
			tempAct.setX(20);
			tempAct.setY(15);
			list.add(tempAct);
			//���ؽ���
			this.requestFocus(true);
			//�������ܿ�ء����»������
			repaint();
		}
		if (e.getSource()==stopGame) {
			System.exit(0);
		}
		
	}
	public void move(int x , int y){
		if (minOk(x, y)) {
			list.get(0).setX(list.get(0).getX()+x);
			list.get(0).setY(list.get(0).getY()+y);
			repaint();			
		}
	}
	public boolean minOk(int x, int y){
		if (maxOk(x+list.get(0).getX(),y+list.get(0).getY())) {
			return true;
		}
		return false;
		
	}
	public boolean maxOk(int x,int y){
		if (x<0||x>=40||y<0||y>=30) {
			return false;
		}
		return true;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (start) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				move(0,-1);
				break;
			case KeyEvent.VK_DOWN:
				move(0,1);
				break;
			case KeyEvent.VK_LEFT:
				move(-1,0);
				break;
			case KeyEvent.VK_RIGHT:
				move(1,0);
				break;
			default:
				break;
			}
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
