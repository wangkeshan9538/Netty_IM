package Test;

import java.util.concurrent.TimeUnit;

public class TestVol3 {
    public static int[] arr = new int[20];
    public static  boolean flag = true;


    public static void main(String[] args) throws Exception {
        new Thread(new Thread() {
            @Override
            public void run() {
                //线程1
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                    //arr[19] = 2;
                    flag = false;
                    //TimeUnit.MILLISECONDS.sleep(20000);
                    //System.out.println("end 1");
                    int count=0;
                    while(true){
                        count++;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Thread() {
            @Override
            public void run() {
                //线程2
                while (flag) {
                   System.out.println("flag--->");  //就这个影响了 刷新，有就刷，没有就不刷，很奇怪
//                    if () {
//                        System.out.println("flag in loop--->" + flag);
//                        break;
//                    }
                }
                System.out.println("Jump out of the loop!");
            }
        }).start();

    }
}
