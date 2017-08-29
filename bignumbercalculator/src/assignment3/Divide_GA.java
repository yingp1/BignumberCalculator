/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.Random;

/**
 *
 * @author ZheHuang
 */
public class Divide_GA{
    private BigNumber b1;
    private BigNumber b2;
    Random rand = new Random(System.currentTimeMillis());
    int[][] popArray;
    int[][] concatArray;
    int intLength, decLength;
    
    public Divide_GA(BigNumber b1, BigNumber b2) {
        this.b1 = b1;
        this.b2 = b2;
    }
    public BigNumber evolve(int POP_SIZE, int MAX_GENS, int CHILD_SIZE, float MUT_RATE) {
        // Set percision
        int percision = 2;
        int[] fitness;
        // Initialize population
        popArray = initialize(POP_SIZE, percision);
        for(int i = 0; i < MAX_GENS; i++) {
            concatArray = reproduce(popArray, CHILD_SIZE, MUT_RATE);
            fitness = evaluate(concatArray);
            popArray = selectSurvivors(concatArray, fitness, POP_SIZE);
        }
        // Output the evolved best result, the first one in the pop is the best one
        String str = "";
        for(int i = 0; i < intLength; i++)
            str = str + Integer.toString(popArray[0][i]);
        str = str + ".";
        for(int i = intLength; i < popArray[0].length - percision; i++) {
            str = str + Integer.toString(popArray[0][i]);
        }
        return new BigNumber(str);
    }
    
    private int[][] initialize(int popSize, int percision) {
        if(b1.getDigits1().getLength() - b2.getDigits1().getLength() >= 0)
            intLength = b1.getDigits1().getLength() - b2.getDigits1().getLength() + 1;
        else
            intLength = 1;
        decLength = b1.getDigits2().getLength() + b2.getDigits2().getLength() + percision;
        int[][] pop = new int[popSize][intLength + decLength];
        for(int i = 0; i < pop.length; i++)
            for(int j = 0; j < pop[0].length; j++)
                pop[i][j] = rand.nextInt(10);
        return pop;
    }
    
    private int[] evaluate(int[][] pop) {
        int[] fitness = new int[pop.length];
        for(int i = 0; i < pop.length; i++) {
            String str = "";
            for(int j = 0; j < intLength; j++)
                str = str + Integer.toString(pop[i][j]);
            str = str + ".";
            for(int j = intLength; j < pop[0].length; j++)
                str = str + Integer.toString(pop[i][j]);
            
            BigNumber parent = new BigNumber(str);
            BigNumber b2_tmp = new BigNumber(b2.toString());
            BigNumber result = parent.multiply(b2_tmp);
            str = result.toString();
            String str2 = b1.toString();
            
            // If no decimal portion, add .0
            if(!str.contains("."))
                str = str + ".0";
            if(!str2.contains("."))
                str2 = str2 + ".0";
            
            String[] s1 = str.split("\\."); // Evolved
            String[] s2 = str2.split("\\."); // Target
            
            //Alignment
            if(s2[0].length() < s1[0].length()) {
                int diff = s1[0].length() - s2[0].length();
                for(int j = 0; j < diff; j++)
                    s2[0] = "0" + s2[0];
            }
            else {
                int diff = s2[0].length() - s1[0].length();
                for(int j = 0; j < diff; j++)
                    s1[0] = "0" + s1[0];
            }
            if(s2[1].length() < s1[1].length()) {
                int diff = s1[1].length() - s2[1].length();
                for(int j = 0; j < diff; j++)
                    s2[1] = s2[1] + "0";
            }
            else {
                int diff = s2[1].length() - s1[1].length();
                for(int j = 0; j < diff; j++)
                    s1[1] = s1[1] + "0";
            }
            // Compare and calculate fitness
            for(int j = 0; j < s2[0].length(); j++)
               if(s1[0].charAt(j) != s2[0].charAt(j))
                   fitness[i]++;
            for(int j = 0; j < s2[1].length(); j++)
               if(s1[1].charAt(j) != s2[1].charAt(j))
                   fitness[i]++;
        }
        return fitness;
    }
    
    private int[][] reproduce(int[][] pop, int childCont,float mutRate) {
        int[][] children = new int[childCont][pop[0].length];
        int cut, cut1, cut2;
        int p1,p2;
        // 2-points crossover
        for(int i = 0; i < childCont; i++) {
            cut1 = rand.nextInt(pop[0].length);
            cut2 = cut1;
            while(cut2 == cut1)
                cut2 = rand.nextInt(pop[0].length);
            p1 = rand.nextInt(pop.length);
            p2 = p1;
            while(p2 == p1)
                p2 = rand.nextInt(pop.length);
            if(cut1 < cut2) {
                System.arraycopy(pop[p2], 0, children[i], 0, cut1);
                System.arraycopy(pop[p1], cut1, children[i], cut1, cut2 - cut1);
                System.arraycopy(pop[p2], cut2, children[i], cut2, pop[0].length - cut2);
            }
            else {
                System.arraycopy(pop[p2], 0, children[i], 0, cut2);
                System.arraycopy(pop[p1], cut2, children[i], cut2, cut1 - cut2);
                System.arraycopy(pop[p2], cut1, children[i], cut1, pop[0].length - cut1);
            }
        }
        // Mutate
        for(int i = 0; i < childCont; i++) {
            cut = rand.nextInt(pop[0].length);
            if(rand.nextDouble() < mutRate)
                children[i][cut] = (children[i][cut] + 1) % 10;
                //children[i][cut] = rand.nextInt(10);
        }
        //int[][] concatpop = append(pop, children);
        // Return children as the next pop, discard currect pop
        return children;
    }

    // Concat two arrays by row (Not in use)
    private int[][] append(int[][] a, int[][] b) {
        int[][] result = new int[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    private int[][] selectSurvivors(int[][] concatpop, int[] fit, int popSize) {
        int[][] newpop = new int[popSize][concatpop[0].length];
        int pointer;
        for(int i = 0; i < popSize; i++) {
            pointer = 0;
            for(int j = 0; j < fit.length; j++) {
                if(fit[pointer] > fit[j])
                    pointer = j;
            }
            newpop[i] = concatpop[pointer];
            // Trace the best result every gen
            if(i == 0) {
                System.out.print("best:");
                for(int val:newpop[i])
                    System.out.print(val);
                System.out.print(" ");
                System.out.print("error:" + fit[pointer]);
                System.out.println(" IntegerLength:" + intLength);
            }
            fit[pointer] = Integer.MAX_VALUE;
        }    
        return newpop;
    }
}
