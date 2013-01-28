package jp.projects.miya.hadoop_solr_indexer;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SolrIndexerTest {
	@Test
	public void deleteSolrIndexes() throws Exception {
		HttpSolrServer server = new HttpSolrServer("http://10.211.55.16:8983/solr");
		server.deleteByQuery("*:*");
		server.commit();
		server.optimize();
	}
	
	@Test
	public void testLocalRunner() throws Exception {
		SolrIndexer.main(new String[] {
				"-D", Constants.CSV_HEADER_PROPERTY + "=id,zipcde_s,addr_ja",
				"-D", Constants.SOLR_URL + "=http://10.211.55.16:8983/solr",
				"-D", Constants.SEND_BUFFER_SIZE + "=1000",
				"-D", Constants.REQUEST_THREAD_COUNT + "=3",
				"/input"} );
	}
	
	@Test
	public void testDistributeEnv() throws Exception {
		SolrIndexer.main(new String[] {
				"-D", Constants.CSV_HEADER_PROPERTY + "=id,zipcde_s,addr_ja",
				"-D", Constants.SOLR_URL + "=http://10.211.55.16:8983/solr",
				"-D", Constants.SEND_BUFFER_SIZE + "=1000",
				"-D", Constants.REQUEST_THREAD_COUNT + "=3",
				"-D", "mapred.job.tracker=10.211.55.8:8021",
				"-D", "fs.default.name=hdfs://10.211.55.8:8020",
				"-libjars", "/Users/mbp_user/devel/java/hadoop-solr-indexer/target/hadoop-solr-indexer-0.0.1-SNAPSHOT.jar,/Users/mbp_user/.m2/repository/asm/asm/3.1/asm-3.1.jar,/Users/mbp_user/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar,/Users/mbp_user/.m2/repository/com/google/guava/guava/11.0.2/guava-11.0.2.jar,/Users/mbp_user/.m2/repository/com/google/protobuf/protobuf-java/2.4.0a/protobuf-java-2.4.0a.jar,/Users/mbp_user/.m2/repository/com/jcraft/jsch/0.1.42/jsch-0.1.42.jar,/Users/mbp_user/.m2/repository/com/sun/jersey/jersey-core/1.8/jersey-core-1.8.jar,/Users/mbp_user/.m2/repository/com/sun/jersey/jersey-server/1.8/jersey-server-1.8.jar,/Users/mbp_user/.m2/repository/com/thoughtworks/paranamer/paranamer/2.3/paranamer-2.3.jar,/Users/mbp_user/.m2/repository/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar,/Users/mbp_user/.m2/repository/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar,/Users/mbp_user/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar,/Users/mbp_user/.m2/repository/commons-codec/commons-codec/1.2/commons-codec-1.2.jar,/Users/mbp_user/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar,/Users/mbp_user/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar,/Users/mbp_user/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar,/Users/mbp_user/.m2/repository/commons-el/commons-el/1.0/commons-el-1.0.jar,/Users/mbp_user/.m2/repository/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar,/Users/mbp_user/.m2/repository/commons-io/commons-io/2.1/commons-io-2.1.jar,/Users/mbp_user/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar,/Users/mbp_user/.m2/repository/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar,/Users/mbp_user/.m2/repository/commons-net/commons-net/3.1/commons-net-3.1.jar,/Users/mbp_user/.m2/repository/hsqldb/hsqldb/1.8.0.10/hsqldb-1.8.0.10.jar,/Users/mbp_user/.m2/repository/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar,/Users/mbp_user/.m2/repository/javax/servlet/jsp/jsp-api/2.1/jsp-api-2.1.jar,/Users/mbp_user/.m2/repository/junit/junit/4.10/junit-4.10.jar,/Users/mbp_user/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar,/Users/mbp_user/.m2/repository/net/sf/opencsv/opencsv/2.3/opencsv-2.3.jar,/Users/mbp_user/.m2/repository/org/apache/avro/avro/1.7.1.cloudera.2/avro-1.7.1.cloudera.2.jar,/Users/mbp_user/.m2/repository/org/apache/commons/commons-math/2.1/commons-math-2.1.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-annotations/2.0.0-cdh4.1.2/hadoop-annotations-2.0.0-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-auth/2.0.0-cdh4.1.2/hadoop-auth-2.0.0-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-client/2.0.0-mr1-cdh4.1.2/hadoop-client-2.0.0-mr1-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-common/2.0.0-cdh4.1.2/hadoop-common-2.0.0-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-core/2.0.0-mr1-cdh4.1.2/hadoop-core-2.0.0-mr1-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/hadoop/hadoop-hdfs/2.0.0-cdh4.1.2/hadoop-hdfs-2.0.0-cdh4.1.2.jar,/Users/mbp_user/.m2/repository/org/apache/httpcomponents/httpclient/4.1.3/httpclient-4.1.3.jar,/Users/mbp_user/.m2/repository/org/apache/httpcomponents/httpcore/4.1.4/httpcore-4.1.4.jar,/Users/mbp_user/.m2/repository/org/apache/httpcomponents/httpmime/4.1.3/httpmime-4.1.3.jar,/Users/mbp_user/.m2/repository/org/apache/solr/solr-solrj/4.0.0-BETA/solr-solrj-4.0.0-BETA.jar,/Users/mbp_user/.m2/repository/org/apache/zookeeper/zookeeper/3.3.6/zookeeper-3.3.6.jar,/Users/mbp_user/.m2/repository/org/codehaus/jackson/jackson-core-asl/1.8.8/jackson-core-asl-1.8.8.jar,/Users/mbp_user/.m2/repository/org/codehaus/jackson/jackson-mapper-asl/1.8.8/jackson-mapper-asl-1.8.8.jar,/Users/mbp_user/.m2/repository/org/codehaus/woodstox/wstx-asl/3.2.7/wstx-asl-3.2.7.jar,/Users/mbp_user/.m2/repository/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar,/Users/mbp_user/.m2/repository/org/mockito/mockito-all/1.8.5/mockito-all-1.8.5.jar,/Users/mbp_user/.m2/repository/org/mortbay/jetty/jetty/6.1.26.cloudera.2/jetty-6.1.26.cloudera.2.jar,/Users/mbp_user/.m2/repository/org/mortbay/jetty/jetty-util/6.1.26.cloudera.2/jetty-util-6.1.26.cloudera.2.jar,/Users/mbp_user/.m2/repository/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar,/Users/mbp_user/.m2/repository/org/slf4j/slf4j-log4j12/1.6.1/slf4j-log4j12-1.6.1.jar,/Users/mbp_user/.m2/repository/org/slf4j/slf4j-simple/1.6.6/slf4j-simple-1.6.6.jar,/Users/mbp_user/.m2/repository/org/xerial/snappy/snappy-java/1.0.4.1/snappy-java-1.0.4.1.jar,/Users/mbp_user/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar,/Users/mbp_user/.m2/repository/tomcat/jasper-runtime/5.5.23/jasper-runtime-5.5.23.jar,/Users/mbp_user/.m2/repository/xmlenc/xmlenc/0.52/xmlenc-0.52.jar",
				"hdfs:///tmp/solr-input"} );
	}
}
