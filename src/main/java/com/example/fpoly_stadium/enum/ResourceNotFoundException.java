package com.example.fpoly_stadium.services;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);  // Truyền thông điệp vào constructor của RuntimeException
    }

    // Constructor nhận thông điệp lỗi và đối tượng gây ra exception
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);  // Truyền thông điệp và nguyên nhân lỗi
    }
}
