package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * 游戏窗口（面板）
 * @author Mr.Yang
 *
 */
public class SnakeWin extends JPanel implements ActionListener,KeyListener,Runnable{
	
	private static final long serialVersionUID = 1L;
	private JButton startGame,stopGame;
	private int score=0,speed=0;
	private boolean start=false;
	private int rx,ry;
	private Random r=new Random();
	
	//创建一个集合来存放蛇块
	private static List<SnakeAct> list = new ArrayList<>();
	private int temp=0,tempEat1=0,tempEat2=0;
	
	//创建结束游戏对话框
	private JDialog dialog = new JDialog();
	private JLabel label = new JLabel("你挂了！你得分数是："+score);
	private JButton ok = new JButton("Game over!");
	Thread th;
	
	public SnakeWin(){
		startGame=new JButton("开始");
		stopGame=new JButton("结束");
		//设置面板布局
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//添加监听器
		startGame.addActionListener(this);
		stopGame.addActionListener(this);
		ok.addActionListener(this);
		this.addKeyListener(this);
		//将各个组件添加到面板
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
	 * 画所有组件
	 */
	public void paintComponent(Graphics g){
		//清除原有方框，再重新画
		super.paintComponent(g);
		g.drawRect(10, 40, 400, 300);
		g.drawString("分数："+score,150,25);
		g.drawString("速度："+speed,220,25);
		g.setColor(new Color(0, 255, 0));
		//随机生成蛇块
		if (start) {
			g.fillRect(10+rx*10, 40+ry*10, 10, 10);
			//？？？？？？？？线程导致
			g.setColor(new Color(201, 201,201));
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				//？？？？？？重复动作太多
				g.fillRect(10+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
			}
		}
	}
	
	/**
	 * 事件响应
	 * @parameter ActionEvent e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//开始游戏
		if (e.getSource()==startGame) {
			startGame.setEnabled(false);
			start=true;
			//生成x，y轴随机数
			rx=r.nextInt(40);
			ry=r.nextInt(30);
			//设置蛇头
			SnakeAct tempAct = new SnakeAct();
			tempAct.setX(20);
			tempAct.setY(15);
			list.add(tempAct);
			//返回焦点
			this.requestFocus(true);
			th = new Thread(this);
			th.start();
			repaint();
		}
		//结束游戏
		if (e.getSource()==stopGame) {
			System.exit(0);
		}
		//点击ok,回到初始界面
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
	 * 蛇吃蛇块的算法实现
	 */
	public void eat(){
		if (rx==list.get(0).getX()&&ry==list.get(0).getY()) {
			SnakeAct tempAct = new SnakeAct();
			tempAct.setX(list.get(list.size()-1).getX());
			tempAct.setY(list.get(list.size()-1).getY());
			//分数，速度计算
			score+=100+10*speed;
			tempEat2+=1;
			if (tempEat2-tempEat1>=10) {
				tempEat1=tempEat2;
				if (speed<10) {
					speed++;	
				}
			}
			list.add(tempAct);
			//蛇块被吃，重新随机生成新的蛇块
			rx=r.nextInt(40);
			ry=r.nextInt(30);
		}
	}
	
	/**
	 * 蛇身移动算法实现
	 */
	public void otherMove(){
		SnakeAct tempAct = new SnakeAct();
		for (int i = 0; i < list.size(); i++) {
			if (i==1) {
				//list变化立即刷新，此处必须赋值，否则与蛇头重合
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
	 * 蛇头移动算法实现
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
			label.setText("你挂了！你得分数是："+score);
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
	 * 蛇块移动边界判定
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
	 * 根据键盘事件作出响应
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
	 * 线程运行,设置蛇块自动移动方法
	 */
	@Override
	public void run() {
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
			//设置延时
			try {
				Thread.sleep(500-speed*50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
