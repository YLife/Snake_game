package game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class SnakeWin extends JPanel implements ActionListener,KeyListener{
	JButton startGame,stopGame;
	int score=0,speed=0;
	boolean start=false;
	int rx,ry;
	Random r=new Random();
	
	public SnakeWin(){
		startGame=new JButton("开始");
		stopGame=new JButton("结束");
		//设置面板布局
		startGame.addActionListener(this);
		stopGame.addActionListener(this);
		this.addKeyListener(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(startGame);
		this.add(stopGame);
	}
	public void paintComponent(Graphics g){
		//清除原有方框，再重新画
		super.paintComponent(g);
		g.drawRect(10, 40, 400, 300);
		g.drawString("分数："+score,150,25);
		g.drawString("速度："+speed,220,25);
		g.setColor(new Color(0, 255, 0));
		if (start) {
			g.fillRect(10+rx*10, 40+ry*10, 10, 10);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==startGame) {
			startGame.setEnabled(true);
			start=true;
			rx=r.nextInt(40);
			ry=r.nextInt(30);
			repaint();
		}
		if (e.getSource()==stopGame) {
			System.exit(0);
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
