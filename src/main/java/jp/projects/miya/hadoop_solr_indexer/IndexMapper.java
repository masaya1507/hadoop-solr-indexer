package jp.projects.miya.hadoop_solr_indexer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVParser;


/**
 *
 * @author
 *
 */
public class IndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	/**
	 *
	 */
	private static final Logger LOG = LoggerFactory.getLogger(IndexMapper.class);
	private SolrServer _server;
	private CSVParser _csvParser;
	private String[] _csvHeaders;

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#setup(org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Configuration c = context.getConfiguration();
		this._server =  new ConcurrentUpdateSolrServer(
				c.get(Constants.SOLR_URL),
				Integer.parseInt(c.get(Constants.SEND_BUFFER_SIZE)),
				Integer.parseInt(c.get(Constants.REQUEST_THREAD_COUNT)));
		this._csvParser = new CSVParser(',', '"');
		this._csvHeaders = this._csvParser.parseLine(c.get(Constants.CSV_HEADER_PROPERTY));
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		try {
			String[] fields = this._csvParser.parseLine(value.toString());
			if (fields.length == this._csvHeaders.length) {
				SolrInputDocument doc = new SolrInputDocument();
				for (int i = 0; i < fields.length; i++) {
					doc.addField(this._csvHeaders[i], fields[i]);
				}
				this._server.add(doc);
			}
		} catch (SolrServerException ex) {
			IndexMapper.LOG.error(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#cleanup(org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		try {
			this._server.commit();
		} catch (SolrServerException ex) {
			IndexMapper.LOG.error(ex.getMessage());
		}
	}
}
