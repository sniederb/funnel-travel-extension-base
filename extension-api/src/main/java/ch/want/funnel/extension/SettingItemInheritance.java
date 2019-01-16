/*
 * Created on 8 Jan 2019
 */
package ch.want.funnel.extension;

public enum SettingItemInheritance {
    /**
     * The setting item is only allowed on the account-level. Typical examples
     * are API keys or third-party system client IDs
     */
    ACCOUNT_ONLY,
    /**
     * The setting item is available on the account-level, but can be overwritten
     * on the orgunit-level. Typical example are e-mail credentials.
     */
    ACCOUNT_AND_ORGUNIT;
}
