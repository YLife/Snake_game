package game;
/**
 * 封装蛇块x，y轴的坐标信息
 * @author Administrator
 *
 */
public class SnakeAct {
	private int x;
	private int y;

	public SnakeAct() {
		super();
	}

	public SnakeAct(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
