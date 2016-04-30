package xyz.moechat.sws3d;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity  implements View.OnClickListener {
    Sws3dView sws3dView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sws3dView=(Sws3dView)findViewById(R.id.textView);

        sws3dView.setRotate_X(104);sws3dView.setRotate_Y(0);sws3dView.setRotate_Z(0);
        sws3dView.setTranslate_X(-600);
        sws3dView.setTranslate_Y(800);
        sws3dView.setTranslate_Z(400);

        Button b_1=(Button)findViewById(R.id.b_1);
        Button b_2=(Button)findViewById(R.id.b_2);
        Button b_3=(Button)findViewById(R.id.b_3);
        Button b_4=(Button)findViewById(R.id.b_4);
        Button b_5=(Button)findViewById(R.id.b_5);
        Button b_6=(Button)findViewById(R.id.b_6);
        Button b1=(Button)findViewById(R.id.b1);
        Button b2=(Button)findViewById(R.id.b2);
        Button b3=(Button)findViewById(R.id.b3);
        Button b4=(Button)findViewById(R.id.b4);
        Button b5=(Button)findViewById(R.id.b5);
        Button b6=(Button)findViewById(R.id.b6);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b_1.setOnClickListener(this);
        b_2.setOnClickListener(this);
        b_3.setOnClickListener(this);
        b_4.setOnClickListener(this);
        b_5.setOnClickListener(this);
        b_6.setOnClickListener(this);

        Intent intent = new Intent("xyz.moechat.sw3d.MUSIC");
        intent.setPackage(getPackageName());
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("xyz.moechat.sw3d.MUSIC");
        intent.setPackage(getPackageName());
        stopService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1:
                sws3dView.setRotate_X(sws3dView.getRotate_X()+1);
                break;
            case R.id.b2:
                sws3dView.setRotate_Y(sws3dView.getRotate_Y() + 1);
                break;
            case R.id.b3:
                sws3dView.setRotate_Z(sws3dView.getRotate_Z() + 1);
                break;
            case R.id.b4:
                sws3dView.setTranslate_X(sws3dView.getTranslate_X() + 100);
                break;
            case R.id.b5:
                sws3dView.setTranslate_Y(sws3dView.getTranslate_Y() + 100);
                break;
            case R.id.b6:
                sws3dView.setTranslate_Z(sws3dView.getTranslate_Z() + 100);
                break;

            case R.id.b_1:
                sws3dView.setRotate_X(sws3dView.getRotate_X()-1);
                break;
            case R.id.b_2:
                sws3dView.setRotate_Y(sws3dView.getRotate_Y() -1);
                break;
            case R.id.b_3:
                sws3dView.setRotate_Z(sws3dView.getRotate_Z()- 1);
                break;
            case R.id.b_4:
                sws3dView.setTranslate_X(sws3dView.getTranslate_X() - 100);
                break;
            case R.id.b_5:
                sws3dView.setTranslate_Y(sws3dView.getTranslate_Y() - 100);
                break;
            case R.id.b_6:
                sws3dView.setTranslate_Z(sws3dView.getTranslate_Z() - 100);
                break;
        }

        Log.v("moe","rotate:("+sws3dView.getRotate_X()+","+sws3dView.getRotate_Y()+","+sws3dView.getRotate_Z()+")"+"translate:("+sws3dView.getTranslate_X()+","+sws3dView.getTranslate_Y()+","+sws3dView.getTranslate_Z()+")");
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try{
                    Thread.sleep(sws3dView.Duration);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Message m=new Message();
                    //// TODO: 16/5/1  
                    m.what=1;
                    handler.sendMessage(m);
                }
            }
        }).start();
    }
    String split_tag="\n\n";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1) {
                List<String> lines = java.util.Arrays.asList((sws3dView.getText() + "").split(split_tag));
                if (sws3dView.getLineCount() > 30) {
                    String text = TextUtils.join(split_tag, lines.subList(1, lines.size()));
                    sws3dView.setText(text + getMixString(20) + split_tag);

                } else {
                    sws3dView.setText(sws3dView.getText() + getMixString(20) + split_tag);
                }
            }
            else {
                sws3dView.setText(sws3dView.getText() + getMixString(20) + split_tag);
                sws3dView.startScroll();
            }
        }
    };

    //获取固定长度的随机字符串
    public static String getMixString(int length){
        Random r = new Random();
        char[] charArray = new char[r.nextInt(length)];

        for(int i=0;i<charArray.length;i++){


            int n = r.nextInt(123);
            while(n<48 || (n>57 && n<65) || (n>90 && n <97) || n>122){//(!((n>=48 && n<=57) || (n>=65 && n<=90) && (n>=97 && n<=122))){
                n = r.nextInt(123);
            }
            charArray[i] = (char)n ;
        }

        return String.valueOf(charArray) ;

    }
}
