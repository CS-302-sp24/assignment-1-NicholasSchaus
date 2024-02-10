import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;


class Philosopher extends Thread {
  private String name;
  private Chopstick left, right;
  private boolean rightHanded;
  private Random random;
  private int MaxCycles;
  private int MaxThinkingTime;
  private int MaxEatingTime;
  private FileWriter writer;
  private long curTime;

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
      do {
        // FINISH
        curTime = System.currentTimeMillis();

        // Eating

        // Grabbing chopsticks, (right hand dominant)
        if(rightHanded) {

          // Prints want right chopstick
          try {
            writer.write("Philosopher " + name + " wants right chopstick" + "\n");
          } catch (IOException io) {
            System.out.println("Failed to print line in Philosopher " + name);
            io.printStackTrace(); }

          // Deadlock Check
          MTC mtcThread = new MTC(currentThread(), "grab right chopstick");
          mtcThread.start();

          // Grab right chopstick
          synchronized(right) {

            // Deadlock Check Complete
            mtcThread.interrupt();

            // Prints has right chopstick
            try {
              writer.write("Philosopher " + name + " has right chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            
            /*****************************************************************************************************************/
            //inserted pause to force deadlock. Comment out if not desired, will not affect program

            // try {
            //   writer.write("Philosopher " + name + " WAITING TO FORCE DEADLOCK" + "\n");
            // } catch (IOException io) {
            //   System.out.println("Failed to print line in Philosopher " + name);
            //   io.printStackTrace(); }
            // Thread.sleep(2000);

            /*****************************************************************************************************************/

            // Prints wants left chopstick
            try {
              writer.write("Philosopher " + name + " wants left chopstick" + "\n");
            } catch (IOException io) {
              System.out.println("Failed to print line in Philosopher " + name);
              io.printStackTrace(); }
            
            // Deadlock Check
            mtcThread = new MTC(currentThread(), "grab left chopstick");
            mtcThread.start();

            // Grab Left chopstick
            synchronized(left) {

              // Deadlock Check Complete
              mtcThread.interrupt();


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

          // Deadlock Check
          MTC mtcThread = new MTC(currentThread(), "grab left chopstick");
          mtcThread.start();

          // Grab right chopstick
          synchronized(left) {

            // Deadlock Check Complete
            mtcThread.interrupt();

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

            // Deadlock Check
            mtcThread = new MTC(currentThread(), "grab left chopstick");
            mtcThread.start();
            
            // Grab Left chopstick
            synchronized(right) {

              // Deadlock Check Complete
              mtcThread.interrupt();

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
      } while(CurrentCycle != MaxCycles);
      try {
        writer.write("Philosopher " + name + " has finished their meal!!!!!!!!!!!!!!!!!!!!!!" + "\n");
      } catch (IOException io) {
        System.out.println("Failed to print line in Philosopher " + name);
        io.printStackTrace();
      } 
    } catch (InterruptedException e) {} 
  }

  // Interruption attempt
  /*
  Goal: Create a second thread that checks if the first thread is still valid to run
  (invalid case being time is past the maximum allotted thinking time)

  inputs: (OriginThread)
  outputs: Void, but throw InterruptedException() on origin thread
  ?? Return thread to allow for cancellation??
  Create thread,
  in origin thread, run this threads "run" function

  run()
    while (!(self.interrupted())) {
      if(currenttime - GETtimelasteaten() >= MaxThinkingTime) {
        OriginThread.interrupt()
        self.interrupt()
      }
    }
   */

  class PhilosopherStarvedException extends Exception {
    private String s;
    public PhilosopherStarvedException(String s) {
      this.s = s;
    }

    //return message
    String getM() {
      return s;
    }
  }
  
  // Generic class to create threads
  private class MTC extends Thread {
    private Thread t;
    private String s;
    // Thread t is the parent thread, String s is a message should an exception arise
    public MTC(Thread t, String s) {
      this.t = t;
      this.s = s;
    }
    
    public void run() {
      try {
        while(!interrupted()) {
          if(System.currentTimeMillis() - curTime >= MaxThinkingTime) {
            t.interrupt();
            throw new PhilosopherStarvedException(s);
          }
        }
      } catch (SecurityException s) {
        s.printStackTrace();
      } catch (PhilosopherStarvedException pse) {
        try {
          writer.write("Philosopher " + name + " has Starved!!!!!!!!!!!!!!!!!!!!!!" + "\n" +
                       "Philosopher " + name + " starved because they could not " + s + "!\n");
        } catch (IOException io) {
          System.out.println("Failed to print line in Philosopher " + name);
          io.printStackTrace();
        } 
        
        // Pausing to show if other threads are dying concurrently (its quite a long pause but want to see)
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // Do nothing, intended behavior
        }

        // Close writer to save output file
        try {
          writer.close();
          System.exit(0);
        } catch (IOException io) {}
      }
    }
  }
}
