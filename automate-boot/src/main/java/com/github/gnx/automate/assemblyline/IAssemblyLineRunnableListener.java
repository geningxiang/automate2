package com.github.gnx.automate.assemblyline;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:12
 */
public interface IAssemblyLineRunnableListener {

    void onStart(AssemblyLineRunnable assemblyLineRunnable);

    void onFinished(AssemblyLineRunnable assemblyLineRunnable);

}
