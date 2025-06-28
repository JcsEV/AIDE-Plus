package cn.iyutong.aide.quickinput;

import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iyutong.aide.YAIDEEditor;
import io.github.zeroaicy.aide.completion.QuickCode;
import io.github.zeroaicy.aide.preference.ZeroAicySetting;

public class YQuickCode {

    public static final MMKV kv = MMKV.mmkvWithID("Yquickcode", MMKV.SINGLE_PROCESS_MODE);
    private static final List<QuickCode> all = new ArrayList<>();

    private static String pathLowerCase1 = "";

    public static List<QuickCode> getAll(String pathLowerCase) {
        String json = kv.decodeString("qt");
        if (pathLowerCase.endsWith(".css")) {
            json = kv.decodeString("css");
        }
        if (pathLowerCase.endsWith(".xml") || pathLowerCase.endsWith(".html") || pathLowerCase.endsWith(".htm")) {
            json = kv.decodeString("html");
        }
        if (pathLowerCase.endsWith(".java") || pathLowerCase.endsWith(".js")) {
            json = kv.decodeString("js");
        }
        if (pathLowerCase.endsWith(".c") || pathLowerCase.endsWith(".cpp") || pathLowerCase.endsWith(".h")) {
            json = kv.decodeString("c");
        }
        if (all.isEmpty() || !pathLowerCase1.equals(pathLowerCase)) {
            all.clear();
            if (TextUtils.isEmpty(json)) {
                QuickCode quickCode = new QuickCode();
                quickCode.setKj("for");
                quickCode.setBt("正序循环体");
                quickCode.setCodeText("for( int i = 0; i < length; i++) {\n\t\t\n}");
                all.add(quickCode);
            }
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        QuickCode quickCode = new QuickCode();
                        quickCode.setKj(jsonObject.getString("kj"));
                        quickCode.setBt(jsonObject.getString("bt"));
                        quickCode.setCodeText(jsonObject.getString("ct"));
                        all.add(quickCode);
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
            pathLowerCase1 = pathLowerCase;
        }
        return all;
    }

}
