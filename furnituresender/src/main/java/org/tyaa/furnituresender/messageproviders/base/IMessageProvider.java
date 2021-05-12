package org.tyaa.furnituresender.messageproviders.base;

import android.content.Context;
import androidx.databinding.ObservableArrayList;

import org.tyaa.fhelpermodel.interfaces.ISubLink;

import java.util.ArrayList;

/**
@author Bokov Valery
 */
public interface IMessageProvider{
	/**Sets options for provider which changes each time before sending of message.*/
	void setTempOptions(MessageProviderOptions options);
	/**Saves stable options of provider to options instance*/
	void saveOptions(Object/*SharedPreferences?*/ options);
	//	/**Saves stable options of provider from options instance*/
	//	void setOptions(MessageProviderOptions options);

	/**Sends message.
	@param content Some instance of ISubLink.
	@return True if message sent with one of more items from content. False if all items in content are not applicable for this message
	@exception Can't send message. See message information.*/
	boolean sendMessage(final ArrayList<ISubLink> content, Context context);
}
