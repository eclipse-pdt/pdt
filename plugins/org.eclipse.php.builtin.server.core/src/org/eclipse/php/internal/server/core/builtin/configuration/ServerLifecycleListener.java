package org.eclipse.php.internal.server.core.builtin.configuration;

import org.eclipse.php.internal.server.core.builtin.PHPServer;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerLifecycleListener;

public class ServerLifecycleListener implements IServerLifecycleListener {

	@Override
	public void serverAdded(IServer server) {
		if (!server.getServerType().getId().equals(PHPServer.TYPE_ID)) {
			return;
		}
		PHPServer phpServer = (PHPServer) server.loadAdapter(PHPServer.class, null);
		phpServer.registerPHPServer();
	}

	@Override
	public void serverChanged(IServer server) {
		if (!server.getServerType().getId().equals(PHPServer.TYPE_ID)) {
			return;
		}
		System.out.println(server.loadAdapter(PHPServer.class, null));
		PHPServer phpServer = (PHPServer) server.loadAdapter(PHPServer.class, null);
		phpServer.updatePHPServer();
	}

	@Override
	public void serverRemoved(IServer server) {
		if (!server.getServerType().getId().equals(PHPServer.TYPE_ID)) {
			return;
		}
		PHPServer phpServer = (PHPServer) server.loadAdapter(PHPServer.class, null);
		phpServer.removePHPServer();
	}

}
