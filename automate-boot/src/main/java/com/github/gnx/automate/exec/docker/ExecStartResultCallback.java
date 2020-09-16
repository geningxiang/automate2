package com.github.gnx.automate.exec.docker;

import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.Frame;
import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.IMsgListener;

import java.nio.charset.Charset;

public class ExecStartResultCallback extends ResultCallbackTemplate<ExecStartResultCallback, Frame> {

    private final IMsgListener execListener;
    private Charset charset = Charsets.UTF_8;

    public ExecStartResultCallback(IMsgListener execListener) {
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
                        this.execListener.append(new String(frame.getPayload(), charset));
                    }
                    break;
                default:

            }
        }
    }
}