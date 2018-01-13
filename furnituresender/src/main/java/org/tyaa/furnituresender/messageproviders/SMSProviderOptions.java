package org.tyaa.furnituresender.messageproviders;

import org.tyaa.furnituresender.messageproviders.base.MessageProviderOptions;

/**
@author Bokov Valery
 */
public class SMSProviderOptions extends MessageProviderOptions{
	public String receiverPhoneNumber;

	public SMSProviderOptions(String phoneNumber){
		this.receiverPhoneNumber = phoneNumber;
	}
}
