import java.util.ArrayList;

public class Main {

    private Process cordinator;

    public static void main(String[] args) {
        ArrayList myProcess = new ArrayList<Process>();
        for (int i = 0; i < 10; i++) {
            Process process = new Process(i);
            myProcess.add(process);
        }
    }
}