package com.kaloyanlazarov.sap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Homework1 {

    public static ArrayList<Integer> stoveTimers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int testCases = scanner.nextInt();

        for (int i=0; i < testCases; i++) {
            int mekici = scanner.nextInt();
            int stoves = scanner.nextInt();

            scanner.nextLine(); //Used to fix the difference between nextInt() and nextLine() in reading"\n"

            String[] readStovesSpeed = scanner.nextLine().split(" ");
            //read the entire line at once so that we have all speeds in one object
            int[] stovesSpeed = new int[stoves];

            Arrays.sort(stovesSpeed); // Sorting the array => makes finding the solution easier and more understandable

            for (int j = 0; j < stovesSpeed.length; j++)
                stovesSpeed[j] = Integer.parseInt(readStovesSpeed[j]); // convert the String[] to int[]

            for (int j=0; j < stoves; j++) {
                stoveTimers.add(0); // populating the list with zeroes, as every stove starts from time=0
            }

            int solution = solve(mekici, stovesSpeed);

            System.out.println(solution);
        }
    }

    private static int solve(int mekici, int[] stovesSpeed) {
        /*
          Taking the greedy approach
          We place a mekica every time a stove is Open
          We make corrections every time a stove becomes too slow
         */

        if (mekici == 0) { //this is the bottom of the recursion, we have fried all mekici
            return findMax(stoveTimers);
        }

        int nextStove = findNextStove(mekici, stovesSpeed); //index of stove on which we are placing the next mekica
        //to take into account a mekica is placed,
        //+we add the time for frying a mekica to the time the stove has been running
        stoveTimers.set(nextStove, stoveTimers.get(nextStove) + stovesSpeed[nextStove]);

        // recursive call, as we are having the same problem but this time with one mekica less
        return solve(mekici-1, stovesSpeed);
    }

    private static int findNextStove(int mekici, int[] stovesSpeed) {
        /*
          This function decides if we continue with the greedy approach
          OR
          We are having a faster stove
         */
        int indexNextOpenStove = findIndexOfMin(stoveTimers); // this is the greedy approach
        //but we need to check if this is the best decision available
        //+ we need to know if we have a stove, which can finish the entire task faster than the NextOpenStove can fry 1 mekica

        for (int i=0; i < indexNextOpenStove; i++) {
            int timeToFinishTask = (stoveTimers.get(i) - stoveTimers.get(indexNextOpenStove)) + (stovesSpeed[i] * mekici);
            //(first) we find the time it takes for stove[i] to become Open
            //(second) we add the time required to finish the Task
            if (timeToFinishTask <= stovesSpeed[indexNextOpenStove]) {
                //If this is true => the NextAvailableStove is no longer needed to finish the task
                stoveTimers.remove(indexNextOpenStove);

                //if we have a faster stove, we return the index of the faster stove
                return i;
            }
        }
        // If we do not find a faster stove, we continue with the greedy approach
        return indexNextOpenStove;
    }

    private static int findMax(ArrayList<Integer> array) {
        /*
          Basic finding the max value in an array
         */
        int max = array.get(0);
        for(int i=1; i < array.size(); i++) {
            max = Math.max(max, array.get(i));
        }
        return max;
    }

    private static int findIndexOfMin(ArrayList<Integer> array) {
        /*
          I care for the index of the item, not the value
          So the realisation of the method differs a bit from findMax()
         */
        int min = array.get(0);
        int index = 0;
        for(int i=1; i < array.size(); i++) {
            if (array.get(i) < min) {
                min = array.get(i);
                index = i;
            }
        }
        return index;
    }
}