import java.util.ArrayList;

public class Main {

	private Process cordinator;

	public static void main(String[] args) {
		var myProcess = new ArrayList<Process>();
		for (var i = 0; i < 10; i++) {
			var process = new Process(i);
			myProcess.add(process);
		}
	}
}