package game;

import javax.swing.*;
/**
 * ��Ϸ������
 * @author Mr.Yang
 *
 */
public class SnakeMain extends JFrame{
	private static final long serialVersionUID = 1L;
	public SnakeMain(){
		setTitle("̰����");
		setSize(435, 390);
		setLocation(200, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//��panel��ӵ�frame��
		add(new SnakeWin());
	}
}
