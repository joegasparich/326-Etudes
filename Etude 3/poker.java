import java.util.*;

public class poker {
	
	public static HashSet<String> deck;

	public static void main(String[] args) {
			
		Scanner scan = new Scanner(System.in);

		while(scan.hasNextLine()) {
			boolean valid = true;

			//Generate deck
			deck = new HashSet<String>();
			for(int i=0; i<4; i++) {
				for(int j=0; j<13; j++) {
					String card = "";
					switch(j+1){
						case 1: card += "A"; break;
						case 10: card += "T"; break;
						case 11: card += "J"; break;
						case 12: card += "Q"; break;
						case 13: card += "K"; break;
						default: card += Integer.toString(j+1); break;
					}
					switch(i){
						case 0: card += "C"; break;
						case 1: card += "S"; break;
						case 2: card += "D"; break;
						case 3: card += "H"; break;
						default: break;
					}
					deck.add(card);
				}
			}

			//Parse input and convert to standard format
			String input = scan.nextLine();
			String[] hand = input.split("-|\\/|\\ ");
			for(int i=0; i<hand.length; i++) {
				String card = hand[i];
				char suit = Character.toUpperCase(card.charAt(card.length()-1));
				String number = card.substring(0, card.length()-1).toUpperCase();
				switch(number){
					case "1": number = "A"; break;
					case "10": number = "T"; break;
					case "11": number = "J"; break;
					case "12": number = "Q"; break;
					case "13": number = "K"; break;
					default: break;
				}
				card = number + suit;
				hand[i] = card;
				//Check card exists in deck
				if(deck.contains(card)) {
					deck.remove(card);
				} else {
					valid = false;
				}
			}
			//Check hand is valid size
			if(hand.length != 5) valid = false;

			//Print output
			if(valid) {
				hand = sort(hand);
				for(String card : hand) {
					System.out.print(card + " ");
				}
				System.out.println();
			} else {
				System.out.println("Invalid: " + input);
			}
		}
	}

	//Sorts the hand using Arrays.sort through some trickery (pls no bully)
	public static String[] sort(String[] hand) {
		for(int i=0; i<hand.length; i++) {
			if(hand[i].charAt(0) == 'A') hand[i] = "Z" + hand[i].charAt(1);
			if(hand[i].charAt(0) == 'K') hand[i] = "Y" + hand[i].charAt(1);
			if(hand[i].charAt(0) == 'T') hand[i] = "A" + hand[i].charAt(1);
		}
		Arrays.sort(hand);
		for(int i=0; i<hand.length; i++) {
			if(hand[i].charAt(0) == 'Y') hand[i] = "K" + hand[i].charAt(1);
			if(hand[i].charAt(0) == 'A') hand[i] = "10" + hand[i].charAt(1);
			if(hand[i].charAt(0) == 'Z') hand[i] = "A" + hand[i].charAt(1);
		}
		return hand;
	}
}