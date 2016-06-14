import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class LeftoverReducer extends Reducer<IntWritable, Node, IntWritable, Node> {
    public static double alpha = 0.85;
    public void reduce(IntWritable nid, Iterable<Node> Ns, Context context) throws IOException, InterruptedException {
    	Configuration conf = context.getConfiguration();
    	long size = Long.parseLong(conf.get("size"));
    	long leftoverLong = Long.parseLong(conf.get("leftover"));
    	double leftover = ((double) leftoverLong) / 100000000; // calculate m which is leftover here
    	
    	Node node = Ns.iterator().next(); // get the node
    	double pagerank = node.getPageRank(); // get the page rank of this node, which is p
    	
    	double param = ((double) 1.0) / size; // = 1 / G
    	double retVal = alpha * param + (1- alpha) * (leftover * param + pagerank);
    	
    	node.setPageRank(retVal);
    	context.write(nid, node);
    }
}
