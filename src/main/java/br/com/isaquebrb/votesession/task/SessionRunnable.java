package br.com.isaquebrb.votesession.task;

import br.com.isaquebrb.votesession.service.SessionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SessionRunnable implements Runnable {

    private final Long sessionId;
    private final SessionService service;

    @Override
    public void run() {
        service.closeSession(sessionId);
    }
}
