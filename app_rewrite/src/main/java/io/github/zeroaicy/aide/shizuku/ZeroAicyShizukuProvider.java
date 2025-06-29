package io.github.zeroaicy.aide.shizuku;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import com.aide.common.AppLog;
import rikka.shizuku.ShizukuProvider;

public class ZeroAicyShizukuProvider extends ShizukuProvider {
	public static final String TAG = "ZeroAicyShizukuProvider";
		
	public ZeroAicyShizukuProvider(){
		ZeroAicyShizukuProvider.enableMultiProcessSupport(true);
	}
	@Override
	public void attachInfo(Context context, ProviderInfo info) {
		AppLog.d(TAG, "attachInfo");
		//初始化Shizuku库
		ShizukuUtil.initialized(context);
		
		super.attachInfo(context, info);
		
	}

	@Override
	public boolean onCreate() {
		return super.onCreate();
	}

	@Override
	public Bundle call(String authority, String method, String arg, Bundle extras) {
		return super.call(authority, method, arg, extras);
	}

	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		AppLog.w(TAG, "call: Shizuku附加远程服务");
		return super.call(method, arg, extras);
	}
	


}
