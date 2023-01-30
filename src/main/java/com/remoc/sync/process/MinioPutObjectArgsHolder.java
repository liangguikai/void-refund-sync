package com.remoc.sync.process;

import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MinioPutObjectArgsHolder {

    public static PutObjectArgs pngArgs(InputStream inputStream, String objName) {
        return PutObjectArgs.builder()
                .bucket("aone")
                .object(objName + ".png")
                .stream(inputStream, -1, 10485760)
                .contentType("image/png")
                .build();
    }

    public static PutObjectArgs mp4Args(FileInputStream inputStream, String objName) throws IOException {
        return PutObjectArgs.builder().bucket("aone").object(objName + ".mp4").stream(inputStream, inputStream.available(), -1).build();

    }

}
