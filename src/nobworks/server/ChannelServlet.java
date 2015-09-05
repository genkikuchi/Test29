package nobworks.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

import net.arnx.jsonic.JSON;
import nobworks.client.channel.ChannelService;
import nobworks.shared.MyFactory;
import nobworks.shared.MyMessage;
import nobworks.shared.URLBeanFactory;
import nobworks.shared.URLBeanInterface;
import nobworks.shared.URLFactory;
import nobworks.shared.URLInterface;

public class ChannelServlet extends RemoteServiceServlet implements ChannelService {

	public ChannelServlet() {
	}

	public void doGet( HttpServletRequest req, HttpServletResponse resp ) throws IOException {
		// sample: http://genkikuchi001.appspot.com/nanosignageplayer/channelService
		// 注意：url は /nanosignageplayer/channelService
		// TODO:Application Server から json が渡される。
		String channelUUID = req.getParameter( "channelUUID" );
		String json = req.getParameter( "json" );
		json = "doGet が実行:" + json;
		send( channelUUID, json );
		// doPost はオーバーライドできないので、他のサーブレット(JsonPostReceiver)を経由する
	}
	
	/*
	 * @param json json以外の場合もある。Ex) "resend"
	 */
	public static void send( String channel, String json ) {
// UtilService.log( "ChannelServlet.send() channel=" + channel + " json=" + json );
		ChannelServiceFactory.getChannelService().sendMessage( new ChannelMessage( channel, json ) );
	}
	@Override
	public final String join( String channelName ) {
		String token = ChannelServiceFactory.getChannelService().createChannel( channelName );
//UtilService.log( "ChannelServlet.join() channelName=" + channelName + " token=" + token );
		return token;
	}

	private void sendStopMessage( String channelName ) {
		send( channelName, "stop message" );
	}

	@Override
    public void command( String token, String channel, String command ) {
		System.out.println( ">>>> Enter ChannelServlet.command()" );
    }
}
