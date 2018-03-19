package zth.com.gezhi.prop;

/**
 * Created by Kotle on 2017/9/25.
 * 类似于java8的高级函数
 * 其实就是一个回调而已
 */

public interface Fun<T> {
     void execute(T t);
}
