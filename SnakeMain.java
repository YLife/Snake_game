package game;

import javax.swing.*;

public class SnakeMain extends JFrame{
	public SnakeMain(){
		setTitle("̰����");
		setSize(435, 390);
		setLocation(200, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//��panel���ӵ�frame��
		add(new SnakeWin());
	}
	public static void main(String[] args) {
		SnakeMain sFrame=new SnakeMain();
	}
	
	

}
