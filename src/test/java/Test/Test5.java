package Test;


import java.util.concurrent.TimeUnit;

public class Test5 {
    public static volatile String flag="1";
    public static volatile  String vol="2";
    public static String noVol="3";


    public static void main(String[] args) throws Exception {
        //线程1
        new Thread(new Thread(){
            @Override
            public void run() {
                //Thread A
                System.out.println(noVol);
            while(true){
                String s=vol;
                if(noVol.equals("33")){

                    break;
                }
            }
            }
        }).start();
        //线程2
        new Thread(new Thread(){
            @Override
            public void run() {
                //Thread B
                try {
                    TimeUnit.MILLISECONDS.sleep(2000L);
                    //setVol( "22");
                    //setFlag( "2");
                    //vol="22";
                    noVol= "33";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}











