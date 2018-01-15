package org.tyaa.furnituresender.messagehelper;

import android.content.Context;
import android.databinding.ObservableArrayList;

import java.util.HashMap;

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.furnituresender.messageproviders.base.IMessageProvider;

/**
@author Bokov Valery
This type can send messages via different message providers
 */
public final class MessageHelper{

	private static final HashMap<String, ProviderData> providers = new HashMap<>();
	private static final MessageHelper instance = new MessageHelper();
	private static Context context;

	private MessageHelper(){
	}

	public static MessageHelper getInstance(Context _context){

		context = _context;
		return instance;
	}

	public void registerProvider(String providerName, IMessageProvider provider){

		providers.put(providerName, new ProviderData(provider));
	}

	public void unregisterProvider(String providerName){
		providers.remove(providerName);
	}

	public ProviderData getProviderData(String providerName){
		return providers.get(providerName);
	}
	//https://www.youtube.com/watch?v=RtMCPyhoALg
	//https://habrahabr.ru/post/243411/
	public boolean sendMessages(ObservableArrayList<LinkListItem> list){
		boolean messagesSent = false;

		for(final LinkListItem item : list){
			//send one message via diffrend providers
			for(final ProviderData data : providers.values()){
				if (data.enabled && data.provider.sendMessage(item.subLinks.mSubLinks, context))
					messagesSent = true;
			}
		}

		return messagesSent;
	}
}