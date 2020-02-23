package com.monitor;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;

import static org.apache.spark.sql.functions.*;

public class Main_POC {

    public static void main(String[] args) {

        SparkConf sparkConf = getSparkConf();

        //dataset from elasticsearch index
        Dataset<Row> ds = getRowDatasetFromElasticSearch(sparkConf);
        Dataset<Row> res = ds.select(to_json(struct(col("*"))).alias("content")).distinct();

        //write into delta table
        res.write().format("delta").mode("append").save("/usr/local/Cellar/delta/test");

    }

    private static Dataset<Row> getRowDatasetFromElasticSearch(SparkConf sparkConf) {
        SparkSession spark =  SparkSession.builder().config(sparkConf).getOrCreate();
        SQLContext sqlContext = spark.sqlContext();
        return JavaEsSparkSQL.esDF(sqlContext, "kibana_sample_data_flights/_doc");
    }

    private static SparkConf getSparkConf() {
        SparkConf sparkConf = new SparkConf(true);
        sparkConf.setMaster("local[*]");
        sparkConf.set("es.index.auto.create", "false");
        sparkConf.set("es.nodes", "127.0.0.1");
        sparkConf.set("es.port", "9200");
        sparkConf.set("es.nodes.wan.only", "false");
        sparkConf.set("es.scroll.size", "50");
        return sparkConf;
    }
}
