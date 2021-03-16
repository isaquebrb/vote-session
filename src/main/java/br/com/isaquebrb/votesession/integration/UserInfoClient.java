package br.com.isaquebrb.votesession.integration;

import br.com.isaquebrb.votesession.domain.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "userInfo", url = "${url-user-info}")
public interface UserInfoClient {

    @GetMapping("/{document}")
    Optional<UserInfo> getUserInfo(@PathVariable("document") String document);

}
