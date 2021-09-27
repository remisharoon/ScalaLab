package designpatterns;

public class SingletonPattern {

    static String hello = "Hello";

    private SingletonPattern(){
        System.out.println("In private constructor");
    }

    private static class InstanceHolder{
        static SingletonPattern INSTANCE = new SingletonPattern();
    }

    public static SingletonPattern getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("main starts");

        System.out.println(String.format("Say %s", SingletonPattern.hello));

        SingletonPattern si;
        System.out.println("declared si");

        si = null;
        System.out.println("assigned si");


        System.out.println("getting si instance");
        si = SingletonPattern.getInstance();
        System.out.println(si);

    }
}
