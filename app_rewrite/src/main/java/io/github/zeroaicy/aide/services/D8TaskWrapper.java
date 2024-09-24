package io.github.zeroaicy.aide.services;

import android.text.TextUtils;
import com.aide.ui.services.AssetInstallationService;
import io.github.zeroaicy.util.IOUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class D8TaskWrapper {

	public static final String R8Task = "io.github.zeroaicy.r8.R8Task";
	public static final String D8Task = "io.github.zeroaicy.r8.D8Task";

	public static final String D8BatchTask = "io.github.zeroaicy.r8.D8BatchTask";

	public static void runD8Task(List<String> argList) throws Throwable {

		// 使用 app_process运行 d8 || r8

		// 临时先使用 这个
		// com.android.tools.r8.D8.main(argList.toArray(new String[argList.size()]));
		run(D8Task, argList);

	}
	
	public static void runD8Task(List<String> argList, Map<String, String> environment) throws Throwable {
		run(D8Task, argList, environment);
	}
	/**
	 * 编译多个jar且输出多个路径
	 * argList 为通用配置 run minsdk android_sdk路径等
	 * 不可有 --output及输入文件
	 */
	public static void runD8BatchTask(List<String> inputFiles, List<String> outputFiles, List<String> argList, Map<String, String> environment) throws Throwable {
		// 输出
		argList.add(String.join("|", outputFiles));
		// 输入
		argList.add(String.join("|", inputFiles));
		
		run(D8BatchTask, argList, environment);
	}
	
	public static void runR8Task(List<String> argList) throws Throwable {

		// 使用 app_process运行 d8 || r8
		run(R8Task, argList, Collections.<String, String>emptyMap());

	}
	public static void runR8Task(List<String> argList, Map<String, String> environment) throws Throwable {

		// 使用 app_process运行 d8 || r8
		run(R8Task, argList, environment);

	}


	private static void run(String className, List<String> argList) throws Throwable {
		run(className, argList, Collections.<String, String>emptyMap());
	}

	private static void run(String className, List<String> argList, Map<String, String> environment) throws Throwable {
		String r8Path = AssetInstallationService.DW("com.android.tools.r8.zip", true);
		// 去除写入权限
		new File(r8Path).setWritable(false);
		// /system/bin/app_process -Djava.class.path="r8Path" /system/bin --nice-name=R8Task io.github.zeroaicy.r8.R8Task "$@"
		ArrayList<String> cmdList = new ArrayList<String>();
		cmdList.add("app_process");
		cmdList.add("-Djava.class.path=" + r8Path);
		cmdList.add("/system/bin");
		cmdList.add("--nice-name=D8Task");

		// 需要运行的类
		cmdList.add(className);

		cmdList.addAll(argList);
		

		//*
		String[] args = cmdList.toArray(new String[cmdList.size()]);
		System.out.println(cmdList);
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		processBuilder.environment().putAll(environment);
		
		
		// 运行进程
		Process process = processBuilder.start();
		
		// 读取错误流
		D8TaskWrapper.ProcessStreamReader errorStreamReader = new ProcessStreamReader(process.getErrorStream());
		Thread thread = new Thread(errorStreamReader);
		thread.start();		
		// 读取输出流
		D8TaskWrapper.ProcessStreamReader inputStreamReader = new ProcessStreamReader(process.getInputStream());
		Thread inputStreamReaderThread = new Thread(inputStreamReader);
		inputStreamReaderThread.start();		
		
		try {
			// 等待 r8进程运行完
			// 再此之前必须读取输出流和错误流
			// 否则缓存池用完会阻塞
			process.waitFor();
			
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		String error = errorStreamReader.getError();
		if (process.exitValue() != 0 && !TextUtils.isEmpty(error)) {
			throw new Error(error);
		}
		//*/
	}

	public static class ProcessStreamReader implements Runnable {
		BufferedInputStream bufferedInputStream;
		ByteArrayOutputStream byteArrayOutputStream;
		public ProcessStreamReader(InputStream inputStream) {
			this.byteArrayOutputStream = new ByteArrayOutputStream();

			this.bufferedInputStream = new BufferedInputStream(inputStream);
		}
		String error;
		public String getError() {
			try {
				if (this.error == null) {
					this.error = new String(this.byteArrayOutputStream.toByteArray());
				}
				return this.error;
			}
			finally {
				IOUtils.close(this.byteArrayOutputStream);
			}
		}
		@Override
		public void run() {
			try {
				byte[] data = new byte[1024 * 100];
				int count = 0;
				while ((count = this.bufferedInputStream.read(data)) != -1) {
					this.byteArrayOutputStream.write(data, 0, count);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				IOUtils.close(this.bufferedInputStream);
			}
		}
	}
	
	public static void fillD8Args(List<String> argsList, int minSdk, boolean file_per_class_file, boolean intermediate, String user_androidjar, List<String> dependencyLibs, String outPath) {
		// 都启用多线程dexing ❛˓◞˂̵✧
		argsList.add("--thread-count");
		argsList.add("32");

		argsList.add("--min-api");
		//待跟随minSDK
		argsList.add(String.valueOf(minSdk));

		if ( file_per_class_file ){
			argsList.add("--file-per-class-file");
		}
		if ( intermediate ){
			argsList.add("--intermediate");
		}
		if ( !TextUtils.isEmpty(user_androidjar) ){
			argsList.add("--lib");
			argsList.add(user_androidjar);
		}

		if ( dependencyLibs != null ){
			for ( String librarie : dependencyLibs ){
				argsList.add("--classpath");
				argsList.add(librarie);
			}
		}
		argsList.add("--output");
		argsList.add(outPath);
	}
}
