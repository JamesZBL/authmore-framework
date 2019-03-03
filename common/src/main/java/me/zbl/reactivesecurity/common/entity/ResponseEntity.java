package me.zbl.reactivesecurity.common.entity;

import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
public class ResponseEntity extends org.springframework.http.ResponseEntity<ResponseContent> {

    public ResponseEntity(HttpStatus status) {
        super(status);
    }

    public ResponseEntity(ResponseContent body, HttpStatus status) {
        super(body, status);
    }

    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseEntity(ResponseContent body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
