import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

public class TrustReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {
    public void reduce(IntWritable key, Iterable<NodeOrDouble> values, Context context)
        throws IOException, InterruptedException {
    	double pageRank = 0;
    	Node M = null;
    	for (NodeOrDouble curr : values) {
    		if (curr.isNode()) { // if IsNode(p) then
    			M = curr.getNode(); // M <- p
    		}
    		else {
    			pageRank += curr.getDouble(); // s <- s + p
    		}
    	}
    	M.setPageRank(pageRank);
    	context.write(key, M); // Emit(nid m, node M);
    }
}
