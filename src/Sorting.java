/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement the sorting methods.
 *
 * @author Chirayu Garg, TODO: add your name here
 * @username garg104, TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * DO NOT COPY PASTE THE ALGORITHMS FOR SORTING METHODS FROM THE INTERNET. WE WILL KNOW!
 *
 */

import java.util.ArrayList;

public class Sorting<Item extends Comparable<Item>> {


    /**
     *
     * Default Constructor
     *
     */
    public Sorting() {
    }

    /**
     *
     * returns true if the Item at index @param i in @ param list is greater than Item at index @param j
     * else return false
     *
     * @param list
     * @param i
     * @param j
     * @return
     */
    public boolean greaterThan(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) > 0;
    }

    /**
     *
     * returns true if the Item @param i is greater than Item @param j else return false
     *
     * @param i
     * @param j
     * @return
     */
    public boolean greaterThan(Item i, Item j) {
        return i.compareTo(j) > 0;
    }


    /**
     *
     * returns true if the Item at index @param i in @ param list is lesser than Item at index @param j
     * else return false
     *
     * @param list
     * @param i
     * @param j
     * @return
     */
    public boolean lessThan(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) < 0;
    }

    /**
     *
     * returns true if the Item @param i is less than Item @param j else return false
     *
     * @param i
     * @param j
     * @return
     */
    public boolean lessThan(Item i, Item j) {
        return i.compareTo(j) < 0;
    }


    /**
     *
     * returns true if the Item @param i is equal to Item @param j else return false
     *
     * @param i
     * @param j
     * @return
     */
    public boolean equal(Item i, Item j) {
        return i.compareTo(j) == 0;
    }


    /**
     *
     * returns true if the Item at index @param i in @ param list is equal to the Item at index @param j
     * else return false
     *
     * @param list
     * @param i
     * @param j
     * @return
     */
    public boolean equal(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) == 0;
    }


    /**
     * Sorts the list in ascending order using Insertion Sort.
     *
     *
     * @param list
     */
    public ArrayList<Item> insertionSort(ArrayList<Item> list) {
        // TODO: part 1
        int n = list.size();
        for (int i = 1; i < n; ++i) {
            Item key = list.get(i);
            int j = i - 1;
 
            while(j >= 0 && greaterThan(list, j, i))
                j--;

            j++;
            if( j < i )
            {
                list.add(j, list.remove(i));                
            }
        }

        return list;
    }

    public void merge(ArrayList<Item> list, int l, int m, int r)
    {
        //Below is the mergedarray that will be sorted array Array[i-m] , Array[(m+1)-r]
        ArrayList<Item> mergedSortedArray = new ArrayList<Item>();
        
        int leftIndex = l;
        int rightIndex = m+1;
        
        while(leftIndex<=m && rightIndex<=r){
            if( lessThan(list, leftIndex, rightIndex) ){
                mergedSortedArray.add(list.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(list.get(rightIndex));
                rightIndex++;
            }
        }       
        
        //Either of below while loop will execute
        while(leftIndex<=m){
            mergedSortedArray.add(list.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex<=r){
            mergedSortedArray.add(list.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = l;
        //Setting sorted array to original one
        while(i<mergedSortedArray.size()){
            list.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    void mergeSort(ArrayList<Item> list, int l, int r)
    {
        if (l < r && (r - l) >= 1) {
            // Find the middle point
            int m = (r + l) / 2;
 
            // Sort first and second halves
            mergeSort(list, l, m);
            mergeSort(list, m + 1, r);
 
            // Merge the sorted halves
            merge(list, l, m, r);
        }
    }
 
    /**
     *
     * Sorts the list in ascending order using Merge Sort.
     *
     * @param list
     */
    public ArrayList<Item> mergeSort(ArrayList<Item> list) {
        // TODO: part 1
        mergeSort(list, 0, list.size() - 1);

        return list;
    }


    /**
     *
     * prints the Items in the @param list.
     *
     * @param list
     */
    public void print(ArrayList<Item> list) {
        int n = list.size();

        StringBuffer bf = new StringBuffer();
        bf.append(list.get(0).toString());

        for (int i = 1;i < n; i += 1) {
            bf.append(" " + list.get(i).toString());
        }

        System.out.println(bf.toString());
    }


    /**
     *
     * Swaps the element at index @param i with element at index @param j in the @param list.
     *
     * @param list
     * @param i
     * @param j
     */
    private void swap(ArrayList<Item> list, int i, int j) {
        Item temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     *
     * This is the main function to run the Sorting class to help with debugging.
     *
     * @param args
     */
    public static void main(String[] args) {

        Sorting<Integer> s = new Sorting<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        Integer[] K = {4,4,3,1,3,9,8,2,5,6};

        for (Integer k : K) {
            A.add(k);
        }

        s.print(A);

    }

}
