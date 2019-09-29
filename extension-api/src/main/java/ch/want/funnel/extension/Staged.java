/*
 * Created on 29.09.2019
 */
package ch.want.funnel.extension;

/**
 * If an extension implements this interface, either of the two methods will be called immediately after construction.
 * Assume a sequence
 * <ol>
 * <li>FunnelExtension myExtension = new FunnelExtensionImpl();</li>
 * <li>if (myExtension instanceof Staged) {((Staged)myExtension).setProductionStage();}</li>
 * <li>myExtension.consume(); // or produce, or whatever the extension implements</li>
 * </ol>
 */
public interface Staged {

    void setTestStage();

    void setProductionStage();
}
