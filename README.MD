elasticsearch
1. docker pull elasticsearch:7.16.3 
2. docker run -d -p 9200:9200 -p 9300:9300 -e ES_JAVA_POTS="-Xms128m -Xmx128m" -e "discovery.type=single-node" --name elastic_search elasticsearch:7.16.3