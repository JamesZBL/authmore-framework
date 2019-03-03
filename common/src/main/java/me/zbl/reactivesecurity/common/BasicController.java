package me.zbl.reactivesecurity.common;

import me.zbl.reactivesecurity.common.entity.ResponseContent;
import me.zbl.reactivesecurity.common.entity.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
public class BasicController {

    public ResponseEntity success() {
        return new ResponseEntity(new ResponseContent("", "success"), HttpStatus.OK);
    }

    public ResponseEntity error() {
        return new ResponseEntity(new ResponseContent("", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity badRequest() {
        return new ResponseEntity(new ResponseContent("", "invalid request"), HttpStatus.BAD_REQUEST);
    }

    public Map exist(boolean exist) {
        return map().put("result", exist).map();
    }

    public ResultBuilder map() {
        return new ResultBuilder();
    }
}
