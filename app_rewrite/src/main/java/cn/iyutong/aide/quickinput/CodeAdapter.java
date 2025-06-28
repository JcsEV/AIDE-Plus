package cn.iyutong.aide.quickinput;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aide.ui.ServiceContainer;
import com.aide.ui.rewrite.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.iyutong.aide.YAIDEEditor;
import cn.iyutong.tool.adapter.recyclerview.BaseRecyclerAdapter;
import cn.iyutong.tool.adapter.recyclerview.RecyclerViewHolder;
import io.github.zeroaicy.aide.completion.QuickCode;
import io.github.zeroaicy.aide.preference.ZeroAicySetting;
import io.github.zeroaicy.aide.ui.services.ThreadPoolService;

public class CodeAdapter extends BaseRecyclerAdapter<QuickCode> {
    public CodeAdapter(List<QuickCode> data) {
        super(data);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.yquick_keys_settings;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, QuickCode item) {
        TextView textView = holder.findViewById(R.id.bt);
        TextView textView2 = holder.findViewById(R.id.nr);

        String bt = item.getBt();
        String name = item.getKj();
        String codeText = item.getCodeText();

        if (TextUtils.isEmpty(bt)) {
            textView.setText(name);
        } else {
            textView.setText(name + "-" + bt);
        }
        textView2.setText(codeText);

        holder.findViewById(R.id.delete).setOnClickListener(v -> {
           delete(position);
        });

        holder.findViewById(R.id.edit).setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(holder.getContext());
            View dialogView = inflater.inflate(R.layout.dlialog_yquick_yt, null);

            EditText name1 = dialogView.findViewById(R.id.nr);
            name1.setText(codeText);
            EditText kl = dialogView.findViewById(R.id.kj);
            kl.setText(name);
            EditText bt1 = dialogView.findViewById(R.id.bt);
            bt1.setText(bt);

            AlertDialog dialog = new AlertDialog.Builder(holder.getContext()).setView(dialogView)
                    .setPositiveButton("确定",  (dialog1, which) -> {
                        String klText = kl.getText().toString();
                        if( klText.isEmpty() || !klText.matches("^[a-zA-Z]+$") ){
                            Toast.makeText(holder.getContext(), "快捷索引词不可为空，只能为英文", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String nameText = name1.getText().toString();
                        if( nameText.isEmpty()){
                            Toast.makeText(holder.getContext(), "请输入代码内容", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        item.setKj(kl.getText().toString());
                        item.setCodeText(name1.getText().toString());
                        item.setBt(bt1.getText().toString());
                        notifyItemChanged(position);
                    })
                    .setNegativeButton("取消",null)
                    .create();
            dialog.show();
        });
    }
}
