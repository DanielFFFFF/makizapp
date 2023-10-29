package fr.makizart.data;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface NoVideoAndImageTogether {
    String message() default "VideoAsset and ImageAsset cannot be present together.";
    String[] columnNames();
}