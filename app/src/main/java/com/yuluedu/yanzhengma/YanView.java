package com.yuluedu.yanzhengma;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by gameben on 2017-05-11.
 */

public class YanView extends RelativeLayout{

    private EditText editText;
    private TextView[] textViews;
    private StringBuffer stringBuffer = new StringBuffer();
    private int count = 5;
    private String string;




    public YanView(Context context) {
        this(context,null);
    }

    public YanView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public YanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.layout,this);

        editText = (EditText) findViewById(R.id.item_edittext);

        textViews = new TextView[5];
        textViews[0] = (TextView) findViewById(R.id.item_code_tv1);
        textViews[1] = (TextView) findViewById(R.id.item_code_tv2);
        textViews[2] = (TextView) findViewById(R.id.item_code_tv3);
        textViews[3] = (TextView) findViewById(R.id.item_code_tv4);
        textViews[4] = (TextView) findViewById(R.id.item_code_tv5);

        //隐藏光标
        editText.setCursorVisible(false);

        //设置监听
        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //重点~！！！！
                //如果字符不为“”空时才进行操作
                if (!s.toString().equals("")){
                    if (stringBuffer.length() > 4){
                        editText.setText("");
                        return;
                    }else {
                        //当字符添加到StringBuffer中
                        stringBuffer.append(s);
                        //添加后将EditText置空，造成没有文字输入的错觉
                        editText.setText("");
                        //记录stringBuffer长度
                        count = stringBuffer.length();
                        //对最终的string进行赋值
                        string = stringBuffer.toString();
                        //todo 当文字长度为5，则调用完成输入监听
                        if(stringBuffer.length() == 5){
                            if (inputCompleteListener != null){
                                inputCompleteListener.inputComplete();
                            }
                        }

                    }

                //将输入内容用TextView进行展示
                for (int i = 0; i < stringBuffer.length(); i++) {
                    //设置TextView显示文字
                    textViews[i].setText(String.valueOf(string.charAt(i)));
                    //改变textView背景
                    textViews[i].setBackgroundResource(R.drawable.bg_verify_press);
                }
                }
            }
        });
        //editText，添加用户点击删除按键执行删除操作的监听
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //判断是否点击了删除按键
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN){
                    // 删除操作
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }

            


        });




    }
    //对外提供两个方法
    //1.获取输入文本
    public String getEditContent() {
        return string;
    }

    //2.清空输入内容
    public void clearEditText() {
        stringBuffer.delete(0, stringBuffer.length());
        string = stringBuffer.toString();
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText("");
            textViews[i].setBackgroundResource(R.drawable.bg_verify);
        }
    }

    private boolean onKeyDelete() {
        if (count == 0) {
            count = 5;
            return true;
        }

        if (stringBuffer.length() > 0) {
            //删除相应位置的字符
            stringBuffer.delete((count - 1), count);
            //string长度减一
            count--;
            //重新赋值
            string = stringBuffer.toString();
            //删除后的TextView置空
            textViews[stringBuffer.length()].setText("");
            //删除后的TextView背景改变
            textViews[stringBuffer.length()].setBackgroundResource(R.drawable.bg_verify);
            //删除的回调
            if (inputCompleteListener != null) {
                inputCompleteListener.deleteContent(true);
            }
        }
        return false;

    }


    //输入完成监听------接口回调
    public interface InputCompleteListener {

        //输入完成
        void inputComplete();

        //删除回调
        void deleteContent(boolean isDelete);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }
}
