package com.nawhew.awsboot.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {

    @Test
    @DisplayName("real_profile을 찾습니다.")
    public void findReal_profile () {
        // given
        String expectedProfile = "real";
        MockEnvironment environment = new MockEnvironment();
        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("oauth");
        environment.addActiveProfile("real-db");
        ProfileController profileController = new ProfileController(environment);

        // when
        String profile = profileController.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real_profile이 없는 경우 default_profile을 찾습니다.")
    public void findDefault_profile () {
        // given
        String expectedProfile = "default";
        MockEnvironment environment = new MockEnvironment();
        ProfileController profileController = new ProfileController(environment);

        // when
        String profile = profileController.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}