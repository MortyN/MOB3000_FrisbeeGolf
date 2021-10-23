package com.example.mob3000_frisbeegolf.api.model

class UserSetting {
    private var settingId: Long
    private var settingName: String? = null
    private var settingDescription: String? = null

    constructor(settingId: Long) {
        this.settingId = settingId
    }

    constructor(settingId: Long, settingName: String?, settingDescription: String?) {
        this.settingId = settingId
        this.settingName = settingName
        this.settingDescription = settingDescription
    }
}