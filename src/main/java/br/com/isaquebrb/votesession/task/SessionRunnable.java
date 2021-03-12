package br.com.isaquebrb.votesession.task;

import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SessionRunnable implements Runnable {

    private final Topic topic;
    private final SessionService service;

    @Override
    public void run() {
        service.closeSession(topic);
    }
}
