public class TimeTotal {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please enter the filename.");
			return;
		}
		try {
			TextIO.readFile(args[0]);
		} catch (java.lang.IllegalArgumentException e) {
			System.out.printf("Error: Could not find the file specified: \n\n\t\"" + args[0] + "\"\n");
			System.exit(1);
		}
		int total = 0;
		for (short lineNum = 1; !TextIO.eof(); lineNum++) {
			String line = TextIO.getln();
			if (!(line.startsWith("Monday,") || line.startsWith("Tuesday")
				|| line.startsWith("Wednesday") || line.startsWith("Thursday")
				|| line.startsWith("Friday") || line.startsWith("Saturday")
				|| line.startsWith("Sunday")))
				continue;
			int result;
			try {
				result = parseLine(line);
			} catch (Exception e) {
				System.out.println("Error on line " + lineNum);
				return;
			}
			String day = "";
			for (short pos = 0; line.charAt(pos) != '('; pos++)
				day += line.charAt(pos);
			day.trim();
			System.out.printf("%30s: %d hours %d minutes\n", day, result/60, result%60);
			total += result;
		}
		System.out.println();
		System.out.printf("Total: %d hours and %d minutes, which is $%.2f.", total/60, total%60, ((double)total)/4.0);
		
		
	}
	
	static int parseLine(String str) {
		short pos = 0;
		int total = 0;
		for (; str.charAt(pos) != '('; pos++);
		pos++;
		int startTime=0, stopTime=0;
		while (true) {
			String temp = "";
			for (; str.charAt(pos) != ' '; pos++)
				temp += str.charAt(pos);
			startTime = parseTime(temp);
			temp = "";
			pos+=3;
			for (; str.charAt(pos) != ',' && str.charAt(pos) != ')'; pos++)
				temp += str.charAt(pos);
			stopTime = parseTime(temp);
			total += (stopTime - startTime);
			if (str.charAt(pos) == ')')
				break;
			else
				pos+=2;
		}
		return total;
	}
	
	static short parseTime(String str) {
		short pos = 0;
		int hrs = 0, mins = 0;
		for (; str.charAt(pos) != ':'; pos++)
			hrs = hrs*10 + (int)(str.charAt(pos) - '0');
		pos++; //Skip past colon
		for (short i = 0; i < 2; i++, pos++)
			mins = mins*10 + (int)(str.charAt(pos) - '0');
		if (str.charAt(pos) == 'p' && hrs != 12)
			hrs += 12;
		return (short)(hrs * 60 + mins);
	}
}