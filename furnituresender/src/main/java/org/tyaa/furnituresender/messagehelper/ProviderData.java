package org.tyaa.furnituresender.messagehelper;

import org.tyaa.furnituresender.messageproviders.base.IMessageProvider;

/**
Holds IMessageProvider instance and additional information for him
@author Bokov Valery
 */
public class ProviderData{
	public ProviderData(IMessageProvider provider) {
		this.provider = provider;
		enabled = true;
	}

	public IMessageProvider provider;
	public boolean enabled;
}