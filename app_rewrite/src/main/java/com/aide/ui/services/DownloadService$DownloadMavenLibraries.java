package com.aide.ui.services;



/**
 * 替换maven下载实现
 */
// NativeCodeSupportService$q -> DownloadService$DownloadMavenLibraries
@androidx.annotation.Keep
public class DownloadService$DownloadMavenLibraries extends io.github.zeroaicy.aide.ui.services.DownloadMavenLibraries {

	public DownloadService$DownloadMavenLibraries(
		com.aide.ui.services.DownloadService downloadService, 
		android.app.Activity activity, 
		java.util.List<com.aide.ui.util.BuildGradle.MavenDependency> deps, 
		java.util.List<com.aide.ui.util.BuildGradle.RemoteRepository> remoteRepositorys, 
		java.lang.Runnable completeCallback) {
			
		// 调用
        super(downloadService, activity, deps, remoteRepositorys, completeCallback);
    }   
}

