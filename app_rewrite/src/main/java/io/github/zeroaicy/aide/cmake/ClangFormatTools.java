/**
 * @Date 
 * @AIDE AIDE+ 
 */
package io.github.zeroaicy.aide.cmake;
import android.text.TextUtils;
import com.aide.ui.ServiceContainer;
import io.github.zeroaicy.aide.shell.ShellEnvironment;
import io.github.zeroaicy.aide.shell.ShellEnvironmentUtils;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ClangFormatTools {

	static {

	}

	private static final String ErrorTAG = "没有clang-format，请安装Ndk";

	private static ShellEnvironment shellEnvironment = ShellEnvironmentUtils.getShellEnvironment();

	private static String clangFormatPath;

	private static File ndkDir;
	public static String format() {

		if (TextUtils.isEmpty(ClangFormatTools.clangFormatPath)) {
			if (ClangFormatTools.ndkDir == null) {
				// 计算 clangFormatPath
				File filesDir = ServiceContainer.getContext().getFilesDir();
				ClangFormatTools.ndkDir = new File(filesDir, "home/android-sdk/ndk");
			}

			String[] ndkVersions = ClangFormatTools.ndkDir.list();
			if (ndkVersions != null && ndkVersions.length != 0) {
				// 排序
				Arrays.sort(ndkVersions);
				String ndkVersion = ndkVersions[ndkVersions.length - 1];

				File clangFormatFile = new File(ndkDir,
						ndkVersion + "/toolchains/llvm/prebuilt/linux-aarch64/bin/clang-format");
				if (!clangFormatFile.isFile()) {
					return ClangFormatTools.ErrorTAG;
				}
				ClangFormatTools.clangFormatPath = clangFormatFile.getAbsolutePath();
			} else {
				return ClangFormatTools.ErrorTAG;
			}
		}
		// ClangFormatTools.clangFormatPath
		String extarExecutable = ClangFormatTools.clangFormatPath;

		List<String> arguments = shellEnvironment.setupShellCommandArguments(Arrays.asList(extarExecutable));
		
		shellEnvironment.setupShellCommandArguments(arguments);
		
		

		return null;
	}
}

