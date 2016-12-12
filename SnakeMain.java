package game;

import javax.swing.*;

public class SnakeMain extends JFrame{
	public SnakeMain(){
		setTitle("贪吃蛇");
		setSize(435, 390);
		setLocation(200, 200);
		setVisible(true);
		//将panel添加到frame中
		add(new SnakeWin());
	}
	public static void main(String[] args) {
		SnakeMain sFrame=new SnakeMain();
	}
	
	

}
