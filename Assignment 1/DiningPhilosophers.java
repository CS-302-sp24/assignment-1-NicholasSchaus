import java.io.File;  // Import the File class
import java.io.FileWriter;  // Import FileWriter Class
import java.io.IOException;  // Import the IOException class to handle errors



public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException, IOException {
    /* Usage details:
    Enter either 0 or 5 args when running main
    in order, these args should represent numbers convertable to integers, with *rl* being limited to 0 or 1
      [np, nc, tt, et, rl]
    as described in the readme in the assignment 1 folder. Should you enter no additional args, a base case will be appointed
    implemented just below.

    On running main, a file named "Trace.txt" will be created if not already, or overwritten if made, that will store all
    logs for the philosophers. Any exceptions that occur during runtime will instead print to console.
    */

    // For testing, if no inputs provided
    if(args.length == 0) {
      String[] temp = {"5", // Number of philosophers
                        "0", // Number of cycles (how large their meal is) (0 runs forever/until deadlock)
                        "500", // Max thinking time in ms (must be higher than max eating time, by a reasonable margin)
                        "300", // Max eating time in ms
                        "1" // Dominant Hand mix (0 or 1)
                      };
      args = temp;
    }

    //Create output File
    try {
      File Trace = new File("Trace.txt");
      if (Trace.createNewFile()) {
        System.out.println("File created: " + Trace.getName());
      } else { 
        //If file already exists, recreate file to reset log
        System.out.println("File already exists.");
        Trace.delete();
        Trace.createNewFile();
        System.out.println("File reset: " + Trace.getName());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Create file Writer (how to get around without try statement?)
    FileWriter traceWriter = new FileWriter("Trace.txt");

    // Set up Philosophers
    int GatheringSize = 1; // *np* is the number of philosophers (and forks).
    int MaxCycles = 1; // *nc* is the number of cycles that each philosopher will go through. If this parameter is 0, they run forever.
    int MaxThinkingTime = 1; // *tt* is the maximum thinking time, in units of milliseconds.
    int MaxEatingTime = 1;// *et* is the maximum eating time, in units of milliseconds.
    int DominantHand = 0; // If *rl* is 0, then all philosophers are right-handed and try to grab their right chopstick first. If *rl* is 1, then even-numbered 
    assert (args.length == 5);
    try {
      GatheringSize = Integer.valueOf(args[0]);
      MaxCycles = Integer.valueOf(args[1]);
      MaxThinkingTime = Integer.valueOf(args[2]);
      MaxEatingTime = Integer.valueOf(args[3]);
      DominantHand = Integer.valueOf(args[4]);
    } catch (NumberFormatException n) {
      System.out.println("Inputs invalid, must input numbers as arguments");
      System.out.println(n.getStackTrace());
      System.exit(-1);
    }
    assert (DominantHand == 1 || DominantHand == 0);
    
    //Print parameters to file as Header
    traceWriter.write("Parameters:\n[np: " + Integer.toString(GatheringSize) + 
                      ", nc: " + Integer.toString(MaxCycles) + 
                      ", tt: " + Integer.toString(MaxThinkingTime) + 
                      ", et: " + Integer.toString(MaxEatingTime) + 
                      ", rl: " + Integer.toString(DominantHand) + "]\n\n");

    // Simulate philosophers
    Philosopher[] philosophers = new Philosopher[GatheringSize];
    Chopstick[] chopsticks = new Chopstick[GatheringSize];
    
    for (int i = 0; i < GatheringSize; ++i)
      chopsticks[i] = new Chopstick(i);
    for (int i = 0; i < GatheringSize; ++i) {
      if(DominantHand == 1) {
        philosophers[i] = new Philosopher(Integer.toString(i), chopsticks[i], chopsticks[(i + 1) % GatheringSize], MaxCycles, MaxEatingTime, MaxThinkingTime, i%2==0, traceWriter);
      }
      else {
        philosophers[i] = new Philosopher(Integer.toString(i), chopsticks[i], chopsticks[(i + 1) % GatheringSize], MaxCycles, MaxEatingTime, MaxThinkingTime, true, traceWriter);
      }
      philosophers[i].start();
    }
    for (int i = 0; i < GatheringSize; ++i)
      philosophers[i].join();
















    traceWriter.close();
  } 

}
