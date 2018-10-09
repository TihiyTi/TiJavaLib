package com.ti;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FileService {

    private AsynchronousFileChannel channel;
    private long writePosition = 0;
    private long readPosition = 0;
    private String fileName = "default.txt";
    private boolean endOfFile = false;

    public FileService(){
        try {
            channel = AsynchronousFileChannel.open(Paths.get(fileName),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileService(String fileName){
        this.fileName = fileName;
        preparePath();
        try {
            channel = AsynchronousFileChannel.open(Paths.get(fileName),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.READ,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileService(Path path){
        try {
            channel = AsynchronousFileChannel.open(path,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int writeBytes(ByteBuffer buffer){
        buffer.rewind();
        Future<Integer> res = channel.write(buffer, writePosition);
        try {
            int num = res.get();
            writePosition = writePosition + num;
            return num;
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
            return 0;
        }
    }

    public ByteBuffer readBytes(){
        return readBytes(1000);
    }

    public ByteBuffer readBytes(int byteToRead){
        ByteBuffer buffer = ByteBuffer.allocate(byteToRead);
        Future<Integer> future = channel.read(buffer, readPosition);
        int readByte = 0;
        try {
            while(!future.isDone()){}
            readPosition = readPosition + future.get();
            readByte = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(readByte < 0){
            readByte = 0;
            endOfFile = true;
        }
        return (ByteBuffer) buffer.limit(readByte);
    }

    public void closeChannel(){
        if(channel != null){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     private void preparePath(){
        if(fileName.contains("/")){
            String[] folders = fileName.split("/");
            String dirPath = fileName.substring(0,fileName.length() - folders[folders.length-1].length());
            try {
                Files.createDirectories(Paths.get(dirPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEndOfFile(){
        return endOfFile;
    }
}
