package com.example.wangmiao.teamdemo01.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by kzj on 2016/10/4.
 */
public class JudgeNetIsConnectedSuccessReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if(!booleanExtra||JudgeNetIsConnectedSuccess(context)){
            Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "网络连接失败，请检查网络", Toast.LENGTH_SHORT).show();
        }

    }
    public static boolean JudgeNetIsConnectedSuccess(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
