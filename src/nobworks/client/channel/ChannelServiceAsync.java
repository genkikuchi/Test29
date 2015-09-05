package nobworks.client.channel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChannelServiceAsync {
	public void join( String channelName, AsyncCallback<String> asyncCallback );
	public void command( String token, String channel, String message, AsyncCallback<Void> asyncCallback );
}
