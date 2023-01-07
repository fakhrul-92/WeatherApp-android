package com.example.finalapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;

public class MyLotteryService extends Service {
    public MyLotteryService() {
    }
    int mLottoNumberAmount = 0;
    @Override
    public int onStartCommand(Intent intent, int lags, int startId){
        mLottoNumberAmount = intent.getIntExtra("LOTTO_NUMBER_AMOUNT", 0);
        new Thread(()->{
            try {
                for(int i=0; i<mLottoNumberAmount; i++){
                    Thread.sleep(2000);
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(9);
                    int number = rand_int1;
                    Intent lottoNumber = new Intent("com.taml.mylotteryapp");
                    lottoNumber.putExtra("LOTTO_NUMBER", number);
                    sendBroadcast(lottoNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
}