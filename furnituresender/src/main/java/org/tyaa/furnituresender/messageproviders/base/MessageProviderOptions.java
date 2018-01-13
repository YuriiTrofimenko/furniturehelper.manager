package org.tyaa.furnituresender.messageproviders.base;

import android.content.Context;

/**
@author Bokov Valery
 */
public class MessageProviderOptions{
	private static Context context_;

	public MessageProviderOptions(){

	}

	public static Context getContext(){
		return context_;
	}

	public static void setContext(Context context){
		context_ = context;
	}
}
