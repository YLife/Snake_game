package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;


public class SnakeWin extends JPanel implements ActionListener,KeyListener,Runnable{
	JButton startGame,stopGame;
	int score=0,speed=0;
	boolean start=false;
	int rx,ry;
	Random r=new Random();
	
	//����һ������������߿�
	static List<SnakeAct> list =new ArrayList<>();
	int temp=0,tempEat1=0,tempEat2=0;
	
	//����������Ϸ�Ի���
	JDialog dialog = new JDialog();
	JLabel label = new JLabel("����ˣ���÷����ǣ�"+score);
	JButton ok = new JButton("Game over!");
	
	//�����߳�
	Thread th;
	
	//��Ϸ���ڲ��ֹ�����
	public SnakeWin(){
		startGame=new JButton("��ʼ");
		stopGame=new JButton("����");
		//������岼��
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//��Ӽ�����
		startGame.addActionListener(this);
		stopGame.addActionListener(this);
		ok.addActionListener(this);
		this.addKeyListener(this);
		//�����������ӵ����
		this.add(startGame);
		this.add(stopGame);
		dialog.setLayout(new GridLayout(2, 1));
		dialog.add(label);
		dialog.add(ok);
		dialog.setSize(250,200);
		dialog.setLocation(300, 280);
		dialog.setVisible(false);
	} 
	
	/**
	 * ���������
	 */
	public void paintComponent(Graphics g){
		//���ԭ�з��������»�
		super.paintComponent(g);
		g.drawRect(10, 40, 400, 300);
		g.drawString("������"+score,150,25);
		g.drawString("�ٶȣ�"+speed,220,25);
		g.setColor(new Color(0, 255, 0));
		//��������߿�
		if (start) {
			g.fillRect(10+rx*10, 40+ry*10, 10, 10);
			g.setColor(new Color(255, 0, 0));
			for (int i = 0; i < list.size(); i++) {
				g.fillRect(10+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
			}
		}
	}
	
	/**
	 * �¼���Ӧ
	 * @parameter ActionEvent e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==startGame) {
			//����ʼ��ť����Ϊ�����޸�
			startGame.setEnabled(false);
			start=true;
			//����x��y�������
			rx=r.nextInt(40);
			ry=r.nextInt(30);
			//������ͷ
			SnakeAct tempAct = new SnakeAct();
			tempAct.setX(20);
			tempAct.setY(15);
			list.add(tempAct);
			//���ؽ���
			this.requestFocus(true);
			th = new Thread(this);
			th.start();
			//���»������
			repaint();
		}
		if (e.getSource()==stopGame) {
			System.exit(0);
		}
		//���ok,�ص���ʼ����
		if (e.getSource()==ok) {
			list.clear();
			score = 0;
			speed = 0;
			start=false;
			startGame.setEnabled(true);
			dialog.setVisible(false);
			repaint();
		}
		
	}
	
	/**
	 * �߳��߿���㷨ʵ��
	 */
	public void eat(){
		if (rx==list.get(0).getX()&&ry==list.get(0).getY()) {
			SnakeAct tempAct = new SnakeAct();
			tempAct.setX(list.get(list.size()-1).getX());
			tempAct.setY(list.get(list.size()-1).getY());
			//�������ٶȼ���
			score+=100+10*speed;
			tempEat2+=1;
			if (tempEat2-tempEat1>=10) {
				tempEat1=tempEat2;
				if (speed<10) {
					speed++;	
				}
			}
			list.add(tempAct);
			//�߿鱻�ԣ�������������µ��߿�
			rx=r.nextInt(40);
			ry=r.nextInt(30);
		}
	}
	
	/**
	 * �����ƶ��㷨ʵ��
	 */
	public void otherMove(){
		SnakeAct tempAct = new SnakeAct();
		for (int i = 0; i < list.size(); i++) {
			if (i==1) {
				list.get(i).setX(list.get(0).getX());
				list.get(i).setY(list.get(0).getY());
			}
			if (i>1) {
				tempAct = list.get(i-1);
				list.set(i-1, list.get(i));
				list.set(i, tempAct);
			}
		}
	}
	
	/**
	 * ��ͷ�ƶ��㷨ʵ��
	 * @param x
	 * @param y
	 */
	public void move(int x , int y){
		if (minOk(x, y)) {
			otherMove();
			list.get(0).setX(list.get(0).getX()+x);
			list.get(0).setY(list.get(0).getY()+y);
			eat();
			repaint();			
		}else {
			th=null;
			label.setText("����ˣ���÷����ǣ�"+score);
			dialog.setVisible(true);
		}
	}
	public boolean minOk(int x, int y){
		if (maxOk(x+list.get(0).getX(),y+list.get(0).getY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * �߿��ƶ��߽��ж�
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean maxOk(int x,int y){
		if (x<0||x>=40||y<0||y>=30) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i>1&&list.get(0).getX()==list.get(i).getX()&&list.get(0).getY()==list.get(i).getY()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ���ݼ����¼�������Ӧ
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (start) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				move(0,-1);
				temp=1;
				break;
			case KeyEvent.VK_DOWN:
				move(0,1);
				temp=2;
				break;
			case KeyEvent.VK_LEFT:
				move(-1,0);
				temp=3;
				break;
			case KeyEvent.VK_RIGHT:
				move(1,0);
				temp=4;
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
	
	/**
	 * �߳�����
	 */
	@Override
	public void run() {
		//�����Զ��ƶ��㷨
		while (start) {
			switch (temp) {
			case 1:
				move(0, -1);
				break;
			case 2:
				move(0, 1);
				break;
			case 3:
				move(-1, 0);
				break;
			case 4:
				move(1, 0);
				break;
			default:
				break;
			}
			repaint();
			//������ʱ
			try {
				Thread.sleep(500-speed*50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
