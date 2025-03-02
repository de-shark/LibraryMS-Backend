package me.deshark.lms.interfaces.controller;

import me.deshark.lms.interfaces.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<Void>> test() {
        return ResponseEntity.ok(ApiResponse.<Void>builder().build());
    }
}
