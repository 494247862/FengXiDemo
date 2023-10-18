package com.fengxi.auth.dto;

import lombok.Data;

@Data
public class GetPhoneParam {
    public String encryptedData;
    public String sessionKey;
    public String iv;
}
