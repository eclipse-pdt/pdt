package org.eclipse.php.debug.core.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnknownMessageResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {
	
	int fOrigMessageType;
	
	/**
	 * Returns original message type as received by profiler
	 * @return int original message type
	 */
	public int getOriginalMessageType() {
		return fOrigMessageType;
	}
	
	private void setOriginalMessageType(int origMessageType) {
		fOrigMessageType = origMessageType;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setOriginalMessageType(in.readInt());
	}

	public int getType() {
		return 1000;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getOriginalMessageType());
	}
}
