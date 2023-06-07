
//package com.self.average.driver;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.common.CommandLineUtil;
import org.apache.tools.ant.util.FileUtils;

import com.self.average.mapreduce.AverageMRC;

public class AverageDriver extends Configured implements Tool {

  public static void main(final String[] args) throws Exception {

   final int res = ToolRunner.run(new Configuration(),
    new AverageDriver(), args);
  System.exit(res);
 }

  private Configuration conf;

  private String inputFile;
 private String outputName;

  private final String inputDirectoryPrefix = "Average";

  private final String averagefilename = "average.txt";

  public boolean cpFilestoUserLocation(final String useroutputfile,
   final String systemfile) throws IOException {
  System.out.println("Copy file to user location");
  System.out.println("useroutputfile::" + useroutputfile);
  System.out.println("systemfile::" + systemfile);
  final FileUtils fu = FileUtils.getFileUtils();
  fu.copyFile(systemfile, useroutputfile, null, true);

   return true;
 }

  @Override
 public Configuration getConf() {
  return this.conf;
 }

  public final String getDateTime() {
  final DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
  return df.format(new Date());
 }

  @SuppressWarnings("deprecation")
 @Override
 public int run(final String[] args) throws Exception {
  final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
  final ArgumentBuilder abuilder = new ArgumentBuilder();
  final GroupBuilder gbuilder = new GroupBuilder();

   final Option inputFileOpt = obuilder
    .withLongName("input")
    .withShortName("input")
    .withRequired(false)
    .withArgument(
      abuilder.withName("input").withMinimum(1)
        .withMaximum(1).create())
    .withDescription("input file").create();

   final Option outputDirOpt = obuilder
    .withLongName("o")
    .withShortName("o")
    .withRequired(false)
    .withArgument(
      abuilder.withName("o").withMinimum(1).withMaximum(1)
        .create())
    .withDescription("output directory option").create();

   final Group group = gbuilder.withName("Options")
    .withOption(inputFileOpt).withOption(outputDirOpt).create();

   System.out.println(group);

   try {
   final Parser parser = new Parser();
   parser.setGroup(group);
   final CommandLine cmdLine = parser.parse(args);

    if (cmdLine.hasOption("help")) {
    CommandLineUtil.printHelp(group);
    return -1;
   }

    this.inputFile = cmdLine.getValue(inputFileOpt).toString().trim();
   this.outputName = cmdLine.getValue(outputDirOpt).toString().trim();

    System.out.println("input file path::" + this.inputFile);
   System.out.println("output Directory::" + this.outputName);

   } catch (final OptionException e) {
   System.err.println("Exception : " + e);
   CommandLineUtil.printHelp(group);
   return -1;
  }

   final String parentDirectory = "Average";
  final String baseDirectory = this.outputName + "/" + parentDirectory;
  if (!FileSystem.get(new Configuration()).isDirectory(
    new Path(baseDirectory))) {
   FileSystem.get(new Configuration()).mkdirs(new Path(baseDirectory));
  }

   final Job job = new Job(this.getConf());
  job.setJobName("AverageProcess");
  job.setJarByClass(AverageMRC.class);

   job.setOutputKeyClass(Text.class);
  job.setOutputValueClass(Text.class);

   job.setNumReduceTasks(1);

   job.setMapperClass(AverageMRC.AverageMap.class);
  job.setReducerClass(AverageMRC.Reduce.class);
  job.setCombinerClass(AverageMRC.Combiner.class);

   job.setInputFormatClass(TextInputFormat.class);
  job.setOutputFormatClass(TextOutputFormat.class);

   DistributedCache.addCacheFile(new Path(this.inputFile).toUri(),
    job.getConfiguration());
  final String averageoutputPath = baseDirectory + "/"
    + this.inputDirectoryPrefix;

   if (FileSystem.get(new Configuration()).isDirectory(
    new Path(averageoutputPath))) {
   FileSystem.get(new Configuration()).delete(new Path(baseDirectory),
     true);
  }

   FileInputFormat.setInputPaths(job, new Path(this.inputFile));
  FileOutputFormat.setOutputPath(job, new Path(averageoutputPath));

   final boolean status = job.waitForCompletion(true);

   if (!status) {
   System.out.println("Could Not create the dictionary file");
   return -1;
  }

   if (this.cpFilestoUserLocation(baseDirectory + "/"
    + this.averagefilename, averageoutputPath + "/"
    + "part-r-00000")) {
   System.out.println("Average File Copied at " + baseDirectory + "/"
     + this.averagefilename);
  }

   return 0;
 }

  @Override
 public void setConf(final Configuration conf) {
  this.conf = conf;
 }

}
