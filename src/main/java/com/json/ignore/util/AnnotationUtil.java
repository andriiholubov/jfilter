package com.json.ignore.util;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.file.FileConfig;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotation util class
 * <p>
 * This is util class used to help find annotations in class or method
 */
public class AnnotationUtil {

    /**
     * Search for specified type list of annotation
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotations
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T>             {@link Annotation} generic class
     * @return {@link Annotation} list of found annotations if found, else an array of length zero
     */
    public static <T extends Annotation> T[] getDeclaredAnnotations(MethodParameter methodParameter, Class<T> annotationClass) {
        T[] annotations = methodParameter.getMethod().getDeclaredAnnotationsByType(annotationClass);
        Class<?> containingClass = methodParameter.getContainingClass();
        annotations = annotations.length != 0 ? annotations : containingClass.getDeclaredAnnotationsByType(annotationClass);
        return annotations;
    }

    public static <T extends Annotation> T getDeclaredAnnotation(MethodParameter methodParameter, Class<T> annotationClass) {
        //Get annotation from method
        T annotation = methodParameter.getMethod().getDeclaredAnnotation(annotationClass);
        //If annotation is null try to get annotation from containing class
        annotation = annotation != null ? annotation : methodParameter.getContainingClass().getDeclaredAnnotation(annotationClass);

        return annotation;
    }

    /**
     * Search for {@link FieldFilterSetting} in method
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotation
     * @return list of {@link FieldFilterSetting} if this type of annotation declared in method
     */
    public static FieldFilterSetting[] getSettingAnnotations(MethodParameter methodParameter) {
        FieldFilterSettings settings = getDeclaredAnnotation(methodParameter, FieldFilterSettings.class);
        if (settings != null) {
            return settings.value();
        } else
            return getDeclaredAnnotations(methodParameter, FieldFilterSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public static SessionStrategy[] getStrategyAnnotations(MethodParameter methodParameter) {
        SessionStrategies strategies = getDeclaredAnnotation(methodParameter, SessionStrategies.class);
        if (strategies != null) {
            return strategies.value();
        } else
            return getDeclaredAnnotations(methodParameter, SessionStrategy.class);
    }

    /**
     * Gets class by name
     * <P>
     * Try to get class by it full name. If class couldn't be found, returns null
     * @param className {@link String} class name. Example: java.io.File
     * @return {@link Class} return class, else null
     */
    public static Class getClassByName(String className) {
        if (className != null && !className.isEmpty()) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else
            return null;
    }




}
