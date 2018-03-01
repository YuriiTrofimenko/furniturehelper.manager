package org.tyaa.furniturehelper.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.tyaa.furniturehelper.manager.BusinessCardActivity;

public class CallReceiver extends BroadcastReceiver {

    private static String mPhoneNumber = "";
    public static final String EXTRA_PHONE_NUMBER =
            "org.tyaa.furniturehelper.manager.receiver.phone_number";
    //true if activity called receiver
    private static boolean isReceiverCalled_;

    public static boolean isReceiverCalled(){
        boolean value = isReceiverCalled_;
        isReceiverCalled_ = false;
        return value;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                //телефон звонит, получаем входящий номер
                mPhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.i("call", "begin");
            } else
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                //телефон находится в режиме звонка (набор номера / разговор)
                Log.i("call", "talk");
            } else
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                //телефон находится в ждущем режиме.
                // Это событие наступает по окончанию разговора,
                // когда мы уже знаем номер и факт звонка
                //Toast.makeText(context, mPhoneNumber,
                        //Toast.LENGTH_SHORT).show();
                Log.i("call", "end");
                isReceiverCalled_ = true;
                Intent mainActivityIntent = new Intent(context, BusinessCardActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainActivityIntent.putExtra(EXTRA_PHONE_NUMBER, mPhoneNumber);
                context.startActivity(mainActivityIntent);
            }
        }
    }
}
