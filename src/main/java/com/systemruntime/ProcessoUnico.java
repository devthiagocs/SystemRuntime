package com.systemruntime;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ProcessoUnico {
    private static FileChannel canal;
    private static FileLock bloqueio;

    public static boolean iniciar() throws Exception {
        File arquivo = new File("systemruntime.lock");
        canal = new FileOutputStream(arquivo).getChannel();
        bloqueio = canal.tryLock();

        if (bloqueio == null) {
            canal.close();
            return false;
        }

        return true;
    }
}
