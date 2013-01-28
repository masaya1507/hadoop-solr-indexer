hadoop-solr-indexer
=============
Delete All Indexes by curl

curl http://10.211.55.16:8983/solr/collection1/update/?commit=true -H "Content-Type: text/xml" -d "<delete>*:*</delete>"