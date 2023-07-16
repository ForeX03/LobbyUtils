package api.serverchecker;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

class PacketUtils {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static void writeString(DataOutputStream out, String s, Charset charset) throws IOException {
        if (charset == UTF8) {
            writeVarInt(out, s.length());
        } else {
            out.writeShort(s.length());
        }
        out.write(s.getBytes(charset));
    }

    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5)
                throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128)
                return i;
        }
    }

    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while ((paramInt & 0xFFFFFF80) != 0) {
            out.write(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
        out.write(paramInt);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException iOException) {}
    }
}

