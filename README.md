# DataMonitoringPOC
Reading data from Elasticsearch and analysing it in spark cluster with delta tables.


### 1. Spark setup:


Install java:
brew cask install java

Install scala:
brew install scala

Install apache-spark:
brew install apache-spark

Install Zeppelin:
brew install apache-zeppelin
 
* In my case there was an issue while installing apache-zeppelin using homebrew. Hence installed the binary file from apache-zeppelin _https://zeppelin.apache.org/download.html_ (https://zeppelin.apache.org/download.html) 


*→ Setup:*

    * Add JAVA_HOME & SPARK_HOME in ~/.zshrc & zeppelin-env.sh
    * $HOME/zeppelin-0.8.2-bin-all/conf/

 cp zeppelin-env.sh.template zeppelin-env.sh -> add Add JAVA_HOME & SPARK_HOME
 cp zeppelin-site.xml.template zeppelin-site.xml -> change port number 

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.5.jdk/Contents/Home/
export SPARK_HOME=/usr/local/Cellar/apache-spark/2.4.4/libexec/


### 2. Elasticsearch setup:

https://github.com/elastic/homebrew-tap

*Install Elasticsearch*
 brew tap elastic/tap
 brew install elastic/tap/elasticsearch-full
 elasticsearch
 _http://127.0.0.1:9200/_ (http://127.0.0.1:9200/)

*Install Kibana*
 brew tap elastic/tap
 brew install elastic/tap/Kibana-full
 kibana
 _http://localhost:5601/_ (http://localhost:5601/)


### 3. Connecting Spark with Elasticsearch setup:


For streaming data from spark to Elasticsearch using elasticsearch-hadoop-7.5.6.jar
PATH: /usr/local/Cellar/apache-spark/2.4.4/libexec/jars/elasticsearch-hadoop-7.5.2.jar

### 4. Delta lake in spark:

https://docs.delta.io/latest/quick-start.html
spark-shell —packages io.delta:delta-core_2.11:0.5.0

### 5. Run a batch job in the cluster that reads data from elastic search index and write to a raw delta table.

bin/spark-submit \
 --deploy-mode cluster \
 --class "com.apple.pst.dc.Main_POC" \
 --master local \
data-monitoing-project-poc.jar
