package cn.iyutong.aide.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.iyutong.aide.quickinput.CodeAdapter;
import cn.iyutong.aide.quickinput.YQuickCode;
import com.aide.ui.ThemedActionbarActivity;
import com.aide.ui.rewrite.R;
import io.github.zeroaicy.aide.completion.QuickCode;
import org.json2.JSONArray;
import org.json2.JSONObject;

public class YQuickCodeSettings extends ThemedActionbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yquick_keys_settings);
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setTitle("快捷代码内容设置");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String lx = intent.getStringExtra("lx");
        if (TextUtils.isEmpty(lx)) {
            lx = "qt";
        }

        RecyclerView recyclerView = findViewById(R.id.lbwj);
        CodeAdapter adapter = new CodeAdapter(YQuickCode.getAll(lx));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.tjsj).setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.dlialog_yquick_yt, null);
            EditText name1 = dialogView.findViewById(R.id.nr);
            EditText kl = dialogView.findViewById(R.id.kj);
            EditText bt1 = dialogView.findViewById(R.id.bt);
            AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView)
                    .setPositiveButton("添加",  (dialog1, which) -> {
                        String klText = kl.getText().toString();
                        if( klText.isEmpty() || !klText.matches("^[a-zA-Z]+$") ){
                            Toast.makeText(this, "快捷索引词不可为空，只能为英文", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String nameText = name1.getText().toString();
                        if( nameText.isEmpty()){
                            Toast.makeText(this, "请输入代码内容", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        QuickCode quickCode = new QuickCode();
                        quickCode.setKj(kl.getText().toString());
                        quickCode.setCodeText(name1.getText().toString());
                        quickCode.setBt(bt1.getText().toString());
                        adapter.add(quickCode);
                    })
                    .setNegativeButton("取消",null)
                    .create();
            dialog.show();
        });

        String finalLx = lx;
        findViewById(R.id.bcsj).setOnClickListener(v -> {
            JSONArray jsonArray = new JSONArray();
            for (QuickCode quickCode : adapter.getData()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kj", quickCode.getKj());
                jsonObject.put("bt", quickCode.getBt());
                jsonObject.put("ct", quickCode.getCodeText());
                jsonArray.put(jsonObject);
            }
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            if (finalLx.endsWith(".css")) {
                YQuickCode.kv.encode("css", jsonArray.toString());
                return;
            }
            if (finalLx.endsWith(".xml") || finalLx.endsWith(".html") || finalLx.endsWith(".htm")) {
                YQuickCode.kv.encode("html", jsonArray.toString());
                return;
            }
            if (finalLx.endsWith(".java") || finalLx.endsWith(".js")) {
                YQuickCode.kv.encode("js", jsonArray.toString());
                return;
            }
            if (finalLx.endsWith(".c") || finalLx.endsWith(".cpp") || finalLx.endsWith(".h")) {
                YQuickCode.kv.encode("c", jsonArray.toString());
                return;
            }
            YQuickCode.kv.encode("qt", jsonArray.toString());
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
