package nobworks.client.channel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.List;

import nobworks.client.common.FailListener;

public class Channel {
	private final List<ChannelListener> channelListeners;
	private final String channelName;
	private final ChannelServiceAsync channelService = GWT.create( ChannelService.class );
	private String token;

	public Channel( String channelName ) {
		this.channelName = channelName;
		channelListeners = new ArrayList<ChannelListener>();
	}

	private void onMessage( String message ) {
		for ( int i = 0; i < channelListeners.size(); i++ ) {
			channelListeners.get( i ).onMessage( message );
		}
	}

	private void onOpen() {
		for ( int i = 0; i < channelListeners.size(); i++ ) {
			channelListeners.get( i ).onOpen();
		}
	}

	private void onError( int code, String description ) {
		for ( int i = 0; i < channelListeners.size(); i++ ) {
			channelListeners.get( i ).onError( code, description );
		}
	}

	private void onClose() {
		for ( int i = 0; i < channelListeners.size(); i++ ) {
			channelListeners.get( i ).onClose();
		}
	}

	public void join( final FailListener failListener ) {
		channelService.join( channelName, new AsyncCallback<String>() {
			@Override
			public void onFailure( Throwable throwable ) {
GWT.log( "Channel.join():onFailure=" + throwable.getMessage() );
				failListener.onFailure( null );
			}
			@Override
			public void onSuccess( String t ) {
GWT.log( "Channel.join():onSuccess()" );
				token = t;
				join( token );
			}
		} );
	}
	public String getToken() {
		return token;
	}

	public void addChannelListener( ChannelListener channelListener ) {
		channelListeners.add( channelListener );
	}

	public void removeChannelListener( ChannelListener channelListener ) {
		channelListeners.remove( channelListener );
	}

	private native void join( String channelKey ) /*-{
		var channel = new $wnd.goog.appengine.Channel(channelKey);
		var socket = channel.open();
		var self = this;

		socket.onmessage = function(evt) {
			var data = evt.data;
			self.@nobworks.client.channel.Channel::onMessage(Ljava/lang/String;)(data);
		};

		socket.onopen = function() {
			self.@nobworks.client.channel.Channel::onOpen()();
		};

		socket.onerror = function(error) {
			self.@nobworks.client.channel.Channel::onError(ILjava/lang/String;)(error.code, error.description);
		};

		socket.onclose = function() {
			self.@nobworks.client.channel.Channel::onClose()();
		};
	}-*/;

	public void send( String message ) {
		send( message, new AsyncCallback<Void>() {
			@Override
			public void onFailure( Throwable throwable ) {
			}

			@Override
			public void onSuccess( Void aVoid ) {
			}
		} );
	}

	public void send( String message, AsyncCallback<Void> callback ) {
		channelService.command( token, channelName, message, callback );
	}
}
