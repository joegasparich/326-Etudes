import java.util.*;

public class RedAndGreen {
	
	public static HashMap<Integer, Character> numColours;

	public static void main(String[] args) {

		numColours = new HashMap<>();
		numColours.put(1, 'G');

		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()) {
			String input = scan.nextLine();
			if(input.length() > 0 && input.charAt(0) != '#') {
				System.out.println(input);
				String[] tokens = input.split(" ");
				if(tokens.length == 2) {
					int startNum = Integer.parseInt(tokens[0]);
					int length = Integer.parseInt(tokens[1]);
					String output = "";
					for(int i=startNum; i < startNum + length; i++) {
						output += solve(i);
					}
					System.out.println("#" + output);
				}
			}
		}
	}

	public static Character solve(int num) {
		int r = 0, g = 0;
		HashSet<Integer> nearFactors = new HashSet<>();

		for(int i=2; i <= num; i++) {
			Integer nearFactor = num / i;
			if(!nearFactors.contains(nearFactor)) {
				nearFactors.add(nearFactor);
				Character colour = numColours.get(nearFactor);
				if(colour == null) {
					colour = solve(nearFactor);
					numColours.put(nearFactor, colour);
				}
				if(colour == 'R') r++;
				if(colour == 'G') g++;
			}
		}
		if(g > r) return 'R';
		return 'G';
	}
}