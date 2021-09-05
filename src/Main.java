/* MATH 3020 - Probability and Statistics for Computer Science
   Computer Project
   Name: Shaheen Salim Rahimani
   This program simulates a computer network infected with a virus. Details are on
   exercise 5.9 of the textbook.
 */

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		//Ask the user for the input.
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the number of computers in the network: ");
		int numPC = input.nextInt();
		
		System.out.println("Please enter the probability of infection: ");
		double p = input.nextDouble();
		
		System.out.println("Please enter the number of computers fixed each day: ");
		int fix = input.nextInt();
		
		System.out.println("Please enter the number of runs: ");
		int numOfRuns = input.nextInt();
		input.close();

		//This array will keep track of the number of days for each run.
		int[] numOfDays = new int[numOfRuns];
		
		//This array will keep track of the total number of infected for each run.
		int[] numOfInfected = new int[numOfRuns];
		
		//This variable tracks if all computers were infected at least once.
		int atLeastOnce = 0;
		
		//This loop repeats the simulation
		for(int run = 0; run < numOfRuns; run++) {
			//Variables to keep track of statistics of each run
			int wasAllInfected = 1;
			int day = 1;
			int count = 0;
			//Array that keeps track of the status of each computer (Infected, not Infected)
			int[] isInfected = new int[numPC];
			//Array that keeps track if each computer has been infected once
			int[] wasInfected = new int[numPC];
			
			//The first computer is infected on day 1
			isInfected[0] = 1;
			wasInfected[0] = 1;
			int infected = 1;
			
			
			//While there are infected computers, continue the simulation
			while(infected != 0) {
				day++;
				for(int i = 0; i < numPC; i++) {
					for(int j = 0; j < numPC; j++) {
						//For each infected, do a trial and ,if successful, mark
						// the new computer as newly infected (2)
						if(isInfected(p) == 1 && isInfected[i] == 1 && isInfected[j] == 0) {
							isInfected[j] = 2;
							wasInfected[j] = 1;
							count++;
						} 
					}
				}
				
				//Change all newly infected (2) to infected (1)
				for(int i = 0; i <numPC; i++) {
					if(isInfected[i] == 2) {
						isInfected[i] = 1;
					}
				}
				
				//Update the total count of infected computers
				infected = infected + count; 
				count = 0;
				
				
				//If the number of computers infected is less than the number
				// of computers fixed, fix all computers
				if(infected < fix) {
					infected = 0;
					for(int i = 0; i <numPC; i++) {
						if(isInfected[i] == 1) {
							isInfected[i] = 0;
						}
					}
				
				//Else, fix the computers and update the infected count
				} else {
					infected = infected - fix;
					int fixed = 0;
					for(int i = 0; i <numPC; i++) {
						if(isInfected[i] == 1 && fixed < fix) {
							isInfected[i] = 0;
							fixed++;
						}
					}
				}
			}
			
			//This loop checks if all computers were infected at least once
			for(int i = 0; i < wasInfected.length; i++) {
				if(wasInfected[i] != 1) {
					wasAllInfected = 0;
				}
			}
			
			//This loop calculates the total number of computers infected in the run
			int totalInfected = 0;
			for(int i = 0; i < wasInfected.length; i++) {
				totalInfected += wasInfected[i];
			}
			
			//Update the arrays with the results
			numOfInfected[run] = totalInfected;
			numOfDays[run] = day;
			
			//Update the counter if all computer were infected at least once
			if(wasAllInfected == 1) {
				atLeastOnce++;
			}
		}
		//The main part of the simulation ended. Now it's time to get data from the
		// the simulation
		
		System.out.println(Arrays.toString(numOfDays));
		System.out.println(Arrays.toString(numOfInfected));
		
		//Calculate the sum of all the days to calculate the expectation 
		//of the number of days
		int sumOfDays = 0;
		for(int i = 0; i < numOfRuns; i++) {
			sumOfDays += numOfDays[i];
		}
		
		//Calculate the sum of the number of computers infected
		int sumOfinfected = 0;
		for(int i = 0; i < numOfRuns; i++) {
			sumOfinfected+= numOfInfected[i];
		}
		
		//Calculate the values required from the values gained from the simulation
		double meanOfDays = (double) sumOfDays/numOfRuns;
		System.out.println("The average number of days is: " + meanOfDays + " days");
		double probability = (double) atLeastOnce/numOfRuns;
		System.out.println("The probability that each computer is infected "
				+ "at least once is: " + probability + " = " + probability*100 + "% ");
		double meanOfInfected = (double) sumOfinfected/numOfRuns;
		System.out.println("The average number of infected computers is: " + meanOfInfected + " computers");

	}
	
	//Thia method performs a Bernoulli Trial
	public static int isInfected(double p) {
		Random rand = new Random();
		double U = rand.nextDouble();
		
		if(U < p) {
			return 1;
		} else {
			return 0;
		}
	}
	
}

