package com.modules.system.vo;

import lombok.Data;

/**
 * @author v_vllchen
 */
@Data
public class PhoneRequest {
    private String encryptedData;
    private String iv;
    private String sessionKey;
}
