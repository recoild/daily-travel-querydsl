//package com.fisa.dailytravel;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class AutoMapper {
//
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public AutoMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public <S, D> D map(S source, Class<D> destinationType) {
//        return modelMapper.map(source, destinationType);
//    }
//
////    // 객체의 필드 이름과 값을 가져오는 메서드
////    private static Map<String, Object> getFieldValues(Object obj) throws IllegalAccessException {
////        Map<String, Object> fieldValues = new HashMap<>();
////        for (Field field : obj.getClass().getDeclaredFields()) {
////            field.setAccessible(true);
////            fieldValues.put(field.getName(), field.get(obj));
////        }
////        return fieldValues;
////    }
////
////    // 객체의 필드에 값을 설정하는 메서드
////    private static void setFieldValues(Object obj, Map<String, Object> fieldValues) throws IllegalAccessException {
////        for (Field field : obj.getClass().getDeclaredFields()) {
////            field.setAccessible(true);
////            if (fieldValues.containsKey(field.getName())) {
////                field.set(obj, fieldValues.get(field.getName()));
////            }
////        }
////    }
////
////    // 필드 타입이 일치하는 경우에만 값을 매핑하는 메서드
////    private static void mapFields(Object source, Object destination) throws IllegalAccessException {
////        Map<String, Object> sourceFieldValues = getFieldValues(source);
////
////        for (Field destField : destination.getClass().getDeclaredFields()) {
////            destField.setAccessible(true);
////            if (sourceFieldValues.containsKey(destField.getName())) {
////                Object value = sourceFieldValues.get(destField.getName());
////                if (value == null || destField.getType().isInstance(value)) {
////                    destField.set(destination, value);
////                }
////            }
////        }
////    }
////
////    // 객체를 변환하는 메서드
////    public static <S, D> D map(S source, Class<D> destinationClass) {
////        try {
////            D destination = destinationClass.getDeclaredConstructor().newInstance();
////            mapFields(source, destination);
////            return destination;
////        } catch (Exception e) {
////            throw new RuntimeException("Error during mapping", e);
////        }
////    }
////
////    // 기존 객체를 업데이트하는 메서드
////    public static <S> void mapToExisting(S source, Object destination) {
////        try {
////            mapFields(source, destination);
////        } catch (IllegalAccessException e) {
////            throw new RuntimeException("Error during mapping to existing object", e);
////        }
////    }
//}