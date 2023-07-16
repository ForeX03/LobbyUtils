package api.serverchecker;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerPinger {
    public static PingResponse fetchData(String address, int port, int timeout) {
        try {
            Socket socket = null;
            DataOutputStream dataOut = null;
            DataInputStream dataIn = null;
            try {
                socket = new Socket(address, port);
                socket.setSoTimeout(timeout);
                dataOut = new DataOutputStream(socket.getOutputStream());
                dataIn = new DataInputStream(socket.getInputStream());
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                DataOutputStream handshake = new DataOutputStream(byteOut);
                handshake.write(0);
                PacketUtils.writeVarInt(handshake, 4);
                PacketUtils.writeString(handshake, address, PacketUtils.UTF8);
                handshake.writeShort(port);
                PacketUtils.writeVarInt(handshake, 1);
                byte[] bytes = byteOut.toByteArray();
                PacketUtils.writeVarInt(dataOut, bytes.length);
                dataOut.write(bytes);
                bytes = new byte[]{0};
                PacketUtils.writeVarInt(dataOut, bytes.length);
                dataOut.write(bytes);
                PacketUtils.readVarInt(dataIn);
                PacketUtils.readVarInt(dataIn);
                byte[] responseData = new byte[PacketUtils.readVarInt(dataIn)];
                dataIn.readFully(responseData);
                String jsonString = new String(responseData, PacketUtils.UTF8);
                return new PingResponse(jsonString, address, port);
            } finally {
                PacketUtils.closeQuietly(dataOut);
                PacketUtils.closeQuietly(dataIn);
                PacketUtils.closeQuietly(socket);
            }
        } catch(Exception e){
            return new PingResponse(false , "-", 0, 0);
        }
    }
}

