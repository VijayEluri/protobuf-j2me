/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ponderingpanda.protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author ralf
 */
public class Util {
    /** Only use this when this will be the only message written to the stream. */
    public static void writeMessage(OutputStream out, Message message) throws IOException {
        CodedOutputStream coded = new CodedOutputStream(out);
        message.serialize(coded);
    }

    /** Reads until the end of the stream. */
    public static void readMessage(InputStream in, Message message) throws IOException {
        CodedInputStream coded = new CodedInputStream(in);
        message.deserialize(coded);
    }

    public static void writeMessageWithSize(OutputStream out, Message message) throws IOException {
        CodedOutputStream coded = new CodedOutputStream(out);
        byte[] bytes = messageToBytes(message);
        coded.writeRawVarint32(bytes.length);
        out.write(bytes);
    }

    public static void writeDelimitedMessage(OutputStream out, Message message) throws IOException {
        CodedOutputStream coded = new CodedOutputStream(out);
        message.serialize(coded);
        coded.writeRawVarint32(0);
    }

    public static byte[] messageToBytes(Message message) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CodedOutputStream coded = new CodedOutputStream(out);
        message.serialize(coded);
        return out.toByteArray();
    }

    public static void messageFromBytes(byte[] in, Message message) throws IOException {
        CodedInputStream coded = new CodedInputStream(new ByteArrayInputStream(in));
        message.deserialize(coded);
    }

    public static void readMessageWithSize(InputStream in, Message message) throws IOException {
        CodedInputStream coded = new CodedInputStream(in);
        coded.readMessage(message);
    }

    public static void readDelimitedMessage(InputStream in, Message message) throws IOException {
        CodedInputStream coded = new CodedInputStream(in);
        message.deserialize(coded);
        if(coded.isEndOfStream())
            throw new IOException("End of stream");
    }
}