package com.example.mob3000_frisbeegolf.api.model

class UserSettingMatrix(
    private val userSetting: UserSetting,
    private val user: User,
    private val settingValue: String,
    private val active: Boolean
)