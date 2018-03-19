package zth.com.gezhi.okhttp.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(value= RetentionPolicy.RUNTIME)
public @interface NetWorkAnnotation {
    String url();
}