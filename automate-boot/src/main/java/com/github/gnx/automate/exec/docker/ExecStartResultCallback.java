package com.github.gnx.automate.exec.docker;

import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.Frame;
import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.IExecListener;

import java.nio.charset.Charset;

public class ExecStartResultCallback extends ResultCallbackTemplate<ExecStartResultCallback, Frame> {

    private final IExecListener execListener;
    private Charset charset = Charsets.UTF_8;

    public ExecStartResultCallback(IExecListener execListener) {
        this.execListener = execListener;
    }


    @Override
    public void onNext(Frame frame) {
        if (frame != null) {
            switch (frame.getStreamType()) {
                case STDIN:
                    System.out.println("[STDIN]" + new String(frame.getPayload(), charset));
                    break;
                case STDOUT:
                case RAW:
                case STDERR:
                    if (this.execListener != null) {
                        this.execListener.onMsg(new String(frame.getPayload(), charset));
                    }
                    break;
                default:

            }
        }
    }
}