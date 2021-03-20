public class Position {
	private int x,y;

	public Position(int x, int y) {
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
	
	public Position add(Position pos) {
		return new Position(pos.getX() + x, pos.getY() + y);
	}
	
	public Position multiply(int i) {
		return new Position(x * i, y * i);
	}
	
	public Position opposite() {
		return new Position(-x, -y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Position other) {
		return(x == other.getX() && y == other.getY());
	}
}
