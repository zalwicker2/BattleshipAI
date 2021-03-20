import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerGrid{
	private static Position[] directions = { new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1) };
	
	private char[][] grid;
	private JButton[][] buttons;
	private Ship[] ships;
	private int size;
	
	private static Random r = new Random();
	
	public static PlayerGrid randomGrid(int size, int[] shipSizes) {
		PlayerGrid output = new PlayerGrid(size, new Ship[shipSizes.length]);
		for(int i : shipSizes) {
			Ship addShip = new Ship(new Position(r.nextInt(size), r.nextInt(size)), directions[r.nextInt(4)], i);
			while(!output.isValidShip(addShip)) {
				addShip = new Ship(new Position(r.nextInt(size), r.nextInt(size)), directions[r.nextInt(4)], i);
			}
			output.addShip(addShip);
		}
		return output;
	}
	
	public boolean isValidShip(Ship s) {
		for(Position p : s.getPositions()) {
			if(!isValidPosition(p)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isWithinBounds(Position pos) {
		return (pos.getX() >= 0 && pos.getX() < size) && (pos.getY() >= 0 && pos.getY() < size);
	}
	
	public boolean isValidPosition(Position pos) {
		return isWithinBounds(pos) && !shipAt(pos);
	}
	
	public PlayerGrid(int size, Ship[] ships) {
		super();
		this.ships = ships;
		this.grid = new char[size][size];
		this.buttons = new JButton[size][size];
		this.size = size;
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				if(shipAt(new Position(x, y))) {
					grid[x][y] = 's';
				} else {
					grid[x][y] = 'o';
				}
				JButton newButton = new JButton("" + grid[x][y]);
				buttons[x][y] = newButton;
				newButton.setBackground(Color.green);
			}
		}
	}
	
	public int[] getShipSizes() {
		int[] sizes = new int[ships.length];
		for(int i = 0; i < sizes.length; i++) {
			sizes[i] = ships[i].getPositions().length;
		}
		return sizes;
	}
	
	public char[][] getEnemyView() {
		int size = grid.length;
		char[][] output = new char[size][size];
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				if(grid[x][y] == 's') {
					output[x][y] = 'o';
				} else {
					output[x][y] = grid[x][y];
				}
			}
		}
		return output;
	}
	
	public boolean validGuess(Position pos) {
		return isWithinBounds(pos) && (getAt(pos) != 'X' && getAt(pos) != 'M');
	}
	
	public char getAt(Position pos) {
		return grid[pos.getX()][pos.getY()];
	}
	
	public int guess(Position pos) {
		if(grid[pos.getX()][pos.getY()] == 'M' || grid[pos.getX()][pos.getY()] == 'X') {
			return 0;
		}
		grid[pos.getX()][pos.getY()] = 'M';
		if(shipAt(pos)) {
			grid[pos.getX()][pos.getY()] = 'X';
			Ship hit = getShipAt(pos);
			hit.addHole(pos);
			if(hit.checkIfSunk()) {
				removeShip(hit);
				if(checkGameOver()) {
					System.out.println("Winner!");
					System.exit(1);
				}
				return 2; // sunk
			}
			return 1; // hit but not sunk
		}
		return 0; // miss
	}
	
	public int getGridSize() {
		return size;
	}
	
	public boolean checkGameOver() {
		for(Ship s : ships) {
			if(s != null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean addShip(Ship s) {
		for(int i = 0; i < ships.length; i++) {
			if(ships[i] == null) {
				ships[i] = s;
				Position[] poss = s.getPositions();
				for(int x = 0; x < poss.length; x++) {
					grid[poss[x].getX()][poss[x].getY()] = 's';
				}
				return true;
			}
		}
		return false;
	}
	
	public void removeShip(Ship s) { 
		for(int i = 0; i < ships.length; i++) {
			if(ships[i] == null) {
				continue;
			}
			if(ships[i].equals(s)) {
				ships[i] = null;
			}
		}
	}
	
	public boolean shipAt(Position pos) {
		for(Ship ship : ships) {
			if(ship == null) {
				continue;
			}
			if(ship.containsPosition(pos)) {
				return true;
			}
		}
		return false;
	}
	
	private Ship getShipAt(Position pos) {
		for(Ship ship : ships) {
			if(ship == null) {
				continue;
			}
			if(ship.containsPosition(pos)) {
				return ship;
			}
		}
		return null;
	}
	
	public String toString() {
		String output = "    ";
		for(int x = 0; x < grid.length; x++) {
			output+=(x + " ");
		}
		output+="\n   ";
		for(int x = 0; x < grid.length; x++) {
			output+=("--");
		}
		output+="\n";
		for(int x = 0; x < grid.length; x++) {
			char col = (char) (x + 65);
			output+=col+" | ";
			for(int y = 0; y < grid.length; y++) {
				output+=grid[x][y] + " ";
			}
			output+="\n";
		}
		return output;
	}
	
	public String getEnemyViewStr() {
		char[][] info = getEnemyView();
		String output = "    ";
		for(int x = 0; x < grid.length; x++) {
			output+=(x + " ");
		}
		output+="\n   ";
		for(int x = 0; x < grid.length; x++) {
			output+=("--");
		}
		output+="\n";
		for(int x = 0; x < grid.length; x++) {
			char col = (char) (x + 65);
			output+=col+" | ";
			for(int y = 0; y < grid.length; y++) {
				output+=info[x][y] + " ";
			}
			output+="\n";
		}
		return output;
	}
	
	public void update() {
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid.length; y++) {
				buttons[x][y].setText("" + grid[x][y]);
			}
		}
	}
}
