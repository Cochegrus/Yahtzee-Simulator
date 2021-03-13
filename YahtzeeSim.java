import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class YahtzeeSim {
	
	private YahtzeeScoreboard[] players;
	private ComputerPlayer[] computer;
	private DiceArray playerDice;
	private Queue rotation;
	private int numPlayers;
	private final int NUM_OF_DICES = 5;
	
	public YahtzeeSim(int numUsers, int numCPU, BufferedReader br) {
		this.numPlayers = numUsers + numCPU;
		this.players = new YahtzeeScoreboard[numUsers];
		this.computer = new ComputerPlayer[numCPU];
		this.playerDice = new DiceArray(this.NUM_OF_DICES);
		this.rotation = new Queue();
		this.rotation.setQueue(numPlayers);
		
		for(int d = 0; d < this.NUM_OF_DICES; d++) {
			this.playerDice.setDice(new Dice("D" + (d+1)));
		}
		
		for(int i = 0; i < numPlayers; i++) {
			String name;
			if(i < numUsers) {
				System.out.println("Enter name for Player" + (i+1) + ": ");
				try{
					name = br.readLine();
					this.players[i] = new YahtzeeScoreboard(name);
				}
				catch(IOException e) {name = "Player" + (i+1); this.players[i] = new YahtzeeScoreboard(name);}
			}
			else {this.computer[i-numUsers] = new ComputerPlayer("CPU" + (i-numUsers+1));}
		}
	}
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean samePlayers = false;
		boolean ended = false;
		boolean done = false;
		int numUsers = 0;
		int numCPU = 0;
		YahtzeeSim game = null;
		int count;
		
		// starts new session
		while(!ended) {
			count = 0;
			//starts new turn
			while(!done) {
				// starts new game
				if(!samePlayers) {
					boolean set = false;
					while(!set) {
						// requests number of players
						try {
							System.out.println("How many computer players?");
							numCPU = Integer.parseInt(br.readLine());
							System.out.println("How many players?");
							numUsers = Integer.parseInt(br.readLine());
							set = true;
						}catch(NumberFormatException e) {
							System.out.println("Invalid input - enter interger values.\n");
						}catch(IOException f) {
							System.out.println("Error reading input. Please try again.\n");
						}
					}
					// set up game based on input
					game = new YahtzeeSim(numUsers, numCPU, br); samePlayers = true;
				}
				// determines who's turn it is
				int id = game.rotation.getNext();
				if(id < numUsers) {
					System.out.println("\nIt's " + game.players[id].getName() + "'s turn.");
					done = ended = YahtzeeTurn.play(game.players, game.computer, game.players[id], game.playerDice, count+1, br);
				}else {
					System.out.println("\nIt's " + game.computer[id-numUsers].getName() + "'s turn.");
					YahtzeeComputer.computerPlay(id-numUsers, game.computer[id-numUsers], game.playerDice);
				}
				if(id == game.numPlayers-1) {
					count++;
					if(count == 13) done = true;
				}
			}
			// prints scores
			System.out.println("\n");
			for(int i = 0; i < numUsers; i++)
				System.out.println(game.players[i].getName() + "'s score:		" + game.players[i].totalScore());
			for(int j = 0; j < numCPU; j++)
				System.out.println(game.computer[j].getName() + "'s score:		" + game.computer[j].totalScore());
			
			boolean answered = false;
			while(!answered && !ended) {
				System.out.println("\n\nStart new game?");
				try {
					String check = br.readLine();
					if(check.compareToIgnoreCase("yes") == 0){
						System.out.println("Keep current players?");
						String roster = br.readLine();
						if(roster.compareToIgnoreCase("yes") != 0) {
							samePlayers = false;
							done = false;
						}
					}
					else {
						ended = true;
						System.out.println("\nThanks for playing!");
					}
					answered = true;
				}catch(IOException f) {
					System.out.println("Error reading input. Please try again.");
				}
			}
		}try {
			br.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
