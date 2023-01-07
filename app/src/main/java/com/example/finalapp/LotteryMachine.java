package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Scanner;

public class LotteryMachine extends AppCompatActivity {
    int first=0, second=0, third=0, four=0, five=0;
    private myLottoBroadcastReceiver lottoBroadcastReceiver;
    Scanner sc = new Scanner(System.in);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(new myLottoBroadcastReceiver(), new IntentFilter("com.tamk.mylotteryapp"));
    }
    public void StartLottoService(View view) {
        Intent intent = new Intent(this, MyLotteryService.class);
        intent.putExtra("LOTTO_NUMBER_AMOUNT", 5);
        startService(intent);

    }
    public void showNumber(View view) {
        TextView textView2 = findViewById(R.id.maintextView);
        textView2.setText( five + ", " + four+ ", " + third + ", " + second + ", " + first );
    }
    private class myLottoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()=="com.taml.mylotteryapp"){
                int number = intent.getIntExtra("LOTTO_NUMBER", 0);
                TextView maintextView = findViewById(R.id.maintextView);
                maintextView.setText("Lotto number: " + number);

                if(number!=0){
                    five = four;
                    four = third;
                    third = second;
                    second = first;
                    first = number;
                }
            }
        }
    }
}