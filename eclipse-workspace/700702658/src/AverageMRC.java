
//package com.self.average.mapreduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageMRC {

 public class AverageMap extends Mapper<LongWritable, Text, Text, Text> {
  @Override
  protected void map(final LongWritable key, final Text value,
    final Context context) throws IOException, InterruptedException {
   context.write(new Text("MAPPER"), value);
  };
 }

 public class Combiner extends Reducer<Text, Text, Text, Text> {
  @Override
  protected void reduce(final Text key, final Iterable<Text> values,
    final Context context) throws IOException, InterruptedException {
   Integer count = 0;
   Double sum = 0D;
   final Iterator<Text> itr = values.iterator();
   while (itr.hasNext()) {
    final String text = itr.next().toString();
    final Double value = Double.parseDouble(text);
    count++;
    sum += value;
   }

   final Double average = sum / count;

   context.write(new Text("A_C"), new Text(average + "_" + count));
  };
 }

 public class Reduce extends Reducer<Text, Text, Text, Text> {
  @Override
  protected void reduce(final Text key, final Iterable<Text> values,
    final Context context) throws IOException, InterruptedException {
   Double sum = 0D;
   Integer totalcount = 0;
   final Iterator<Text> itr = values.iterator();
   while (itr.hasNext()) {
    final String text = itr.next().toString();
    final String[] tokens = text.split("_");
    final Double average = Double.parseDouble(tokens[0]);
    final Integer count = Integer.parseInt(tokens[1]);
    sum += (average * count);
    totalcount += count;
   }

   final Double average = sum / totalcount;

   context.write(new Text("AVERAGE"), new Text(average.toString()));
  };
 }
}
