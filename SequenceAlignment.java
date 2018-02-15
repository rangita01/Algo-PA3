/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencealignment;

import java.util.Arrays;

/**
 *
 * @author Rangita
 */
public class SequenceAlignment 
{
    static final int gp = 1;
    static final int vv = 2;
    static final int cc = 2;
    static final int vc = 2;
    Character[] vowels = new Character[] {'a', 'e', 'i', 'o', 'u'};
    Character[] consonants = new Character[] {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
    int[][] mTable;
    int[][][] pIndx;
    public static void main(String[] args) 
    {
        SequenceAlignment s = new SequenceAlignment();
        s.OPT("AGGCTATCACCTGACCTCCAGGCCGATGCCC","TAGCTATCACGACCGCGGTCGATTTGCCCGAC");
    }
    public void OPT(String seq1, String seq2)
    {
        seq1 = seq1.toLowerCase();
        seq2 = seq2.toLowerCase();
        seq1 = seq1.trim();	
        seq2 = seq2.trim();
        seq1 = seq1.replaceAll(" ", "");	
        seq2 = seq2.replaceAll(" ", "");
        seq1 = " " + seq1;		
        seq2 = " " + seq2;
        mTable = new int[seq1.length()][seq2.length()];
        pIndx = new int[seq1.length()][seq2.length()][2];
        for(int i=0; i<seq1.length(); i++){
                mTable[i][0] = i * gp;		
                pIndx[i][0][0] = i-1;
                pIndx[i][0][1] = 0;
        }
        for(int j=0; j<seq2.length(); j++){
                mTable[0][j] = j * gp;		
                pIndx[0][j][0] = 0;
                pIndx[0][j][1] = j-1;
        }
        pIndx[0][0][0] = -1;
        pIndx[0][0][1] = -1;
        for(int j=1; j<seq2.length(); j++)
        {
            for(int i=1; i<seq1.length(); i++)
            {
                int bothAligned = mismatchPenalty(seq1.charAt(i), seq2.charAt(j)) + mTable[i-1][j-1];	
                int seq1WithGap = gp+mTable[i-1][j];	
                int seq2WithGap = gp+mTable[i][j-1];		
                if(bothAligned<=seq1WithGap && bothAligned<=seq2WithGap)
                {		
                    mTable[i][j] = bothAligned;
                    pIndx[i][j][0] = i-1;
                    pIndx[i][j][1] = j-1;
                }
                else if(seq1WithGap<=bothAligned && seq1WithGap<=seq2WithGap)
                {	
                    mTable[i][j] = seq1WithGap;
                    pIndx[i][j][0] = i-1;
                    pIndx[i][j][1] = j;
                }
                else
                {									
                    mTable[i][j] = seq2WithGap;
                    pIndx[i][j][0] = i;
                    pIndx[i][j][1] = j-1;
                }
            }
        }
        System.out.println(mTable[seq1.length()-1][seq2.length()-1] + " is the Minimum penalty " );
        findAlignment(seq1, seq2);
    }
    public int mismatchPenalty(char char1, char char2)
    {
        if(char1==char2)
        {		
            return 0;
        }
        else if(Arrays.asList(consonants).contains(char1) && Arrays.asList(consonants).contains(char2))
        {
            return cc;
        }
        else if(Arrays.asList(vowels).contains(char1) && Arrays.asList(vowels).contains(char2))
        {
            return vv;
        }
        return vc;
    }
   private void findAlignment(String seq1, String seq2)
   {
        String s1Aligned = "";		
        String s2Aligned = "";
        int i = seq1.length()-1;	
        int j = seq2.length()-1;
        while(i>0 && j>0)
        {
            if(mTable[i][j] - mismatchPenalty(seq1.charAt(i), seq2.charAt(j)) == mTable[i-1][j-1])
            {		
                s1Aligned = seq1.charAt(i) + s1Aligned;
                s2Aligned = seq2.charAt(j) + s2Aligned;
                i=i-1;
                j=j-1;
            }
            else if(mTable[i][j] - gp == mTable[i-1][j])
            {		
                s1Aligned = seq1.charAt(i) + s1Aligned;
                s2Aligned = "-" + s2Aligned;
                i=i-1;
            }
            else if(mTable[i][j] - gp == mTable[i][j-1])
            {		
                s2Aligned = seq2.charAt(j) + s2Aligned;
                s1Aligned = "-" + s1Aligned;
                j=j-1;
            }
        }
        while(i>0)
        {
            s1Aligned = seq1.charAt(i) + s1Aligned;
            s2Aligned = "-" + s2Aligned;
            i=i-1;
        }
        while(j>0)
        {		
            s2Aligned = seq2.charAt(j) + s2Aligned;
            s1Aligned = "-" + s1Aligned;
            j=j-1;
        }
        System.out.println("\nOptimal Alignment:\n" +s1Aligned+"\n"+s2Aligned + "\n\n");
    }
}
