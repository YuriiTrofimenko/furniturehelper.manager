package org.tyaa.furnituresender.messageproviders;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.databinding.ObservableArrayList;
//import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import org.tyaa.fhelpermodel.SubLinkLink;
import org.tyaa.fhelpermodel.SubLinkMap;
import org.tyaa.fhelpermodel.SubLinkText;
import org.tyaa.fhelpermodel.interfaces.ISubLink;
//import org.tyaa.furnituresender.AfterSendActivity;
import org.tyaa.furnituresender.messageproviders.base.IMessageProvider;
import org.tyaa.furnituresender.messageproviders.base.MessageProviderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * FB, Telegram, VK, Google Maps, Viber
@author Bokov Valery
 */
public final class SMSProvider implements IMessageProvider {
	private SMSProviderOptions tempOptions;
	private final StringBuilder builder;
	private PendingIntent sentPI;
	private final Intent intent = new Intent("SMS_SENT");
	private final IntentFilter filter = new IntentFilter("SMS_SENT");
	private BroadcastReceiver sentReceiver;

	public SMSProvider(BroadcastReceiver sentReceiver){
		builder = new StringBuilder();
		this.sentReceiver = sentReceiver;
	}

	public void resume(Context c){
		if (sentPI == null)
			sentPI = PendingIntent.getBroadcast(c, 0, intent,0);

		c.registerReceiver(sentReceiver, filter);
	}

	public void reset(Context c){
		c.unregisterReceiver(sentReceiver);
		sentPI = null;
	}

	@Override
	public boolean sendMessage(final ArrayList<ISubLink> content, Context context)
	{	//TODO max length of sms. Divide messages correctly!
		String text;

		for(final ISubLink item : content){
			if (item instanceof SubLinkText){
				text = ((SubLinkText)item).text;
				builder.append(text).append('\n');
			}else
			if (item instanceof SubLinkLink){
				text = ((SubLinkLink)item).link;
				builder.append(text).append('\n');
			}else
			if (item instanceof SubLinkMap){
				text = ((SubLinkMap)item).map;
				builder.append(text).append('\n');
			}
		}

		if (builder.length() == 0) return false;

		try{
			sendSMS(tempOptions.receiverPhoneNumber, builder.toString(), context);
		}finally{
			builder.setLength(0);
		}

		return true;
	}

	//http://www.mobilab.ru/androiddev/smsandroid.html
	private void sendSMS(String phoneNumber, String message, Context context)
	{
		SmsManager sms = SmsManager.getDefault();
		// if message length is too long messages are divided
		List<String> messages = sms.divideMessage(message);

		for (String msg : messages) {
			sms.sendTextMessage(phoneNumber, null, msg, sentPI, null);
		}
	}

	/*	@Override
	public void setOptions(Object options)
	{
		//empty
	}*/

	@Override
	public void setTempOptions(MessageProviderOptions options)
	{
		tempOptions = (SMSProviderOptions)options;
	}

	@Override
	public void saveOptions(Object options)
	{
		//empty
	}
}

/*
<uses-permission android:name="android.permission.SEND_SMS">
</uses-permission>

        sentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Unknown Error";

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Sent Successfully!!!";
                        //return;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error : No Service Available";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error : Null PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error : Radio is off";
                        break;
                    default:
                        break;
                }

                tvErrMsg.setText(s);
            }
        };
 */