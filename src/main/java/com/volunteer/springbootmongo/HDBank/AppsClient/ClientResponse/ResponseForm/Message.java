package com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
public enum Message {
    link_HDBank_account_failure_username_or_password_were_wrong,
    this_HDBank_account_linked_please_choose_another_account,
    current_user_is_limit_link_bank_account,
    the_Phone_number_is_wrong_format,

    change_password_success_login_again_please,
    new_password_is_wrong_format,
    new_password_is_used_in_a_previous_time_try_another_password
}
