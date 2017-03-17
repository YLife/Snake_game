package game;

import javax.swing.*;
/**
 * 游戏主界面
 * @author Mr.Yang
 *
 */
public class SnakeMain extends JFrame{
	private static final long serialVersionUID = 1L;
	public SnakeMain(){
		setTitle("贪吃蛇");
		setSize(435, 390);
		setLocation(200, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//将panel添加到frame中
		add(new SnakeWin());
	}
}
