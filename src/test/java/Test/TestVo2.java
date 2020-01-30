package Test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class TestVo2 {

    public static Person p = new Person("", 1);
    public static volatile Person[] arr =   new Person[1];

    public static void main(String[] args) throws Exception {
        new Thread(new Thread() {
            @Override
            public void run() {
                //线程1
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                    arr[0] = new Person("xixi",12);
                    //arr=arr;
                    //Person p1 = (Person) arr[0];
                    //p.setAge(12);
                    //p1.setAge(12);
                    //System.out.println("end 1 ");
                    int count = 1;
                    while (true) {
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
                //Object[] tab=arr;
                while (true) {
                    Person p= (Person) arr[0];
                    //Person p = (Person) unsafe.getObjectVolatile(tab,(0L << ASHIFT) + ABASE) ;

                    if (p != null && p.getAge() == 12) {
                        break;
                    }
                    //System.out.println("o");

                }
                System.out.println("Jump out of the loop!");
            }
        }).start();

    }

    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final long ABASE;
    private static final int ASHIFT;
    static  Unsafe unsafe=reflectGetUnsafe();
    static {
        try {

            Class<?> ak = Person[].class;
            ABASE = unsafe.arrayBaseOffset(ak);
            int scale = unsafe.arrayIndexScale(ak);
            if ((scale & (scale - 1)) != 0)
                throw new Error("data type scale not a power of two");
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


