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


    public void writeBytes(ByteBuffer buffer){
        buffer.rewind();
        Future<Integer> res = channel.write(buffer, writePosition);
        try {
            writePosition = writePosition + (long)res.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
    }

    public ByteBuffer readBytes(){
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        Future<Integer> future = channel.read(buffer, readPosition);
        int readByte = 0;
        try {
            while(!future.isDone()){}
            readPosition = readPosition + future.get();
            readByte = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return (ByteBuffer) buffer.limit(readByte);
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
}
