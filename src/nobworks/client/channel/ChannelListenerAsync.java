package nobworks.client.channel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChannelListenerAsync {

	void onClose( AsyncCallback<Void> callback );

	void onError( int code, String description, AsyncCallback<Void> callback );

	void onMessage( String message, AsyncCallback<Void> callback );

	void onOpen( AsyncCallback<Void> callback );

}
