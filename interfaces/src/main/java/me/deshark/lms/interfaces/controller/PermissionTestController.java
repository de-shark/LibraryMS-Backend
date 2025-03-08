package me.deshark.lms.interfaces.controller;

import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/test")
public class PermissionTestController {

    @GetMapping
    public ResponseEntity<ResultBody<Void>> test() {
        return ResponseEntity.ok(ResultBody.<Void>builder().build());
    }
}
