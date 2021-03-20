public class Ship {
	private Position[] spaces;
	
	public Ship(Position[] spaces) {
		this.spaces = spaces;
	}
	
	public Ship(Position start, Position deltaDir, int size) {
		spaces = new Position[size];
		for(int i = 0; i < size; i++) {
			spaces[i] = start.add(deltaDir.multiply(i));
		}
	}
	
	public Position[] getPositions() {
		return spaces;
	}
	
	public boolean containsPosition(Position pos) {
		for(Position p : spaces) {
			if(p == null) { 
				continue;
			}
			if(p.equals(pos)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addHole(Position pos) {
		for(int i = 0; i < spaces.length; i++) {
			if(spaces[i] == null) {
				continue;
			}
			if(spaces[i].equals(pos)) {
				spaces[i] = null;
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfSunk() {
		for(Position p : spaces) {
			if(p != null) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		String output = "Ship: [";
		for(Position pos : spaces) {
			output+=pos.toString()+" ";
		}
		output+="]";
		return output;
	}
}
