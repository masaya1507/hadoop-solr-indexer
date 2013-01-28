package jp.projects.miya.hadoop_solr_indexer;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SolrIndexerTest {
	@Test
	public void testMain() throws Exception {
		SolrIndexer.main(new String[] {
				"-D", Constants.CSV_HEADER_PROPERTY + "=id,zipcde_s,addr_ja",
				"-D", Constants.SOLR_URL + "=http://10.211.55.16:8983/solr",
				"-D", Constants.SEND_BUFFER_SIZE + "=1000",
				"-D", Constants.REQUEST_THREAD_COUNT + "=3",
//				"-D", "mapred.job.tracker=10.211.55.8:8021",
//				"-D", "fs.default.name=10.211.55.8:8020",
				"input"} );
	}
}
