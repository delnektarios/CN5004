/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author teacher
 */
public class MyThreads implements Runnable {

    static int sleepTime(){
        System.out.println("what thread called me? : " + Thread.currentThread().getName());
        Random rand = new Random();
        int minduration = 1000;
        int maxduration = 5000;
        
        int random_time = minduration + rand.nextInt(maxduration - minduration + 1); 
        
        return random_time;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int argSize = 10;
        
        // System.out.println("i am the main : " + Thread.currentThread().getName());
        // sleepTime();
        Thread myThreads[] = new Thread[argSize];
        for (int j = 0; j < argSize; j++) {
            myThreads[j] = new Thread(new MyThreads());
            myThreads[j].start();
        }
        for (int j = 0; j < argSize; j++) {
            try {
                myThreads[j].join(); //todo add catch exception
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThreads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("simulation finished!");
    }

    @Override
    public void run() {
        System.out.println("I am " + Thread.currentThread().getName() + " and i am going to sleep");
        try {
            Thread.sleep(sleepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(MyThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Is waking up " + Thread.currentThread().getName());
    }

}
