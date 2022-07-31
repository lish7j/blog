elasticsearch
1. docker pull elasticsearch:7.16.3 
2. docker run -d -p 9200:9200 -p 9300:9300  -e ES_JAVA_POTS="-Xms128m -Xmx128m" -e "discovery.type=single-node" --name elastic_search elasticsearch:7.16.3
测试
3.docker run --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms128m -Xmx128m" -v /Users/bigo/dockerdata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml  -v /Users/bigo/dockerdata/elasticsearch/data/:/usr/share/elasticsearch/data  -v /Users/bigo/dockerdata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.16.3