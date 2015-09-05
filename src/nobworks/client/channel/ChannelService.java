package nobworks.client.channel;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// see web.xml <servlet-mapping>

@RemoteServiceRelativePath("channelService")
public interface ChannelService extends RemoteService {
	public String join( String channelName );
	public void command( String token, String channel, String command );	// 不要
}
