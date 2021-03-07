package com.nawhew.awsboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * nginx를 이용한 무중단 배포를 위한 클래스
 * 배포 시 포트 선정을 위한 properties 선택
 */
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final Environment environment;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(this.environment.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()
                                            ? "default"
                                            : profiles.get(0);

        return profiles.stream().filter(realProfiles::contains)
                    .findAny()
                    .orElse(defaultProfile);
    }
}
