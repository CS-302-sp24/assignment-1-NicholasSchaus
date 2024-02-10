import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;


class Philosopher extends Thread {
  private String name;
  private Chopstick left, right;
  private boolean rightHanded;
  private Random random;
  private int thinkCount;
  private int MaxCycles;
  private int MaxThinkingTime;
  private int MaxEatingTime;
  private FileWriter writer;

  public Philosopher(String name, Chopstick left, Chopstick right, int MaxCycles, int MaxEatingTime, int MaxThinkingTime, boolean righthanded, FileWriter writer) {
    // Oh man that got long, hopefully you dont run too many philosophers at once, might hog a lot of memory.
    this.name = name;
    this.left = left; 
    this.right = right;
    this.rightHanded = righthanded;
    this.MaxCycles = MaxCycles;
    this.MaxEatingTime = MaxEatingTime;
    this.MaxThinkingTime = MaxThinkingTime;
    this.writer = writer;
    random = new Random();
  }

  public void run() {
    try {
      int CurrentCycle = 0;
      while(CurrentCycle < MaxCycles) {
        // FINISH
        long curTime = System.currentTimeMillis();

        // Eating

        // Grabbing chopsticks, (right hand dominant)
        if(rightHanded) {

          // Prints want right chopstick
          try {
            writer.write("Philosopher " + name + " wants right chopstick" + "\n");
          } catch (IOException io) {
            System.out.println("Failed to print line in Philosopher " + name);
            io.printStackTrace(); }

          // Grab right chopstick
          synchronized(right) {

            // Prints has right chopstick
            try {
              writer.write("Philosopher " + name + " has right chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            

            //FINISH
            //Pause to force deadlock
            try {
              writer.write("Philosopher " + name + " WAITING TO FORCE DEADLOCK" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            Thread.sleep(2000);


            // Prints wants left chopstick
            try {
              writer.write("Philosopher " + name + " wants left chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            
            // Grab Left chopstick
            synchronized(left) {

              // Prints has left chopstick
              try {
                writer.write("Philosopher " + name + " has left chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace(); }
              
              // prints time waited since last meal
              long t = System.currentTimeMillis() - curTime;
              try {
                writer.write("Philosopher " + name + " thought for " + t + " units of time" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }

              // prints eat for a bit, then eats
              int r = random.nextInt(MaxEatingTime);
              try {
                writer.write("Philosopher " + name + " eats for " + Integer.toString(r) + " units" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
              Thread.sleep(r);

              //Release chopsticks
              try {
                writer.write("Philosopher " + name + " releases left chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
              try {
                writer.write("Philosopher " + name + " releases right chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
            }
          }
          // Increment Cycle Count
          ++CurrentCycle;

        //Grabbing chopsticks (Left hand dominant)
        } else {

          // Prints want left chopstick
          try {
            writer.write("Philosopher " + name + " wants left chopstick" + "\n");
          } catch (IOException io) {
            System.out.println("Failed to print line in Philosopher " + name);
            io.printStackTrace(); }

          // Grab right chopstick
          synchronized(left) {

            // Prints has left chopstick
            try {
              writer.write("Philosopher " + name + " has left chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }

            // Prints wants left chopstick
            try {
              writer.write("Philosopher " + name + " wants right chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            
            // Grab Left chopstick
            synchronized(right) {

              // Prints has right chopstick
              try {
                writer.write("Philosopher " + name + " has right chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace(); }

              // prints time waited since last meal
              long t = System.currentTimeMillis() - curTime;
              try {
                writer.write("Philosopher " + name + " thought for " + t + " units of time" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }

              // prints eat for a bit, then eats
              int r = random.nextInt(MaxEatingTime);
              try {
                writer.write("Philosopher " + name + " eats for " + Integer.toString(r) + " units" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
              Thread.sleep(r);

              //Release chopsticks
              try {
                writer.write("Philosopher " + name + " releases left chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
              try {
                writer.write("Philosopher " + name + " releases right chopstick" + "\n");
              } catch (IOException io) {
                System.out.println("Failed to print line in Philosopher " + name);
                io.printStackTrace();
              }
            }
          }
          // Increment Cycle Count
          ++CurrentCycle;
        }
      }
      try {
        writer.write("Philosopher " + name + " has finished their meal!!!!!!!!!!!!!!!!!!!!!!" + "\n");
      } catch (IOException io) {
        System.out.println("Failed to print line in Philosopher " + name);
        io.printStackTrace();
      } 
    } catch(InterruptedException e) {}
  }
}
