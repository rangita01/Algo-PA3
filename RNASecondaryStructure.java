/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNASecondaryStructure;

/**
 *
 * @author Rangita
 */
public class RNASecondaryStructure {
    
    public static void main(String[] args) 
    {
        System.out.println("the maximum number of base pairs are " +maxBasePair("AUGGCUACCGGUCGAUUGAGCGCCAAUGUAAUCAUU")); // example RNA string
    }
    public static int maxBasePair(String rna) 
    {
        int n = rna.length(); 
        int[][] values = new int[n+1][n]; 
        for (int i = 0; i <= 5; i++) 
        {
                for (int j = 0; j<n; j++) 
                {
                        values[i][j] = 0; 
                }
        }
        for (int i = 6; i < n+1; i++) 
        { 
            for (int j = 0; j < n-i+1; j++) 
            { 
                int max = values[i-1][j]; 
                for (int k = j; k < i+j-5; k++) 
                { 
                    if (complement(rna.charAt(k), rna.charAt(i+j-1))) 
                    { 
                        max = Math.max(max, values[k-j][j]+values[i-k+j-2][k+1]+1); 

                    }
                }
                values[i][j] = max; 
            }
        }
        return values[n][0];
    }
    public static boolean complement(char a, char b) 
    {
	return a=='A'&&b=='U' || a=='U'&&b=='A' || a=='G'&&b=='C' || a=='C'&&b=='G';
    }
}
