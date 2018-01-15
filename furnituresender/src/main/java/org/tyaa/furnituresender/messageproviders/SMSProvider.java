package org.tyaa.furnituresender.messageproviders;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import org.tyaa.fhelpermodel.SubLinkText;
import org.tyaa.fhelpermodel.interfaces.ISubLink;
import org.tyaa.furnituresender.AfterSendActivity;
import org.tyaa.furnituresender.messageproviders.base.IMessageProvider;
import org.tyaa.furnituresender.messageproviders.base.MessageProviderOptions;

/**
 * FB, Telegram, VK, Google Maps, Viber
@author Bokov Valery
 */
public final class SMSProvider implements IMessageProvider {
	private SMSProviderOptions tempOptions;
	private final StringBuilder builder;

	public SMSProvider(){
		builder = new StringBuilder();
	}

	@Override
	public boolean sendMessage(final ObservableArrayList<ISubLink> content, Context context)
	{
		for(final ISubLink item : content){
			String text;

			if (item instanceof SubLinkText)
			{
				text = ((SubLinkText)item).text;
				//TODO max length of sms
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

	// 	import android.telephony.SmsManager;

	//http://www.mobilab.ru/androiddev/smsandroid.html
	private void sendSMS(String phoneNumber, String message, Context context)
	{
		final PendingIntent pi =
				PendingIntent.getActivity(
						context
						, 0
						, new Intent(MessageProviderOptions.getContext(), context.getClass())
						, 0
				);
		final SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);
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
<uses-permission android:name="android.permission.RECEIVE_SMS">
</uses-permission>
 */