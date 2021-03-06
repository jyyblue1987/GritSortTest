/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement gritSort.
 *
 * @author Chirayu Garg, TODO: add your name here
 * @username garg104, TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GritSort<Item extends Comparable<Item>> {

    /**
     *
     * Default Constructor
     *
     */
    public GritSort() {
    }

    /**
     *
     * Implement Grit Sort as defined in the Handout.
     * for example:
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     * return value = {1,2,2,3,4,4,5,5,5,6,6,8,10}
     *
     *
     * @param list
     * @return sorted list
     *
     */
    public ArrayList<Item> grit(ArrayList<Item> list) {
        /*
            TODO: part 1
            TODO: STEP 1: implement and call makeChunks on list to divide the list into the sorted chunks.            
            TODO: STEP 1.5: make the required number of buckets(can differ from implementation to implementation) and
            TODO            put each chunk into a bucket of proper size.
            TODO: STEP 2: implement and call mergeChunk to merge the chunks in a bucket. do this for every bucket
            TODO: STEP 3: merge the buckets and return the elements of the merged buckets as a list
         */
        ArrayList<ArrayList<Item>> chunks = makeChunks(list);
        HashMap<Integer, ArrayList<ArrayList<Item>>> buckets = makeBuckets(chunks);

        ArrayList<ArrayList<Item>> sortedBuckets = new ArrayList<ArrayList<Item>>();
		for (Integer k : buckets.keySet())
            sortedBuckets.add(mergeChunk(buckets.get(k)));

        ArrayList<Item> sorted_list = mergeChunk(sortedBuckets);

        return sorted_list;
    }

    /**
     *
     * This function takes in a list and returns an ArrayList of ArrayList.
     * Each chunk found is stored in an array list and then all the chunks are in turn stored in an array list which is
     * then returned.
     *
     * for example
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     *
     * return value = {
     *                     {3,5,6,8,10},
     *                     {2,4,5,6},
     *                     {1,2,4,5}
     *                }
     *
     * @param list
     * @return array list of chunks(sorted sub-lists in the original input list)
     *
     */
    public ArrayList<ArrayList<Item>> makeChunks(ArrayList<Item> list) {
        // TODO: part 1
        ArrayList<ArrayList<Item>> chunks = new ArrayList<ArrayList<Item>>();

		Sorting<Item> sort = new Sorting<Item>();

        Item prev_item = null;
        ArrayList<Item> chunk = null;
        for(int i = 0; i < list.size(); i++)
        {
            Item cur = list.get(i);
            if( prev_item == null || sort.lessThan(cur, prev_item) )
            {
                if( chunk != null && chunk.size() > 0 )
                    chunks.add(chunk);

                chunk = new ArrayList<Item>();
            }

            chunk.add(cur);
            prev_item = cur;
        }

        if( chunk != null && chunk.size() > 0 )
            chunks.add(chunk);

        return chunks;
    }



    /**
    *
    * This function takes in an ArrayList of ArrayList and returns an HashMap<Integer, ArrayList<ArrayList<Item>>> 
    * Chunks of the same size are stored in the same bucket. 
    * Each chunk of the size say S is stored in a bucket which corresponds to that size S.
    * Thus, for the returned HashMap, the key is the chunk size, and the corresponding value is a list of chunks of that size.
    * for example
    * chunks = {
    *              {3,5,6,8,10},
    *              {2,4,5,6},
    *              {1,2,4,5}
    *          }
    *
    * return value = {
    * 					4: {{2,4,5,6},{1,2,4,5}},
    * 					5: {{3,5,6,8,10}}
    * 				  }
    * i.e., the bucket for chunks of size 4 will have {{2,4,5,6},{1,2,4,5}} and
    * bucket for chunks of size 5 will have {{3,5,6,8,10}}
    *
    * @param chunks
    * @return HashMap, where key is the chunk size and value is a list of chunks of that size
    * (sorted sub-lists in the original input list)
    *
    */
    public HashMap<Integer, ArrayList<ArrayList<Item>>> makeBuckets(ArrayList<ArrayList<Item>> chunks) {
    	HashMap<Integer, ArrayList<ArrayList<Item>>> buckets = new HashMap<Integer, ArrayList<ArrayList<Item>>>();
    	// make buckets is a helper function. 
        // CHANGE THIS ACCORDING TO YOUR IMPLEMENTATION
        for(int i = 0; i < chunks.size(); i++)
        {
            ArrayList<Item> cur = chunks.get(i);
            int key = cur.size();
            if( buckets.containsKey(key) )
                buckets.get(key).add(cur);                
            else
            {
                ArrayList<ArrayList<Item>> temp = new  ArrayList<ArrayList<Item>>();
                temp.add(cur);
                buckets.put(key, temp);   
            }
        }
    	
	   	return buckets;
	}




    /**
     *
     * This function takes in a bucket(ArrayList of ArrayList) and returns an ArrayList which has the chunks in
     * the bucket merged.
     *
     * for example
     * bucket = {
     *          {2,4,5,6},
     *          {1,2,4,5}
     *         }
     *
     * return value = {1,2,2,4,4,5,5,6}
     *
     *
     * @param bucket
     * @return merged and sorted list
     *
     */

    public static class Node<Item> {
        Item val;
        int row, pos;

        public Node(Item val, int row, int pos) {
            this.val = val;
            this.row = row;
            this.pos = pos;
        }
    }

    public ArrayList<Item> mergeChunk(ArrayList<ArrayList<Item>> bucket) {
        // TODO: part 1

        ArrayList<Item> rst = new ArrayList<Item>();
        if (bucket == null || bucket.size() == 0) {
            return rst;
        }

        PriorityQueue<Node<Item>> pq = new PriorityQueue<>(bucket.size(), new Comparator<Node<Item>>() {
            @Override
            public int compare(Node<Item> o1, Node<Item> o2) {
                return o1.val.compareTo(o2.val);
            }
        });

        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).size() == 0) continue;

            Node<Item> newNode = new Node<Item>(bucket.get(i).get(0), i, 0);
            pq.offer(newNode);
        }
        while (!pq.isEmpty()) {
            Node<Item> num = pq.poll();
            rst.add(num.val);
            if (num.pos < bucket.get(num.row).size() - 1) {
                pq.offer(new Node<Item>(bucket.get(num.row).get(num.pos + 1), num.row, num.pos + 1));
            }
        }

        return rst;
    }




    /**
     *
     * This is the main function to run the gritSort class to help with debugging.
     *
     * @param args
     */
    public static void main(String[] args) {
        Sorting<Integer> s = new Sorting<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        Integer[] K = {3,5,6,8,10,2,4,5,6,1,2,4,5};

        for (Integer k : K) {
            A.add(k);
        }

        s.print(A);

    }

}
