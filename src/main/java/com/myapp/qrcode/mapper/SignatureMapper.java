package com.myapp.qrcode.mapper;

import com.myapp.qrcode.dto.CreateSignatureRequest;
import com.myapp.qrcode.dto.SignatureResponse;
import com.myapp.qrcode.model.SignatureData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.time.LocalDate;

@Mapper
public interface SignatureMapper {

    SignatureMapper INSTANCE = Mappers.getMapper(SignatureMapper.class);

    // Mapping vers DTO de réponse
    SignatureResponse toSignatureResponse(SignatureData data);

    // Mapping depuis le DTO de création (CreateSignatureRequest)
    @Mapping(target = "dateCreation", expression = "java(LocalDate.now())")
    @Mapping(target = "dateExpiration", expression = "java(LocalDate.now().plusMonths(6))")
    SignatureData toSignatureData(CreateSignatureRequest request);
}