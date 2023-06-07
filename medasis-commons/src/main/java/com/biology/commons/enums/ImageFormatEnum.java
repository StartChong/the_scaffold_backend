
package com.biology.commons.enums;

public enum ImageFormatEnum {
    JPG(".jpg"),
    PNG(".png"),
    WEBP(".webp"),
    GIF(".gif");

    private final String extension;

    ImageFormatEnum(String s) {
        extension = s;
    }

    public String getFileExtension() {
        return extension;
    }
}
