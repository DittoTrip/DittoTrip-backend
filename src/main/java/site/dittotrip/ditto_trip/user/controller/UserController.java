package site.dittotrip.ditto_trip.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.user.domain.dto.UserListRes;
import site.dittotrip.ditto_trip.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list/search")
    @Operation(summary = "유저 리스트 검색 조회",
            description = "")
    public UserListRes userList(@RequestParam(name = "query") String query,
                                Pageable pageable) {
        return userService.findUserList(query, pageable);
    }

}
