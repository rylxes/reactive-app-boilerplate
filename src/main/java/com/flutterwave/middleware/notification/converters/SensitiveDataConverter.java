package com.flutterwave.middleware.notification.converters;


import com.flutterwave.middleware.notification.utils.Encryptor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SensitiveDataConverter implements AttributeConverter<String, String> {

  @Value("${maven.version}")
  private String key;

  @Override
  public String convertToDatabaseColumn(String data) {
    if (data == null) {
      return null;
    }
    return Encryptor.encryptWithJasypt(key, data);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return Encryptor.decryptWithJasypt(key, dbData);
  }

}