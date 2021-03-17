package br.com.isaquebrb.votesession.integration;

import br.com.isaquebrb.votesession.domain.dto.UserInfo;
import br.com.isaquebrb.votesession.domain.enums.UserInfoStatus;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoClient userInfoIntegration;

    public boolean isAbleToVote(String document) {
        try {
            Optional<UserInfo> user = userInfoIntegration.getUserInfo(document);
            if (user.isPresent() && user.get().getStatus() != null &&
                    UserInfoStatus.valueOf(user.get().getStatus()).equals(UserInfoStatus.ABLE_TO_VOTE)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            log.error("Method isAbleToVote - A resposta da integracao UserInfo nao esta de acordo.", e);
        } catch (FeignException e) {
            log.error("Method isAbleToVote - Erro na requisicao a integracao UserInfo.", e);
        }
        return false;
    }
}
