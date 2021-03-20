import java.util.Scanner;

public class Main {
	private PlayerGrid enemy;
	private PlayerGrid player;
	
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Scanner input = new Scanner(System.in);
		
		enemy = PlayerGrid.randomGrid(10, new int[] {2,2,3,4,5});
		
		player = PlayerGrid.randomGrid(10, new int[] {2,2,3,4,5});
		OpponentAI enemyAI = new OpponentAI(player);
		
		printGame();
		
		while(!enemy.checkGameOver() && !player.checkGameOver()) {
			Position p = getPosFromInput(input.nextLine());
			enemy.guess(p);
			enemyAI.guess();
			
			printGame();
		}
		input.close();
	}
	
	public void printGame() {
		System.out.println("\n");
		System.out.println(enemy.getEnemyViewStr());
		System.out.println("----------------------------");
		System.out.println(player.toString());
		System.out.println("\n");
	}
	
	public Position getPosFromInput(String input) {
		int row = input.charAt(0) - 65;
		int col = input.charAt(1) - 48;
		return new Position(row, col);
	}
}
