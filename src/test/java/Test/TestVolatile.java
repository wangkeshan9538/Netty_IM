package Test;

        import java.util.concurrent.TimeUnit;

public class TestVolatile {
    public static volatile   long[] arr = new long[20];
    public static void main(String[] args) throws Exception {
        //线程1
        new Thread(new Thread(){
            @Override
            public void run() {
                //Thread A
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("end 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                arr[19] = 2;
            }
        }).start();
        //线程2
        new Thread(new Thread(){
            @Override
            public void run() {
                //Thread B
                while (arr[19] != 2) {
                    System.out.println("test");
                }
                System.out.println("Jump out of the loop!");
            }
        }).start();
    }
}