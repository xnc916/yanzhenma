package com.yuluedu.yanzhengma;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private YanView editText;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (YanView) findViewById(R.id.scv_edittext);
        text = (TextView) findViewById(R.id.tv_text);

        //添加验证码控件监听

        editText.setInputCompleteListener(listener);

        //设置一键删除点击效果
        findViewById(R.id.main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearEditText();
                text.setText("输入验证码表示同意《用户协议》");
                text.setTextColor(Color.BLACK);
            }
        });

    }

    private YanView.InputCompleteListener listener = new YanView.InputCompleteListener() {
        @Override
        public void inputComplete() {
            Toast.makeText(MainActivity.this, "验证码为：" + editText.getEditContent(), Toast.LENGTH_SHORT).show();
            if (!editText.getEditContent().equals("12345")){
                text.setText("验证码输入错误");
                text.setTextColor(Color.RED);
            }
        }

        @Override
        public void deleteContent(boolean isDelete) {
            if (isDelete){
                text.setText("输入验证码表示同意《用户协议》");
                text.setTextColor(Color.BLACK);
            }
        }
    };
}
