import jdk.nashorn.internal.objects.annotations.Constructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: zjh
 * @Date: 2021/5/26 17:34
 * @Version 1.0
 */
class User{

    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public User(int age,String name){

        this.age = age;
        this.name = name;

    }

}

//原子引用
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User(23,"z3");
        User l4 = new User(25,"l4");

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3,l4)+","+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,l4)+","+atomicReference.get().toString());
    }

}
