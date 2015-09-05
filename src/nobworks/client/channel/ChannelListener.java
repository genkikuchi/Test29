package nobworks.client.channel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ChannelListener extends RemoteService {

	void onMessage( String message );

	void onOpen();

	void onError( int code, String description );

	void onClose();
}
