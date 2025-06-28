package cn.iyutong.aide.quickinput;

import java.util.ArrayList;
import java.util.List;

import io.github.zeroaicy.aide.completion.QuickCode;

public class YQuickCode {

    private static final List<QuickCode> all = new ArrayList<>();
    public static List<QuickCode> getAll() {
        if( all.isEmpty()){
            QuickCode quickCode = new QuickCode();
            quickCode.setName("for-正序循环体");
            quickCode.setCodeText("for( int i = 0; i < length; i++) {\n\t\t\n}");
            all.add(quickCode);
        }
        return all;
    }

}
