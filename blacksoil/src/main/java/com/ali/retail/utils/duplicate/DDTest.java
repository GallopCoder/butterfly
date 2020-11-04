package com.ali.retail.utils.duplicate;

import com.datacentor.dup.DDAlert;
import com.datacentor.dup.DupDetectService;
import com.datacentor.dup.DupDetector;

import java.util.UUID;

public class DDTest {

    public static void main(String[] args) {

        for(int i = 0; i < 3; i++) {
            InnerThread innerThread = new InnerThread();
            innerThread.start();
        }


    }

    static class InnerThread extends Thread {
        @Override
        public void run() {
            DupDetectService.initialise("127.0.0.1", 7010);
            DDrPool pool = new DDrPool(4,new DDAlert());
            DupDetector dd = null;
            do {
                try {
                    dd = pool.get();
                    byte[] bb = UUID.randomUUID().toString().getBytes();
                    dd.dupCheck(bb, DupDetector.MODE_NEW_ID);
                    System.out.println(dd);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(dd!=null)
                        pool.release(dd);

                }
            } while (true);
        }
    }

}
